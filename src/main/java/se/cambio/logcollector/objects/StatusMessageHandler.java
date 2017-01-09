package se.cambio.logcollector.objects;

import java.util.Map;

import org.apache.log4j.Logger;

import se.cambio.logcollector.App;

public class StatusMessageHandler
{
  final static Logger logger = App.logger;

  final static String NEW_LINE_FORMAT = "\r\n\t";

  final static String HEADER_FORMAT = "***************************************************";

  public static void printMsg(Map<String, String> locations, String message)
  {
    System.out.println("Totally " + locations.size() + " File " + message + "\n");
  }

  public static boolean sendMail(Map<String, String> contents)
  {
    return false;
  }

  public static void printStatus()
  {
    System.out.println("---------Successful Copied Locations----------- ");
    StatusMessageHandler.printMsg(DataManagement.successLogFileLocations, "Copied Successfully");
    System.out.println("---------Failled Copied Locations----------- ");
    StatusMessageHandler.printMsg(DataManagement.failledLogFileLocations, "Failled in copying!");

    System.out.println("---------Successful Deleted Locations----------- ");
    StatusMessageHandler.printMsg(DataManagement.successFullDeletedDirs, " Deleted Successfully");
    System.out.println("---------Failled Delete Locations----------- ");
    StatusMessageHandler.printMsg(DataManagement.failledDeletedDirs, " Failled in deleting!");

    writetoLog();
  }

  private static boolean isEmpty(Map<String, String> locations)
  {
    return locations.size() == 0 ? true : false;
  }

  private static void writetoLog()
  {
    collectDetails(DataManagement.successLogFileLocations, false, "Files Copied");
    collectDetails(DataManagement.failledLogFileLocations, true, "Error copying files");
    collectDetails(DataManagement.successFullDeletedDirs, false, "Files Deleted");
    collectDetails(DataManagement.failledDeletedDirs, true, "Error deleting files");

    logger.info("Total Copied Files:- " + DataManagement.successLogFileLocations.size() + " Files");
    logger.info("Total Failled Files in Copying :- " + DataManagement.failledLogFileLocations.size() + " Files");
    logger.info("Total Deleted Files:- " + DataManagement.successFullDeletedDirs.size() + " Files");
    logger.info("Total Failled Files in Deletion:- " + DataManagement.failledDeletedDirs.size() + " Files");
  }

  private static void collectDetails(Map<String, String> infos, boolean isFailled, String msg)
  {
    String logMessage = NEW_LINE_FORMAT + HEADER_FORMAT + NEW_LINE_FORMAT + msg + NEW_LINE_FORMAT + HEADER_FORMAT
        + NEW_LINE_FORMAT;

    for (Map.Entry<String, String> info : infos.entrySet())
    {
      logMessage += info.getKey() + NEW_LINE_FORMAT;
    }

    if (!isEmpty(infos))
    {
      if (isFailled)
        logger.error(logMessage);
      else
        logger.info(logMessage);
    }

  }
}
