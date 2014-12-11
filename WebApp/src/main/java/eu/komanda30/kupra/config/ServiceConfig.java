package eu.komanda30.kupra.config;

import eu.komanda30.kupra.services.RecipeFinder;
import eu.komanda30.kupra.services.UserFinder;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({"eu.komanda30.kupra.services"})
@PropertySource("classpath:/kupra.properties")
public class ServiceConfig {
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
