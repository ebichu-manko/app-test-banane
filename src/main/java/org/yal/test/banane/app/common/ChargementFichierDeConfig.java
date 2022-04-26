package org.yal.test.banane.app.common;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ChargementFichierDeConfig implements ApplicationContextInitializer<ConfigurableApplicationContext>
{
  private final static Logger logger = LoggerFactory.getLogger(ChargementFichierDeConfig.class);
  private   String[] args;
  
  public ChargementFichierDeConfig(String[] args)
  {
    this.args = args;
  }
  
  public void initialize(ConfigurableApplicationContext configurableApplicationContext)
  {
    try
    {
//      Resource resource = configurableApplicationContext.getResource("classpath:configpropscustom.json");
      Path fichierConfig = Paths.get(args[0]);
      
      Map readValue = new ObjectMapper().readValue(Files.newInputStream(fichierConfig), Map.class);
      Set<Map.Entry> set = readValue.entrySet();
      List<MapPropertySource> propertySources = set.stream()
                                                   .map(entry -> new MapPropertySource(""+entry.getKey(), Collections.singletonMap(""+entry.getKey(), entry.getValue())))
                                                   .collect(Collectors.toList());
      for (PropertySource propertySource : propertySources)
      {
        configurableApplicationContext.getEnvironment().getPropertySources().addFirst(propertySource);
      }
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }
}
