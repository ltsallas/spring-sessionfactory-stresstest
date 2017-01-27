package com.example;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
@ComponentScan("com.example")
@EnableJpaRepositories(entityManagerFactoryRef = "jpaEntityManagerFactory", basePackages = "com.example.repository")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public DataSource dataSource(DataSourceProperties dataSourceProperties) {
		if (dataSourceProperties.getUrl() == null) {
			throw new ApplicationContextException("Database connection pool is not configured correctly");
		}
		HikariDataSource hikariDataSource =  (HikariDataSource) DataSourceBuilder
				.create(dataSourceProperties.getClassLoader())
				.type(HikariDataSource.class)
				.driverClassName(dataSourceProperties.getDriverClassName())
				.url(dataSourceProperties.getUrl())
				.username(dataSourceProperties.getUsername())
				.password(dataSourceProperties.getPassword())

				.build();

		return hikariDataSource;
	}

	@Bean
	public SessionFactory sessionFactory(DataSourceProperties dataSourceProperties) {
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource(dataSourceProperties));
		sessionBuilder
				.scanPackages("com.example.domain")
				.setProperty("hibernate.show_sql", "true")
            .setProperty("hibernate.hbm2ddl.auto", "create")
//                .setProperty("hibernate.jdbc.use_streams_for_binary", "false")
				.setProperty("hibernate.query.timeout", "180")
				.setProperty("hibernate.default_batch_fetch_size", "100")
				.setProperty("hibernate.jdbc.batch_size", "30")
				.setProperty("hibernate.sessionfactory.reuse", "true")
				.setProperty("hibernate.order_updates", "true")
				.setProperty("hibernate.order_inserts", "true")

				// database dependent configuration
				// only for Oracle (as it has no boolean support)
				.setProperty("hibernate.query.substitutions", "true 1, false 0");
		return sessionBuilder.buildSessionFactory();
	}

	@Bean
	@Qualifier(value = "hibernateTransactionManager")
	@Primary
	public HibernateTransactionManager hibernateTransactionManager(DataSourceProperties dataSourceProperties) {
		HibernateTransactionManager transactionManager =
				new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory(dataSourceProperties));
		return transactionManager;
	}

	@Bean(name = "jpaEntityManagerFactory")
	@Qualifier("jpaEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(
			EntityManagerFactoryBuilder builder,
			@Qualifier("dataSource") DataSource dataSource) {

		return builder
				.dataSource(dataSource)
				.packages("com.example.domain")
				.persistenceUnit("foo")
				.build();
	}
//
//	@Bean(name = "jpaTransactionManager")
//	public PlatformTransactionManager jpaTransactionManager() {
//		return jpaTransactionManager();
//	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("jpaEntityManagerFactory") EntityManagerFactory jpaEntityManagerFactory) {
		return new JpaTransactionManager(jpaEntityManagerFactory);
	}
}
