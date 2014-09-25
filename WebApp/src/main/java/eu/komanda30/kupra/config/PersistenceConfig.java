package eu.komanda30.kupra.config;

import java.beans.PropertyVetoException;

import javax.annotation.Resource;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.google.common.base.Throwables;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@PropertySource("file:${KUPRA_CONFIG_DIR}/data-sources.properties")
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

        final Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setInitOnMigrate(true);
        flyway.migrate();

        return dataSource;
    }
}
