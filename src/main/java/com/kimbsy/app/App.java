package com.kimbsy.app;

/**
 * The main app class.
 * 
 * @author Dave Kimber <github.com/Kimbsy>
 */
public class App 
{

  /**
   * The main function.
   *
   * @param      args  The command line arguments.
   */
  public static void main(String[] args)
  {
    App.importData();
  }

  /**
   * Initialises the Importer and starts the import.
   */
  public static void importData()
  {
    Importer importer = new Importer();
    importer.start();
  }

}
