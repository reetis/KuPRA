package eu.komanda30.kupra;

import eu.komanda30.kupra.config.ControllerConfig;
import eu.komanda30.kupra.config.PersistenceConfig;

import javax.servlet.Filter;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@SuppressWarnings("UnusedDeclaration")
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {
                PersistenceConfig.class,
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { ControllerConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/*" };
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[0];
    }

}