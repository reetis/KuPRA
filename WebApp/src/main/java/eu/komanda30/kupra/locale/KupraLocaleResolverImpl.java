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
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AbstractLocaleContextResolver;
import org.springframework.web.util.WebUtils;

@Component("localeResolver")
public class KupraLocaleResolverImpl extends AbstractLocaleContextResolver implements LocaleResolver {
    private static final String DEFAULT_LOCALE = "lt-LT";
    private static final String LOCALE_SESSION_ATTRIBUTE_NAME = "Locale";

    @Resource
    private KupraUsers kupraUsers;

    @Override
    public LocaleContext resolveLocaleContext(HttpServletRequest httpServletRequest) {
        return () -> resolveLocaleFromRequest(httpServletRequest);
    }

    private Locale resolveLocaleFromRequest(HttpServletRequest httpServletRequest) {
        Locale locale = getLocaleFromSession(httpServletRequest);
        if (locale != null) {
            return locale;
        }

        locale = getLocaleFromAuth();
        if (locale != null) {
            saveLocaleInSession(httpServletRequest, locale);
            return locale;
        }

        locale = Locale.forLanguageTag(DEFAULT_LOCALE);
        saveLocaleInSession(httpServletRequest, locale);
        return locale;
    }

    private Locale getLocaleFromSession(HttpServletRequest httpServletRequest) {
        return (Locale) WebUtils.getSessionAttribute(
                httpServletRequest, LOCALE_SESSION_ATTRIBUTE_NAME);
    }

    private Locale getLocaleFromAuth() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }

        final KupraUser user = kupraUsers.findOne(auth.getName());
        if (user == null) {
            return null;
        }

        return user.getProfile().getLocale();
    }

    private void saveLocaleInSession(HttpServletRequest httpServletRequest, Locale locale) {
        WebUtils.setSessionAttribute(httpServletRequest,
                LOCALE_SESSION_ATTRIBUTE_NAME, locale);
    }

    @Transactional
    @Override
    public void setLocaleContext(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, LocaleContext localeContext) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        KupraUser user = kupraUsers.findOne(auth.getName());
        if (user==null) {
            saveLocaleInSession(httpServletRequest, localeContext.getLocale());
        } else {
            user.getProfile().setLocale(localeContext.getLocale());
        }
        kupraUsers.save(user);
    }
}
