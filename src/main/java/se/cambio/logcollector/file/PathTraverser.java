package se.cambio.logcollector.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PathTraverser
{
  public static final String TRAVISING_STOPPED_POINTER_NAME = PropertyReader.getPorpsValue(7);//"cuts";

  public List<String> cutsLocations;

  public boolean isFound = false;

  public PathTraverser()
  {

    cutsLocations = new ArrayList<String>();
  }

  private boolean canStop(File file)
  {
    boolean canStop = false;
    if (TRAVISING_STOPPED_POINTER_NAME.contains("."))
    {
      if (file.getName().contains(TRAVISING_STOPPED_POINTER_NAME))
        return true;
    }
    if (file.getName().equals(TRAVISING_STOPPED_POINTER_NAME))
      canStop = true;
    return canStop;
  }

  private String getFileLocation(File file)
  {
    if (TRAVISING_STOPPED_POINTER_NAME.equals("."))
    {
      return file.getAbsolutePath().replace(file.getName(), "");
    }
    else
    {
      return file.getAbsolutePath();
    }
  }

  public String findDir(File root)
  {
    if (canStop(root))
    {
      cutsLocations.add(getFileLocation(root));
      isFound = true;
    }

    File[] files = root.listFiles();

    if (files != null)
    {
      for (File f : files)
      {
        String myResult = findDir(f);
        if (myResult == null)
        {
          continue;
        }
        else
        {
          return myResult;
        }

      }
    }
    return null;
  }

  public List<String> getCutsLocations()
  {
    return this.cutsLocations;
  }

  public String getLocation(int position)
  {
    if (position >= 0 && position >= cutsLocations.size())
      return this.cutsLocations.get(position);
    return null;
  }

  public static void main(String args[])
  {
    PathTraverser traverser = new PathTraverser();
    traverser.findDir(new File("\\\\cwlk-srishiyama\\codebrag\\CUTFiles"));
    System.out.println(traverser.cutsLocations);
  }
}
