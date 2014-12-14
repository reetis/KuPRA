package eu.komanda30.kupra.config;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.hibernate.cache.ehcache.EhCacheRegionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import com.google.common.base.Throwables;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/data-sources.properties")
@EnableJpaRepositories(basePackages = "eu.komanda30.kupra.repositories")
public class PersistenceConfig {
    private static final int DATA_SOURCE_MAX_POOL_SIZE = 25;

    @Resource
    private Environment environment;

    @Bean(destroyMethod = "close")
    public ComboPooledDataSource dataSource() {
        final ComboPooledDataSource dataSource;
        try {
            dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass(environment.getRequiredProperty("jdbc.driverClassName"));
            dataSource.setJdbcUrl(environment.getRequiredProperty("jdbc.url"));
            dataSource.setUser(environment.getRequiredProperty("jdbc.username"));
            dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
            dataSource.setPreferredTestQuery("select 1");
            dataSource.setInitialPoolSize(1);
            dataSource.setMinPoolSize(1);
            dataSource.setMaxPoolSize(DATA_SOURCE_MAX_POOL_SIZE);
        } catch (PropertyVetoException e) {
            throw Throwables.propagate(e);
        }


        final String[] migrationDirs = environment
                .getRequiredProperty("flyway.migrations", String[].class);

        final Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setInitOnMigrate(true);
        flyway.setLocations(migrationDirs);
        flyway.setOutOfOrder(true);
        flyway.setCleanOnValidationError(environment.getProperty("flyway.clean_on_error", Boolean.TYPE, false));
        flyway.migrate();

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        final LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        bean.setDataSource(dataSource);

        final Map<String, Object> props = new HashMap<>();
        props.put(AvailableSettings.DIALECT, environment.getRequiredProperty(AvailableSettings.DIALECT));
        props.put(AvailableSettings.HBM2DDL_AUTO, environment.getRequiredProperty(AvailableSettings.HBM2DDL_AUTO));
        props.put(AvailableSettings.FORMAT_SQL, environment.getRequiredProperty(AvailableSettings.FORMAT_SQL));
        props.put(AvailableSettings.STATEMENT_BATCH_SIZE, environment.getRequiredProperty(AvailableSettings.STATEMENT_BATCH_SIZE));
        props.put(AvailableSettings.STATEMENT_FETCH_SIZE, environment.getRequiredProperty(AvailableSettings.STATEMENT_FETCH_SIZE));
        props.put(AvailableSettings.USE_QUERY_CACHE, true);
        props.put(AvailableSettings.USE_SECOND_LEVEL_CACHE, true);
        props.put(AvailableSettings.CACHE_REGION_FACTORY, EhCacheRegionFactory.class.getName());
        props.put(AvailableSettings.RELEASE_CONNECTIONS, "after_transaction");
        props.put("hibernate.connection.charSet","UTF-8");
        props.put("hibernate.connection.characterEncoding","utf8");

        props.put(org.hibernate.jpa.AvailableSettings.NAMING_STRATEGY, ImprovedNamingStrategy.class.getName());

        props.put("hibernate.search.default.directory_provider",
                environment.getRequiredProperty("hibernate.search.default.directory_provider"));
        props.put("hibernate.search.default.indexBase",
                environment.getRequiredProperty("hibernate.search.default.indexBase"));

        bean.setJpaPropertyMap(props);

        bean.setPackagesToScan("eu.komanda30.kupra.entity");
        return bean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        return new JpaTransactionManager(emf);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
}
