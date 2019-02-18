package server.config;

import server.util.DataSourceUtil;
import server.util.TenantContextHolder;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"server.system.persistence"},
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
public class SystemConfig {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() throws URISyntaxException {
        return DataSourceUtil.createAndConfigureDataSource(TenantContextHolder.getTenant());
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean("transactionManager")
    public JpaTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            MultiTenantConnectionProvider connectionProvider,
            CurrentTenantIdentifierResolver tenantResolver,
            JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPackagesToScan("server.system.domain");
        em.setPersistenceUnitName("system-persistence-unit");
        Map<String, Object> properties = new HashMap<>();
        properties.put(org.hibernate.cfg.Environment.MULTI_TENANT,
                MultiTenancyStrategy.SCHEMA);
        properties.put(
                org.hibernate.cfg.Environment.MULTI_TENANT_CONNECTION_PROVIDER,
                connectionProvider);
        properties.put(
                org.hibernate.cfg.Environment.MULTI_TENANT_IDENTIFIER_RESOLVER,
                tenantResolver);
        properties.put(org.hibernate.cfg.Environment.SHOW_SQL, false);
        properties.put(org.hibernate.cfg.Environment.FORMAT_SQL, true);
        properties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "update");
        em.setJpaVendorAdapter(jpaVendorAdapter);
        em.setJpaPropertyMap(properties);
        return em;
    }
    
}
