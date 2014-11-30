package eu.komanda30.kupra.locale;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.UserId;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.services.UserRegistrar;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.AbstractLocaleContextResolver;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Created by Rytis on 2014-11-30.
 */
@Component
public class KupraLocaleResolver extends AbstractLocaleContextResolver{
    private static final String DEFAULT_LOCALE = "lt-LT";
    private static final String LOCALE_SESSION_ATTRIBUTE_NAME = "Locale";

    @Resource
    private KupraUsers kupraUsers;

    @Resource
    private UserRegistrar userRegistrar;

    @Resource
    private HttpSession httpSession;

    @Override
    public LocaleContext resolveLocaleContext(HttpServletRequest httpServletRequest) {
        return new LocaleContext() {
            @Override
            public Locale getLocale() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                KupraUser user = kupraUsers.findOne(UserId.forUsername(auth.getName()));
                if (user == null) {
                    Locale sessionLocale = (Locale) WebUtils.getSessionAttribute(httpServletRequest, LOCALE_SESSION_ATTRIBUTE_NAME);
                    if (sessionLocale == null) {
                        return Locale.forLanguageTag(DEFAULT_LOCALE);
                    } else {
                        return sessionLocale;
                    }
                }

                Locale locale = user.getUserProfile().getLocale();
                if (locale == null) {
                    locale = Locale.forLanguageTag(DEFAULT_LOCALE);
                }
                return locale;

            }
        };
    }

    @Override
    public void setLocaleContext(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, LocaleContext localeContext) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        KupraUser user = kupraUsers.findOne(UserId.forUsername(auth.getName()));
        if (user==null) {
            WebUtils.setSessionAttribute(httpServletRequest, "Locale", localeContext.getLocale());
        } else {
            userRegistrar.editLocale(user.getUserId(), localeContext.getLocale());
        }
    }
}
