package se.cambio.logcollector;

import java.io.File;

import org.apache.log4j.Logger;

import se.cambio.logcollector.datetime.TimeProperty;
import se.cambio.logcollector.file.Path;
import se.cambio.logcollector.file.PropertyReader;
import se.cambio.logcollector.objects.ElementSize;
import se.cambio.logcollector.objects.StatusMessageHandler;
import se.cambio.logcollector.objects.TaskHandler;
/**
 * Hello world!
 *
 */
public class App
{

  public static File root_folder;

  public static String[] directories;
  
  public final static Logger logger = Logger.getLogger(App.class);
  
  static
  {

    
    root_folder = new File(Path.addPathSeparator(PropertyReader.getPorpsValue(0)));

    directories = Path.getSubDirctives(root_folder);
    
    logger.info("Directies are printing----"  + directories.toString());
    
  }

  public static void main(String[] args)
  {
    logger.info("**************************Log Collector started****************************");
    System.out.println("Collecting the log files.....");
    if (!TimeProperty.canRunProgram())
    {
      logger.info("System current time is " + TimeProperty.getCurrentSystemTime());
      logger.info("Ststem can run only less than " + TimeProperty.START_TIME_STRING + " and above  "
          + TimeProperty.END_TIME_STRING);
      return;
    }
    
    logger.info("waitting for starting the task.....");
    int threadSize = ElementSize.getThreadSize(directories);
    
    logger.info("Thread Size "+threadSize);
    
    Thread tasks[] = new Thread[threadSize];
    TaskHandler.createThreadTask(tasks, directories);
    
    logger.info("copying task started.....");
    TaskHandler.waitForTaskCompletion(tasks);
    StatusMessageHandler.printStatus();
    
    logger.info("Collecting log files task completed\n");
    System.out.println("Collecting log files task completed");
  }

}
