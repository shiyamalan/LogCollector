package se.cambio.logcollector;

import java.io.File;
import java.io.IOException;

import se.cambio.logcollector.file.FileOperation;
import se.cambio.logcollector.file.Path;
import se.cambio.logcollector.file.PathTraverser;
import se.cambio.logcollector.file.PropertyReader;
import se.cambio.logcollector.objects.DataManagement;

public class LogFileCopier implements Runnable
{

  public String directories[];

  private String target_root_directory = "";

  private File root_folder = App.root_folder;

  private void makeModifytheFolders()
  {
    System.out.println("Task is traversing log location........");
    target_root_directory = Path.addPathSeparator(PropertyReader.getPorpsValue(1));
    for (String dir : directories)
    {
      PathTraverser traverser = new PathTraverser();
      File f = new File(root_folder.getPath(), dir);
      traverser.findDir(f);
      
      for(String str : traverser.cutsLocations)
        copyProcess(f, str);
    }
  }

  private void copyProcess(File f, String str)
  {
    System.out.println("Task is Copying the files to target dir........");
    if (str == null)
    {
      String value = "Failled Because of " + PathTraverser.TRAVISING_STOPPED_POINTER_NAME
          + " directory does not exists in " + f.getPath();
      String key = f.getPath();
      DataManagement.addDataTo(DataManagement.failledLogFileLocations, key, value);
      return;
    }
    String replacedPath = str.replace(root_folder.getPath(), "");

    FileOperation fileCopy = new FileOperation();
    File targetDir = new File(Path.addPathSeparator(target_root_directory).concat(replacedPath.substring(1)));
    try
    {
      fileCopy.copy(new File(str), targetDir);

      if (Boolean.parseBoolean(PropertyReader.getPorpsValue(2)))
        FileOperation.deleteFile(new File(str));
    }
    catch (IOException e)
    {
      System.out.println("Error\n"  + " " + e.getMessage());
    }
  }

  public void run()
  {
    makeModifytheFolders();

  }

}
