package org.yal.test.banane.app.common;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.yal.test.banane.app.business.BananeService;
import org.yal.test.banane.app.db.CommandeDao;
import org.yal.test.banane.app.rest.APIRestManagement;

/**
 * @author YLE 1 oct. 2019
 * 
 */
@Named
@PropertySource("file:${fichier.config}")
public class ChargementInitial
{
  private final static Logger logger = LoggerFactory.getLogger(ChargementInitial.class);
  private FileChannel lock;
  
  private Environment environment;
  
  @Inject
  public ChargementInitial(Environment environment)
  {
    this.environment = environment;
  }
  
  @EventListener
  public void onApplicationEvent(ContextRefreshedEvent event)
  {
    lockApp();
  }
  
  @PreDestroy
  public void onApplicationClosing()
  {
    logger.error("on arrete");
    unlockApp();
  }
  
  
  private void lockApp()
  {
    String lockFile = environment.getProperty("app.lock.file", "lock/app.lock");
    
    String configFilePath = environment.getProperty("fichier.config");
    org.hsqldb.server.Server server = new org.hsqldb.server.Server();
    Path t = Paths.get(lockFile).normalize();
    Path t1 = Paths .get(lockFile).toAbsolutePath().normalize();
    
    if (!t.toString().equals(t1.toString()))
    {
      t = Paths .get(configFilePath).getParent().getParent()
                .resolve(lockFile)
                .toAbsolutePath().normalize();
    }
    
    Path lockFileP = t;
    
    try
    {
      Files.createDirectories(lockFileP.getParent());
      
      logger.info("verrouillage fichier={}", lockFileP.toAbsolutePath().toString());
      RandomAccessFile randomFile = new RandomAccessFile(lockFileP.toAbsolutePath().toString(), "rw");
      lock = randomFile.getChannel();
      
      if (lock.tryLock(0, 1, false) == null)
      {
        logger.info("il semblerait qu'une autre instance du logiciel soit exécutée");
        System.exit(CodesRetour.LOCKED.errno());
      }
      logger.info("verrouillage");
    }
    catch (IOException e1)
    {
      logger.error("il semblerait qu'une autre instance du logiciel soit executee, probleme trouve", e1);
      System.exit(CodesRetour.BAD_CONFIG.errno());
    }
  }
  
  public void unlockApp()
  {
    try {lock.close();} catch (IOException e1) {}
  }
}
