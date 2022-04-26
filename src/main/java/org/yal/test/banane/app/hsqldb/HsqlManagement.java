package org.yal.test.banane.app.hsqldb;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PostLoad;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.yal.test.banane.app.modele.Commande;
import org.yal.test.banane.app.modele.Destinataire;

/**
 * @author YLE
 * 13 avr. 2022
 * 
 */
@Named
@PropertySource("file:${fichier.config}")
public class HsqlManagement
{
  private Environment         environment;
  
  @Inject
  public HsqlManagement(Environment environment)
  {
    this.environment = environment;
  }
  
  @PostLoad
  public String launchServer() throws Exception
  {
//    if (!hsqlFile.name().equals(databaseConfig.getType()))
//      return;
    String configFilePath = environment.getProperty("fichier.config");
    org.hsqldb.server.Server server = new org.hsqldb.server.Server();
    
    Path t = Paths.get(environment.getProperty("hsql.path"))
                  .resolve(environment.getProperty("hsql.database.name"))
                  .normalize();
    
    Path t1 = Paths .get(environment.getProperty("hsql.path"))
                    .resolve(environment.getProperty("hsql.database.name"))
                    .toAbsolutePath().normalize();
    
    if (!t.toString().equals(t1.toString()))
    {
      t = Paths .get(environment.getProperty("fichier.config")).getParent().getParent()
                .resolve(environment.getProperty("hsql.path"))
                .resolve(environment.getProperty("hsql.database.name"))
                .toAbsolutePath().normalize();
    }
    
    server.setDatabasePath(0, "file:" + t.toString());
    server.setDatabaseName(0, t.getFileName().toString());
//    server.setPort(Integer.valueOf(databaseConfig.getPort()));
    server.setSilent(true);
    server.start();
    
    return t.toAbsolutePath().toString();
  }
  
  public static void initDatabase(Map<String, Object> settings, String databaseLocation)
  {
//    settings.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
//    settings.put("hibernate.connection.url", "jdbc:hsqldb:file:" + nomDatabase); //D:\\dvp\\projets\\tmp\\db\\testdb");
//    settings.put("hibernate.connection.username", "SA");
//    settings.put("hibernate.connection.password", "");
//    settings.put("hibernate.default_schema", "PUBLIC");
//    settings.put("hibernate.connection.autocommit", "true");
//    settings.put("hibernate.show_sql", "true");
//    settings.put("hibernate.format_sql", "true");
//    settings.put("hibernate.jdbc.batch_size", "30");

//    if (!createdatabase)
//    {
//      settings.put("hibernate.hbm2ddl.auto", "none");
//      return;
//    }
//    else
      settings.put("hibernate.connection.url", "jdbc:hsqldb:file:" + databaseLocation); //D:\\dvp\\projets\\tmp\\db\\testdb");
      settings.put("hibernate.hbm2ddl.auto", "update");
    
    MetadataSources metadata = new MetadataSources(new StandardServiceRegistryBuilder().applySettings(settings).enableAutoClose().build());
    metadata.addAnnotatedClass(Destinataire.class);
    metadata.addAnnotatedClass(Commande.class);
    
    SchemaExport schemaExport = new SchemaExport();
    schemaExport.create(EnumSet.of(TargetType.STDOUT), metadata.buildMetadata());
  }



}
