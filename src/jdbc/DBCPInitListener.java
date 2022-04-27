package jdbc;

import java.io.IOException;
import java.io.StringReader;
import java.sql.DriverManager;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class DBCPInitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String poolConfig = 
				sce.getServletContext().getInitParameter("poolConfig");
		Properties prop = new Properties();
		try {
			prop.load(new StringReader(poolConfig));
		} catch (IOException e) {
			throw new RuntimeException("config load fail",e);
		}
		loadJDBCDriver(prop);
		initConnection(prop);

	}

	private void loadJDBCDriver(Properties prop) {
		String driverClass = prop.getProperty("jdbcdriver");
		try {
			Class.forName(driverClass);

		} catch (ClassNotFoundException ex) {
			throw new RuntimeException("fail to load JDBC Driver", ex);
		}
	}

	private void initConnection(Properties prop) {
		try {
			String jdbcUrl = prop.getProperty("jdbcUrl");
			String username = prop.getProperty("dbUser");
			String pw = prop.getProperty("dbPass");
			
			ConnectionFactory connFactory = 
					new DriverManagerConnectionFactory(jdbcUrl, username, pw);

			PoolableConnectionFactory poolableConnFactroy = 
					new PoolableConnectionFactory(connFactory, null);
			String validationQuery = prop.getProperty("validationQuery");  
			if(validationQuery != null && !validationQuery.isEmpty()) {
				poolableConnFactroy.setValidationQuery(validationQuery);
		}
			
			

			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
			poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L);
			poolConfig.setTestWhileIdle(true);
			int minIdle = getIntProperty(prop, "minIdle",5);
			poolConfig.setMinIdle(minIdle);
			int maxTotal = getIntProperty(prop, "maxTotal",50);
			poolConfig.setMaxTotal(maxTotal);

			GenericObjectPool<PoolableConnection> connentionPool =
					new GenericObjectPool<>(poolableConnFactroy, poolConfig);
			poolableConnFactroy.setPool(connentionPool);

			Class.forName("org.apache.commons.dbcp2.PoolingDriver");
			PoolingDriver driver = (PoolingDriver) 
					DriverManager.getDriver("jdbc:apache:commons:dbcp:");
			String poolName = prop.getProperty("poolName");
			driver.registerPool(poolName, connentionPool);

		} catch (Exception e) {
			throw new RuntimeException();
		}

	}

	private int getIntProperty(Properties prop, String propName, int defaultValue) {
		String value = prop.getProperty(propName);
		if(value == null)return defaultValue;
		return Integer.parseInt(value);
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
	
	

}