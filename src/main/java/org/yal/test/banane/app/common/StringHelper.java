package org.yal.test.banane.app.common;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author YL
 * 15 aout 2009
 * 
 */
public class StringHelper
{
  private final static Logger logger = LoggerFactory.getLogger(StringHelper.class);
  
  public static final String LINE_SEPARATOR = System.getProperty("line.separator");
  public static final String CRLF = "\r\n";
  
  static public boolean isNotEmptyOrNull(String value) { return value != null && value.length() > 0; }
  static public boolean isEmptyOrNull(String value) { return value == null || value.length()==0; }
  public static void parametreNonNull(String name, Object o)
  {
    if (o instanceof String && StringHelper.isEmptyOrNull((String) o))
      throw new NullPointerException(String.format("le parametre est une String et %s est vide (alors qu'il ne devrait pas)", name));
    
    Objects.requireNonNull(o, String.format("le parametre %s est null (alors qu'il ne devrait pas)", name));
  }
}

