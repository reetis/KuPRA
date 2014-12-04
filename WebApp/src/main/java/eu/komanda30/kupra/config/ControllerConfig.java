package eu.komanda30.kupra.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestDataValueProcessor;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan({
        "eu.komanda30.kupra.controllers",
        "eu.komanda30.kupra.locale",
        "eu.komanda30.kupra.uploads"})
@PropertySource("classpath:/kupra.properties")
public class ControllerConfig extends WebMvcConfigurerAdapter {

    public static final int SECONDS_IN_YEAR = (int) Duration.ofDays(365).getSeconds();

    @Value("${templates.enable_cache}")
    boolean templateCacheEnabled;

    @Value("${messages.enable_cache}")
    boolean messageCacheEnabled;

    @Value("${resources.enable_cache}")
    boolean resourceCacheEnabled;

    /*@Resource
    private KupraLocaleResolver kupraLocaleResolver;      */

    @Bean
    public SpringTemplateEngine templateEngine() {
        final ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCacheable(templateCacheEnabled);

        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        return templateEngine;
    }

    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);
        viewResolver.setCache(templateCacheEnabled);
        viewResolver.setContentType("text/html;charset=UTF-8");
        return viewResolver;
    }

    /*@Bean
    public LocaleResolver localeResolver() {
        return kupraLocaleResolver;
    }                     */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(
                "classpath:/messages/messages",
                "classpath:/messages/validation");
        messageSource.setCacheSeconds(messageCacheEnabled ? -1 : 0);
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/css/")
                .setCachePeriod(resourceCacheEnabled ? SECONDS_IN_YEAR : 0);
        registry.addResourceHandler("/img/**")
                .addResourceLocations("/img/")
                .setCachePeriod(resourceCacheEnabled ? SECONDS_IN_YEAR : 0);
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/js/")
                .setCachePeriod(resourceCacheEnabled?SECONDS_IN_YEAR:0);
        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("/fonts/")
                .setCachePeriod(resourceCacheEnabled?SECONDS_IN_YEAR:0);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        final DefaultMessageCodesResolver resolver = new DefaultMessageCodesResolver();
        resolver.setMessageCodeFormatter(DefaultMessageCodesResolver.Format.POSTFIX_ERROR_CODE);
        resolver.setPrefix("error.");
        return resolver;
    }

    @Bean
    public RequestDataValueProcessor requestDataValueProcessor() {
        return new CsrfRequestDataValueProcessor();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        StandardServletMultipartResolver resolver=new StandardServletMultipartResolver();
        //resolver.setDefaultEncoding("utf-8");
        return resolver;
    }

    /*@Bean
    public PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
        ClassPathResource[] resourceLocations = new ClassPathResource[] {
                new ClassPathResource("/kupra.properties")
        };
        p.setLocations(resourceLocations);
        return p;
    }*/

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}