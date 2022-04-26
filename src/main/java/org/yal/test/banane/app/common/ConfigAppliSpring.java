package org.yal.test.banane.app.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.yal.test.banane.app.hsqldb.HsqlManagement;

/**
 * @author YLE 1 oct. 2019
 * 
 */
@Named
@Configuration
@PropertySource("file:${fichier.config}")
public class ConfigAppliSpring
{
  private final static Logger logger = LoggerFactory.getLogger(ChargementFichierDeConfig.class);
  
  private Environment         environment;
  private HsqlManagement      hsqlManagement;
  
  @Inject
  public ConfigAppliSpring(Environment environment, HsqlManagement hsqlManagement)
  {
    this.environment = environment;
    this.hsqlManagement = hsqlManagement;
  }
  
  @Bean(name = "sessionFactory")
  public LocalSessionFactoryBean getSessionFactory()
  {
    String databaseLocation = "";
    try
    {
      databaseLocation = hsqlManagement.launchServer();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("", e);
    }
    
    Properties configHibernate = new Properties();
    // configHibernate.putAll(MainCfecProcess.settings);
    Map<String, Object> map = new HashMap<>();
    environment.getProperty("");
    
    for (Iterator<?> it = ((StandardEnvironment) environment).getPropertySources().iterator(); it.hasNext();)
    {
      Object o = it.next();
      org.springframework.core.env.PropertySource<?> propertySource = (org.springframework.core.env.PropertySource<?>) o;
      if (propertySource instanceof CompositePropertySource)
      {
        System.out.println("info -> " + propertySource.getName() + ": " + propertySource.getSource());
        ResourcePropertySource sources = (ResourcePropertySource) ((CompositePropertySource) propertySource).getPropertySources().toArray()[0];
        sources.getSource();
        int i = 0;
        map.putAll(sources.getSource());
      }
    }
    
    try
    {
      map.put("hibernate.connection.url", "jdbc:hsqldb:file:" + databaseLocation); // D:\\dvp\\projets\\tmp\\db\\testdb");
      HsqlManagement.initDatabase(map, databaseLocation);
    }
    catch (Exception e)
    {
      logger.error("probleme lors de la creation de la base", e);
      throw e;
    }
    configHibernate.putAll(map);
    LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
    factoryBean.setHibernateProperties(configHibernate);
    factoryBean.setPackagesToScan("org.yal.test.banane.app.modele");
    return factoryBean;
    
  }
  
  @Autowired
  @Bean(name = "transactionManager")
  public JpaTransactionManager getTransactionManager(@Named("sessionFactory") SessionFactory sessionFactory)
  {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(sessionFactory);
    
    return transactionManager;
  }
  
  @Bean
  public JpaVendorAdapter jpaVendorAdapter()
  {
    HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
    hibernateJpaVendorAdapter.setShowSql(false);
    hibernateJpaVendorAdapter.setGenerateDdl(false);
    return hibernateJpaVendorAdapter;
  }
  
  public static void main(String[] args)
  {}
}
