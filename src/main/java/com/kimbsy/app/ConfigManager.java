package com.kimbsy.app;

import java.io.*;
import java.util.*;

/**
 * This class is responsible for getting parameters from the config.properties file.
 * 
 * @author Dave Kimber <github.com/Kimbsy>
 */
public class ConfigManager
{

  /**
   * The file to get parameters from.
   */
  protected File configFile;

  /**
   * Constructs the object.
   * 
   * Sets the reference to the config file.
   */
  public ConfigManager()
  {
    this.setConfigFile(new File("resources/config/config.properties"));
  }

  /**
   * Gets the configuration file to read from.
   *
   * @return     The configuration file to read from.
   */
  protected File getConfigFile()
  {
    return this.configFile;
  }

  /**
   * Sets the configuration file to read from.
   *
   * @param      configFile  The configuration file to read from.
   *
   * @return     The ConfigManager object.
   */
  protected ConfigManager setConfigFile(File configFile)
  {
    this.configFile = configFile;
    return this;
  }

  /**
   * Gets the value of a property. 
   *
   * @param      propertyName  The property name.
   *
   * @return     The property value.
   */
  public String getProperty(String propertyName)
  {
    Properties properties = new Properties();
    InputStream input     = null;
    String output         = "";

    try {
      input = new FileInputStream(this.getConfigFile());
      properties.load(input);
      output = properties.getProperty(propertyName);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    }

    return output;
  }
}
