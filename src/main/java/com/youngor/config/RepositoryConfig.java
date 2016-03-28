package com.youngor.config;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.SessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.mawujun.repository.DialectEnum;
import com.mawujun.repository.MySqlSessionFactoryBean;
import com.mawujun.repository.mybatis.DatabaseIdProviderCustom;
import com.mawujun.repository.mybatis.PageInterceptor;

//http://10176523.cn/archives/36/ 改成整合成使用hibernate5 试试，看看有没有问题
@Configuration
@EnableTransactionManagement(mode = AdviceMode.PROXY)
// @EnableAspectJAutoProxy(proxyTargetClass=false)
@ComponentScan(basePackages = "com.youngor", excludeFilters = @Filter(type = FilterType.ANNOTATION, value = { Controller.class }))
@MapperScan(basePackages = "com.youngor", sqlSessionFactoryRef = "sqlSessionFactory", annotationClass = org.springframework.stereotype.Repository.class)
@PropertySource("classpath:config.properties")
public class RepositoryConfig implements TransactionManagementConfigurer {
	@Value("${jdbc.packagesToScan}")
	private String jdbc_packagesToScan;// = "com.youngor";
	@Value("${jdbc.dbType}")
	private String jdbc_db_type;// = "h2";// "oracle";//
	@Value("${jdbc.driver}")
	private String jdbc_driver;// = "org.h2.Driver";// "oracle.jdbc.driver.OracleDriver";//
	@Value("${jdbc.url}")
	private String jdbc_url;// = "jdbc:h2:mem:BaseProject;DB_CLOSE_DELAY=-1;MVCC=TRUE";// "jdbc:oracle:thin:@127.0.0.1:1521:ems";//
	@Value("${jdbc.username}")
	private String jdbc_username;// = "sa";// "ems";//
	@Value("${jdbc.password}")
	private String jdbc_password;// = "";
	@Value("${jdbc.minIdle}")
	private Integer jdbc_minIdle = 3;
	@Value("${jdbc.maxIdle}")
	private Integer jdbc_maxIdle = 20;
	@Value("${hibernate.hbm2ddl.auto}")
	private String hibernate_hbm2ddl_auto = "update";

	// To resolve ${} in @Value
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		MySqlSessionFactoryBean sqlSessionFactoryBean = new MySqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		sqlSessionFactoryBean.setSessionFactory(sessionFactory());

		// sqlSessionFactoryBean.setTypeHandlers(new TypeHandler[]{new
		// YesNoTypeHandler()});

		PageInterceptor pageInterceptor = new PageInterceptor();
		pageInterceptor.setDialectClass(DialectEnum.valueOf(jdbc_db_type)
				.get_mybatis_dialect());
		sqlSessionFactoryBean.setPlugins(new Interceptor[] { pageInterceptor });

		DatabaseIdProviderCustom provider = new DatabaseIdProviderCustom();
		provider.setDatabaseId(jdbc_db_type);
		sqlSessionFactoryBean.setDatabaseIdProvider(provider);
		

//		Resource resource=new ClassPathResource("classpath:/**/*_common_Mapper.xml");
//		sqlSessionFactoryBean.setMapperLocations(new Resource[]{resource});

		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public DataSource dataSource() throws SQLException {
		// https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_DruidDataSource%E5%8F%82%E8%80%83%E9%85%8D%E7%BD%AE
		DruidDataSource datasource = new DruidDataSource();
		// BasicDataSource datasource=new BasicDataSource();
		datasource.setDriverClassName(jdbc_driver);
		datasource.setUrl(jdbc_url);
		datasource.setUsername(jdbc_username);
		datasource.setPassword(jdbc_password);

		datasource.setDefaultAutoCommit(false);

		// 通常来说，只�?要修改initialSize、minIdle、maxActive�?
		datasource.setInitialSize(jdbc_minIdle);
		datasource.setMinIdle(jdbc_minIdle);
		datasource.setMaxActive(jdbc_maxIdle);
		// datasource.setMaxWait(600000);

		// 如果用Oracle，则把poolPreparedStatements配置为true，mysql可以配置为false。分库分表较多的数据库，建议配置为false�?
		datasource.setPoolPreparedStatements(true);

		// datasource.setFilters("stat,slf4j");
		// Log4jFilter log_filter=new Log4jFilter();
		// log_filter.setResultSetLogEnabled(false);
		Slf4jLogFilter log_filter = new Slf4jLogFilter();
		log_filter.setResultSetLogEnabled(true);
		ArrayList<com.alibaba.druid.filter.Filter> filter_list = new ArrayList<com.alibaba.druid.filter.Filter>();
		filter_list.add(log_filter);
		datasource.setProxyFilters(filter_list);
		return datasource;
	}

	@Bean
	@Lazy(false)
	public SessionFactory sessionFactory() throws IOException, SQLException {
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		localSessionFactoryBean.setDataSource(dataSource());
		localSessionFactoryBean.setPackagesToScan(jdbc_packagesToScan);

		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.hbm2ddl.auto",hibernate_hbm2ddl_auto);
		hibernateProperties.put("hibernate.dialect",DialectEnum.valueOf(jdbc_db_type).get_hibernate_dialect());

		hibernateProperties.put("hibernate.show_sql", false);
		hibernateProperties.put("hibernate.format_sql", false);
		hibernateProperties.put("use_sql_comments", false);
		hibernateProperties.put("hibernate.max_fetch_depth", 2);
		hibernateProperties.put("hibernate.jdbc.batch_size", 30);

		hibernateProperties.put("hibernate.generate_statistics", false);
		hibernateProperties
				.put("hibernate.cache.use_structured_entries", false);

		hibernateProperties.put("hibernate.connection.release_mode",
				"after_transaction");

		localSessionFactoryBean.setHibernateProperties(hibernateProperties);
		localSessionFactoryBean.afterPropertiesSet();
		return (SessionFactory) localSessionFactoryBean.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws IOException,
			SQLException {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		// transactionManager.setDataSource(dataSource());
		transactionManager.setSessionFactory(sessionFactory());
		// transactionManager.setRollbackOnCommitFailure(true);
		// transactionManager.set

		return transactionManager;
	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		PlatformTransactionManager transactionManager = null;
		try {
			transactionManager = transactionManager();
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transactionManager;
	}

}
