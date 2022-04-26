package org.yal.test.banane.app.common;

import javax.inject.Named;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author YLE 1 oct. 2019
 * 
 */
@Named
@Configuration
public class WebMvcConfig implements WebMvcConfigurer
{
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry)
  {
    registry.addResourceHandler("/**").addResourceLocations("classpath:/static/").setCachePeriod(5);
  }
}