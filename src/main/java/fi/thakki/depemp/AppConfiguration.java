package fi.thakki.depemp;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("fi.thakki.depemp.dao")
@ComponentScan("fi.thakki.depemp.service")
@PropertySource("classpath:application.properties")
public class AppConfiguration {

	@Value("${spring.jpa.entity.package}")
	private String myEntityPackage;

	@Value("${spring.datasource.driver-class-name}")
	private String myDatasourceDriverClassName;
	
	@Value("${spring.datasource.url}")
	private String myDatasourceUrl;

	@Value("${spring.datasource.username}")
	private String myDatasourceUsername;

	@Value("${spring.datasource.password}")
	private String myDatasourcePassword;

	@Value("${spring.jpa.hibernate.dialect}")
	private String myHibernateDialect;

	@Value("${spring.jpa.hibernate.show_sql}")
	private String myHibernateShowSql;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String myHibernateDdlAuto;

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setPackagesToScan(myEntityPackage);
		entityManagerFactoryBean.setJpaProperties(hibernateProperties());
		return entityManagerFactoryBean;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", myHibernateDialect);
		properties.put("hibernate.show_sql", myHibernateShowSql);
		properties.put("hibernate.ddl-auto", myHibernateDdlAuto);
		return properties;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(myDatasourceDriverClassName);
		dataSource.setUrl(myDatasourceUrl);
		dataSource.setUsername(myDatasourceUsername);
		dataSource.setPassword(myDatasourcePassword);
		return dataSource;
	}
}
