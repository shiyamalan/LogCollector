package se.cambio.logcollector.objects;

import java.util.HashMap;
import java.util.Map;

import se.cambio.logcollector.LogFileCopier;

public class DataManagement
{
  public static Map<String, String> successFullDeletedDirs;

  public static Map<String, String> failledDeletedDirs;

  public static Map<String, String> successLogFileLocations;

  public static Map<String, String> failledLogFileLocations;


  static
  {
    successFullDeletedDirs = new HashMap<String, String>();
    failledDeletedDirs = new HashMap<String, String>();
    successLogFileLocations = new HashMap<String, String>();
    failledLogFileLocations = new HashMap<String, String>();

  }

  public static String[] getPartianArray(int startPos, int endPos, String directories[])
  {
    int size = ElementSize.getPartianArraySize(directories);
    int index = 0;
    String result[] = new String[size];

    for (int i = startPos; i < endPos; i++)
    {
      result[index++] = directories[i];
    }
    return result;
  }

  public static void addDataTo(Map<String, String> dataMap, String key, String value)
  {
    synchronized (LogFileCopier.class)
    {
      dataMap.put(key, value);
    }
  }
}
