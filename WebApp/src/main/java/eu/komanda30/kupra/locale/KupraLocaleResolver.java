package eu.komanda30.kupra.locale;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.repositories.KupraUsers;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.i18n.AbstractLocaleContextResolver;
import org.springframework.web.util.WebUtils;

/**
 * Created by Rytis on 2014-11-30.
 */
@Component("localeResolver")
public class KupraLocaleResolver extends AbstractLocaleContextResolver{
    private static final String DEFAULT_LOCALE = "lt-LT";
    private static final String LOCALE_SESSION_ATTRIBUTE_NAME = "Locale";

    @Resource
    private KupraUsers kupraUsers;

    @Override
    public LocaleContext resolveLocaleContext(HttpServletRequest httpServletRequest) {
        return new LocaleContext() {
            @Override
            public Locale getLocale() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                KupraUser user = kupraUsers.findOne(auth.getName());
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

    @Transactional
    @Override
    public void setLocaleContext(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, LocaleContext localeContext) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        KupraUser user = kupraUsers.findOne(auth.getName());
        if (user==null) {
            WebUtils.setSessionAttribute(httpServletRequest, "Locale", localeContext.getLocale());
        } else {
            user.getUserProfile().setLocale(localeContext.getLocale());
        }
    }
}
