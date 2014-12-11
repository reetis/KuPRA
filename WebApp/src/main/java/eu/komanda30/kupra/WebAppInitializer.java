package eu.komanda30.kupra;

import eu.komanda30.kupra.config.ControllerConfig;
import eu.komanda30.kupra.config.PersistenceConfig;
import eu.komanda30.kupra.config.SecurityConfig;
import eu.komanda30.kupra.config.ServiceConfig;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@SuppressWarnings("UnusedDeclaration")
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    private final static long BYTES_IN_MB = 2>>20;

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {
                PersistenceConfig.class,
                ServiceConfig.class,
                SecurityConfig.class
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

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        final MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement(
                        "",
                        UploadConfiguration.MAX_UPLOAD_FILE_SIZE_MB * BYTES_IN_MB,
                        UploadConfiguration.MAX_REQUEST_SIZE_MB * BYTES_IN_MB,
                        (int)(UploadConfiguration.MAX_FILE_THRESHOLD_MB * BYTES_IN_MB) );

        registration.setMultipartConfig(multipartConfigElement);
    }
}