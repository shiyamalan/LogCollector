package se.cambio.logcollector.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NameCreator
{
  private static String FILE_NAME_MODIFIER = "_";

  private static String FILE_NAME_EXTENSION_MODIFIER = ".";

  @SuppressWarnings("unused")
  private static String FILE_NAME_EXTENSION = "";

  private List<Integer> fileContinuousNumbers;

  public NameCreator()
  {
    fileContinuousNumbers = new ArrayList<Integer>();
  }

  private String getSplitFileName(String fileName)
  {
    String splitName = "";

    if (fileName.contains(FILE_NAME_MODIFIER))
    {
      splitName = fileName.substring(0, fileName.lastIndexOf(FILE_NAME_MODIFIER));
      splitName = getFileName(splitName, fileName);
      return splitName;
    }
    return fileName;
  }

  private String getFileName(String name, String modfyingFileName)
  {
    return name + (modfyingFileName.substring(modfyingFileName.lastIndexOf(FILE_NAME_EXTENSION_MODIFIER)));
  }

  public String getNewFileName(File fileDir, String modfyingFileName)
  {
    fileContinuousNumbers.clear();
    File files[] = fileDir.listFiles();

    for (File file : files)
    {
      String currentFileName = file.getName();
      currentFileName = getSplitFileName(currentFileName);
      fileContinuousNumbers.add(getFileContinuosNumber(file.getName(), modfyingFileName, currentFileName));
    }

    String newFileName = getModifiedFileName(modfyingFileName);

    System.out.println("New File Name is " + newFileName);
    return newFileName;
  }

  private String getModifiedFileName(String modifyingFileName)
  {
    Collections.sort(fileContinuousNumbers, new MyNumberSorter());
    int currentContinuousNumber;
    if (fileContinuousNumbers.size() > 0)
      currentContinuousNumber = fileContinuousNumbers.get(0) + 1;
    else
      currentContinuousNumber = -1;

    if (currentContinuousNumber <= 0)
      return modifyingFileName;
    return modifyingFileName.substring(0, modifyingFileName.lastIndexOf(FILE_NAME_EXTENSION_MODIFIER))
        + FILE_NAME_MODIFIER + currentContinuousNumber
        + modifyingFileName.substring(modifyingFileName.lastIndexOf(FILE_NAME_EXTENSION_MODIFIER));
  }

  public int getFileContinuosNumber(String originalFileName, String modfyingFileName, String fileName)
  {
    int endpPositionOfContinuousNumber = originalFileName.lastIndexOf(FILE_NAME_EXTENSION_MODIFIER);
    int startpPositionOfContinuousNumber = originalFileName.lastIndexOf(FILE_NAME_MODIFIER);
    if (originalFileName.equals(modfyingFileName))
    {
      return 0;
    }

    if (fileName.equals(modfyingFileName))
    {
      try
      {
        Integer i = Integer
            .parseInt(originalFileName.substring(startpPositionOfContinuousNumber + 1, endpPositionOfContinuousNumber));
        return i;
      }
      catch (NumberFormatException e)
      {
        return 0;
      }

    }

    return -1;
  }

  private static class MyNumberSorter implements Comparator<Integer>
  {
    @Override
    public int compare(Integer arg0, Integer arg1)
    {
      if (arg0.intValue() < arg1.intValue())
      {
        return 1;
      }
      else
      {
        return -1;
      }
    }
  }

  public static void main(String args[])
  {
    String filePath = "E:\\Resources\\LogCollector\\PerformanceAnalysis\\CUTTarget\\Ram\\R8P1_06_HF_IX3MED\\CambioCosmic\\log\\cuts";
    String fileName = "dummy.txt";
    new NameCreator().getNewFileName(new File(filePath), fileName);
  }
}
