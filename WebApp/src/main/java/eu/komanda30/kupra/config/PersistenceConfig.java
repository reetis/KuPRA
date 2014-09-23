package eu.komanda30.kupra.config;

import java.beans.PropertyVetoException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.flywaydb.core.Flyway;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
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

    @Bean
    @Autowired
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource, ResourceLoader resourceLoader) {
        final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("eu.komanda30.kupra.model");
        bean.setMapperLocations(new org.springframework.core.io.Resource[] {
                resourceLoader.getResource("classpath*:eu/komanda30/kupra/mappers/*.xml")
        });
        return bean;
    }

    @Bean
    @Autowired
    public SqlSessionTemplate sqlSession(SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        final MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("eu.komanda30.kupra.mappers");
        return configurer;
    }
}
