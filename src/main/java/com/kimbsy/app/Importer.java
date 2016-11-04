package com.kimbsy.app;

import java.io.*;
import java.util.*;
import java.text.*;

/**
 * This class is responsible for importing data from csv files in the data directory.
 * 
 * @author Dave Kimber <github.com/Kimbsy>
 */
public class Importer
{

  /**
   * The file to import from.
   */
  protected File inputFile;

  /**
   * The master file of entry data.
   */
  protected File masterFile;

  /**
   * The ConfigManager object.
   */
  protected ConfigManager configManager;

  /**
   * Constructs the object.
   * 
   * Sets the reference to the master entry record file and instantiates the ConfigManager.
   */
  public Importer()
  {
    System.out.println("Importer initialized.");

    System.out.println("Accessing master entry record...");
    this.setMasterFile(new File("data/master.csv"));

    System.out.println("Parsing configuration files...");
    this.configManager = new ConfigManager();
  }

  /**
   * Gets the input file to import from.
   *
   * @return     The input file to import from.
   */
  protected File getInputFile()
  {
    return this.inputFile;
  }

  /**
   * Sets the input file to import from.
   *
   * @param      file  The input file to import from.
   *
   * @return     The Importer object.
   */
  protected Importer setInputFile(File inputFile)
  {
    this.inputFile = inputFile;
    return this;
  }

  /**
   * Gets the master file to write to.
   *
   * @return     The master file to write to.
   */
  protected File getMasterFile()
  {
    return this.masterFile;
  }

  /**
   * Sets the master file to write to.
   *
   * @param      file  The master file to write to.
   *
   * @return     The Importer object.
   */
  protected Importer setMasterFile(File masterFile)
  {
    this.masterFile = masterFile;
    return this;
  }

  /**
   * Starts importing from all files in the input directory.
   */
  public void start()
  {

    System.out.println("Starting import...");

    File inputDirectory = new File("data/input");
    File[] inputFiles   = inputDirectory.listFiles();

    if (inputFiles.length > 0) {

      Arrays.sort(inputFiles);

      for (int i = 0; i < inputFiles.length; i++) {

        File inputFile = inputFiles[i];

        if (inputFile.isFile()) {
          System.out.println("File: " + inputFile.getName());

          this.setInputFile(inputFile).importFromFile();
        }
      }

      System.out.println("Removing input files...");
      this.removeFiles(inputFiles);
    } else {
      System.out.println("No input files found.");
    }
  }

  /**
   * Imports data from the object's inputFile.
   */
  protected void importFromFile()
  {
    System.out.println("Importing " + this.getInputFile().getName() + "...");

    String line = "";

    try (BufferedReader br = new BufferedReader(new FileReader(this.getInputFile()))) {

      while ((line = br.readLine()) != null) {

        Date date;
        String[] lineData = line.split(",");

        // Ensure this line has a valid date.
        if ((date = this.getDateFromLineData(lineData)) != null) {
          
          // Check if we've imported this entry before.
          if (this.entryIsNew(lineData)) {

            System.out.println(line);

            // Import the entry.
            Entry entry = new Entry();

            entry.setDate(date)
              .setDescription(lineData[1])
              .setType(lineData[2])
              .setChange(this.getFloat(lineData[3]) - this.getFloat(lineData[4]))
              .setBalance(this.getFloat(lineData[5]));

            Requester.sendEntry(this.configManager.getProperty("ip_addr"), entry);

            // Save the entry to the master record.
            this.writeToMaster(lineData);
          }

        }

      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets the date from the line data array.
   *
   * @param      lineData  The line data array.
   *
   * @return     The date object.
   */
  protected Date getDateFromLineData(String[] lineData)
  {
    String dateField = lineData[0];

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    Date date;

    try {
      date = format.parse(dateField);
      return date;
    } catch (ParseException e) {
      System.out.println("[PARSE WARNING] Ignoring line beginning with \"" + dateField + "\".");
    }

    return null;
  }

  /**
   * Checks that the entry has not been imported before by checking the master record.
   *
   * @param      entryLineData  The line data array for the entry.
   *
   * @return     Whether the entry is new.
   */
  protected boolean entryIsNew(String[] entryLineData)
  {
    String line      = "";
    boolean hasMatch = false;
    
    try (BufferedReader br = new BufferedReader(new FileReader(this.getMasterFile()))) {

      while (!hasMatch && (line = br.readLine()) != null) {

        String[] latestLineData = line.split(",");

        if (entryLineData.length == latestLineData.length) {

          boolean isMatch = true;

          for (int i = 0; i < entryLineData.length; i++) {
            if (!entryLineData[i].equals(latestLineData[i])) {
              isMatch = false;
            }
          }


          if (isMatch) {
            hasMatch = true;
          }

        }

      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    return !hasMatch;
  }

  /**
   * Writes the Entry data to the master csv.
   *
   * @param      lineData  The line data array for the entry.
   */
  protected void writeToMaster(String[] lineData)
  {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.getMasterFile(), true))) {

      String line = String.join(",", lineData);

      writer.write(line);
      writer.newLine();
      writer.flush();

    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  /**
   * Gets a float value counting empty strings as zero.
   *
   * @param      input  The input.
   *
   * @return     The float value.
   */
  protected float getFloat(String input)
  {
    return (input.length() > 0) ? Float.parseFloat(input) : 0;
  }

  /**
   * Deletes files.
   *
   * @param      files  The files to delete.
   */
  protected void removeFiles(File[] files)
  {
    for (int i =0; i < files.length; i++) {
      if (files[i].delete()) {
        System.out.println(files[i] + " successfully deleted.");
      } else {
        System.out.println("[ERROR] failed to delete file: " + files[i]);
      }
    }
  }

}
