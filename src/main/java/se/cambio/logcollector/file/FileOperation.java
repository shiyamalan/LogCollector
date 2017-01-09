package se.cambio.logcollector.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import se.cambio.logcollector.LogFileCopier;
import se.cambio.logcollector.objects.DataManagement;

public class FileOperation
{

  public void copy(File sourceLocation, File targetLocation) throws IOException
  {
    if (sourceLocation.isDirectory())
    {
      copyDirectory(sourceLocation, targetLocation);
    }
    else
    {
      if (FileModifcationRules.canCopy(sourceLocation))
        copyFile(sourceLocation, targetLocation);
    }
  }

  private void copyDirectory(File source, File target) throws IOException
  {
    if (!target.exists())
    {
      target.mkdirs();
    }

    for (String f : source.list())
    {
      copy(new File(source, f), new File(target, f));
    }
  }

  private void copyFile(File source, File target) throws IOException
  {
    System.out.println("Copying the file ........");
    InputStream in = null;
    OutputStream out = null;
    try
    {
      if(!target.getName().contains(PathTraverser.TRAVISING_STOPPED_POINTER_NAME))
        return;
      File targetDir = new File(target.getAbsolutePath().replace(target.getName(), ""));
      if (!targetDir.exists())
        targetDir.mkdirs();
      in = new FileInputStream(source);
      out = new FileOutputStream(target);

      byte[] buf = new byte[1024];
      int length;
      while ((length = in.read(buf)) > 0)
      {
        out.write(buf, 0, length);
      }
    }
    catch (IOException e)
    {
      DataManagement.addDataTo(DataManagement.failledLogFileLocations, source.getPath(),
          "Failled in copying file " + e.toString());
    }
    finally
    {
      if (in != null)
        in.close();
      if (out != null)
        out.close();
    }
    if (FileModifcationRules.canDeleteFile())
    {
      if (!delete(source))
      {
        delete(target);
        return;
      }
    }
    DataManagement.addDataTo(DataManagement.successLogFileLocations, source.getPath(),
        "Successfully Copied the File Name " + source.getName() + " to the directory "
            + FileModifcationRules.getRootDirectoryFor(target));
  }

  public static void createAFile(File target)
  {
    try
    {
      target.createNewFile();
    }
    catch (Exception e)
    {
      System.out.println("-----------------Error in Creating the file--------------");
      System.out.println("File is creating in location:- " + target.getPath() + " " + e.getMessage());
    }
  }

  public static void deleteFile(File file)
  {
    if (file.exists())
    {
      File[] files = file.listFiles();
      if (null != files)
      {
        for (int i = 0; i < files.length; i++)
        {
          if (files[i].isDirectory())
          {
            deleteFile(files[i]);
          }
          else
          {
            delete(files[i]);
          }
        }
      }
    }
  }

  private static boolean delete(File file)
  {
    Path path = Paths.get(file.getPath());

    try
    {
      Files.delete(path);

      DataManagement.addDataTo(DataManagement.successFullDeletedDirs, file.getPath(),
          "The file " + file.getPath() + " deleted Successfully");
      return true;
    }
    catch (IOException e)
    {
      synchronized (LogFileCopier.class)
      {
        DataManagement.addDataTo(DataManagement.failledDeletedDirs, file.getPath(),
            "Failled  in deletion " + e.toString());
      }
      //      if (file.isDirectory())
      //        System.out.println("-----------------Error in Deleting the Folder--------------");
      //      else
      //        System.out.println("-----------------Error in Deleting the File--------------");
      //     System.out.println("Source File Path:-" + file.getPath() + "  " + e.getMessage());
      return false;
    }
  }

}
