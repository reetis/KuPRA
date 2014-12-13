package eu.komanda30.kupra.config;

import eu.komanda30.kupra.email.DebugMailSender;
import eu.komanda30.kupra.services.RecipeFinder;
import eu.komanda30.kupra.services.UserFinder;

import java.time.Duration;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan({
        "eu.komanda30.kupra.controllers",
        "eu.komanda30.kupra.locale",
        "eu.komanda30.kupra.uploads"})
@PropertySource("classpath:/kupra.properties")
public class WebConfig extends WebMvcConfigurerAdapter {

    public static final int SECONDS_IN_YEAR = (int) Duration.ofDays(365).getSeconds();

    @Value("${templates.enable_cache}")
    boolean templateCacheEnabled;

    @Value("${messages.enable_cache}")
    boolean messageCacheEnabled;

    @Value("${resources.enable_cache}")
    boolean resourceCacheEnabled;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public JavaMailSender mailSender() {
        final String protocol = environment.getRequiredProperty("email.protocol");
        if ("DEBUG".equals(protocol)) {
            return new DebugMailSender();
        }

        final JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(environment.getRequiredProperty("email.host"));
        sender.setPort(environment.getRequiredProperty("email.port", Integer.TYPE));
        sender.setProtocol(environment.getRequiredProperty("email.protocol"));
        sender.setUsername(environment.getRequiredProperty("email.username"));
        sender.setPassword(environment.getRequiredProperty("email.password"));
        return sender;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        final ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
        emailTemplateResolver.setPrefix("templates/");
        emailTemplateResolver.setTemplateMode("HTML5");
        emailTemplateResolver.setCharacterEncoding("UTF-8");
        emailTemplateResolver.setCacheable(templateCacheEnabled);
        emailTemplateResolver.setOrder(1);

        final ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(templateCacheEnabled);
        resolver.setOrder(2);

        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(emailTemplateResolver);
        templateEngine.addTemplateResolver(resolver);
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
    public MessageCodesResolver getMessageCodesResolver() {
        final DefaultMessageCodesResolver resolver = new DefaultMessageCodesResolver();
        resolver.setMessageCodeFormatter(DefaultMessageCodesResolver.Format.POSTFIX_ERROR_CODE);
        resolver.setPrefix("error.");
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
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
                .setCachePeriod(resourceCacheEnabled ? SECONDS_IN_YEAR : 0);
        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("/fonts/")
                .setCachePeriod(resourceCacheEnabled?SECONDS_IN_YEAR:0);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public RequestDataValueProcessor requestDataValueProcessor() {
        return new CsrfRequestDataValueProcessor();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Resource
    private Environment environment;

    @Bean
    public ApplicationListener<ContextRefreshedEvent> startupReindexer(
            RecipeFinder recipeFinder,
            UserFinder userFinder) {
        return new StartupReindexer(recipeFinder, userFinder);
    }

    private class StartupReindexer implements ApplicationListener<ContextRefreshedEvent> {
        private final RecipeFinder recipeFinder;
        private final UserFinder userFinder;

        public StartupReindexer(RecipeFinder recipeFinder, UserFinder userFinder) {
            this.recipeFinder = recipeFinder;
            this.userFinder = userFinder;
        }

        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
            if (environment.getProperty("index.reindex_on_startup", Boolean.class, false)) {
                recipeFinder.indexRecipes();
                userFinder.indexUsers();
            }
        }
    }
}