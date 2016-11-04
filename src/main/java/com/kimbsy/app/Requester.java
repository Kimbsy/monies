package com.kimbsy.app;

import java.io.*;
import java.net.*;
import java.text.*;
import org.json.simple.JSONObject;

/**
 * This class is responsible for sending data about Entry objects to Elasticsearch.
 * 
 * @author Dave Kimber <github.com/Kimbsy>
 */
public class Requester
{

  /**
   * Sends the data for an Entry.
   *
   * @param      ipAddress  The IP address of the Elasticsearch server.
   * @param      entry      The entry object.
   */
  public static void sendEntry(String ipAddress, Entry entry)
  {

    try {

      URL url               = new URL("http://" + ipAddress + ":9200/test/thing/");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      JSONObject data       = new JSONObject();

      System.out.println(entry.getDate().getTime());

      data.put("entry_date", entry.getDate().getTime());
      data.put("description", entry.getDescription());
      data.put("entry_type", entry.getType());
      data.put("change", entry.getChange());
      data.put("balance", entry.getBalance());

      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json");
      con.setRequestProperty("Content-Language", "en-US");
      con.setUseCaches(false);
      con.setDoOutput(true);

      OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
      writer.write(data.toString());
      writer.flush();

      InputStream in         = con.getInputStream();
      BufferedReader reader  = new BufferedReader(new InputStreamReader(in));
      StringBuilder response = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        response.append(line);
        response.append('\r');
      }

      System.out.println(response);

      reader.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
