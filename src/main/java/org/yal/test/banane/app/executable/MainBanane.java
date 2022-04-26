package org.yal.test.banane.app.executable;

import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAutoConfiguration(exclude =
{ DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@ComponentScan(basePackages = "org.yal.test.banane.app")
public class MainBanane
{
  private final static Logger logger = LoggerFactory.getLogger(MainBanane.class);
  static public final Map<String, String> settings1 = new HashMap<>();
  
  public MainBanane()
  {}
  
  public static void main(String[] args)
  {
    try
    {
      Security.addProvider(new BouncyCastleProvider());
      
      new SpringApplicationBuilder(MainBanane.class).build().run(args);
      
      logger.info("démarrage parfait");
    }
    catch (Exception e)
    {
      logger.error("probleme trouve", e);
    }
  }
}
