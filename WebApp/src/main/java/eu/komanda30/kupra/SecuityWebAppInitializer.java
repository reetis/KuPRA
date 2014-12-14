package eu.komanda30.kupra;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

@SuppressWarnings("UnusedDeclaration")
public class SecuityWebAppInitializer extends AbstractSecurityWebApplicationInitializer {
    protected EnumSet<DispatcherType> getSecurityDispatcherTypes() {
        return EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR, DispatcherType.ASYNC, DispatcherType.FORWARD);
    }

    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        final CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        insertFilters(servletContext, encodingFilter);
    }
}
