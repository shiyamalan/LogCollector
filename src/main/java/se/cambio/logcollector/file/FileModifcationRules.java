package se.cambio.logcollector.file;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import se.cambio.logcollector.objects.NameCreator;

public class FileModifcationRules
{

  public final static String OVERRIDE = "Override";

  public final static String KEEPBOTH = "KeepBoth";

  public static String size_more_than = "2KB";

  public static Map<String, Double> sizes;

  static
  {
    sizes = new HashMap<String, Double>();
    String size_string[] = { "B", "KB", "MB", "GB", "TB", "PB" };
    double value = 1 / (1024.0);
    for (String str : size_string)
    {

      sizes.put(str, value);
      value *= 1024;
    }
  }

  public static File processFileCopyRule(File source, File target)
  {
    if (target.exists())
    {
      if (PropertyReader.getPorpsValue(5).equalsIgnoreCase(OVERRIDE))
      {
        FileOperation.deleteFile(target);
        FileOperation.createAFile(target);
      }
      else
      {
        File targetRoot = getRootDirectoryFor(target);
        String newName = new NameCreator().getNewFileName(targetRoot, target.getName());
        if (source.exists())
          target = new File(targetRoot, newName);
      }
    }
    return target;
  }

  public static File getRootDirectoryFor(File file)
  {
    if (file == null)
      return null;
    return new File(file.getPath().replace(file.getName(), ""));
  }

  public static boolean canDeleteFile()
  {
    try
    {
      if (Boolean.parseBoolean(PropertyReader.getPorpsValue(2)))
        return true;
    }
    catch (Exception e)
    {
      return false;
    }

    return false;
  }

  public static boolean canCopy(File file)
  {
    size_more_than = PropertyReader.getPorpsValue(6);
    double size_specified = Double.parseDouble(getSizeInString(size_more_than));

    if (size_specified <= getSizeInKB(file))
      return true;
    return false;
  }

  private static double getSizeInKB(File file)
  {
    double size_in_kb = 0;
    if (file.exists())
    {

      double bytes = file.length();
      size_in_kb = (bytes / 1024);
    }
    return size_in_kb;
  }

  private static String getSizeInString(String sizeStr)
  {
    sizeStr = sizeStr.toUpperCase();
    String str[] = sizeStr.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

    if (str != null && str.length > 0)
    {
      String sizeNumber = str[0];
      String sizePostfix = str[1];

      if (sizes.containsKey(sizePostfix))
      {
        return Double.toString(Double.parseDouble(sizeNumber) * sizes.get(sizePostfix));
      }
    }
    return "0";
  }
}
