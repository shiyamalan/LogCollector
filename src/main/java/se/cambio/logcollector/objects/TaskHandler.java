package se.cambio.logcollector.objects;

import se.cambio.logcollector.LogFileCopier;

public class TaskHandler
{
  public static void createThreadTask(Thread[] tasks, String directories[])
  {
    System.out.println("Task is running........");
    for (int i = 0; i < tasks.length; i++)
    {
      LogFileCopier copier = new LogFileCopier();

      int startPos = 0;
      int endPos = 0;
      System.out.println("Task is starting........");
      startPos = i * ElementSize.MAX_THREADS;
      endPos = ElementSize.MAX_THREADS + startPos;
      if (endPos > directories.length)
        endPos = directories.length;
      if (ElementSize.getThreadSize(directories) == directories.length)
      {
        startPos = i;
        endPos = startPos + 1;
      }
      copier.directories = DataManagement.getPartianArray(startPos, endPos,directories);
      tasks[i] = new Thread(copier);
      tasks[i].start();
    }
  }

  public static void waitForTaskCompletion(Thread[] tasks)
  {
    for (int i = 0; i < tasks.length; i++)
      try
      {
        tasks[i].join();
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
  }
}
