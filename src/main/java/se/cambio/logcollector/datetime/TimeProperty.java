package se.cambio.logcollector.datetime;

import java.text.SimpleDateFormat;
import java.util.Date;

import se.cambio.logcollector.file.PropertyReader;

public class TimeProperty
{
  @SuppressWarnings("unused")
  private static String time_status = "AM";
  
  private static final int MINUTES = 60;

  private static final int START_TIME_ARRAY_INDEX = 3;

  private static final int END_TIME_ARRAY_INDEX = 4;
  
  public static final String START_TIME_STRING = PropertyReader.getPorpsValue(START_TIME_ARRAY_INDEX);
  
  public static final String END_TIME_STRING = PropertyReader.getPorpsValue(END_TIME_ARRAY_INDEX);

  private static int getMiniutes(String timeString)
  {
    if (timeString == null || timeString.equals(""))
      return 0;
    String elements[] = timeString.split(":");
    int hours = Integer.parseInt(elements[0]);
    int minutes = Integer.parseInt(elements[1]);

    return hours * MINUTES + minutes;
  }

  public static boolean canRunProgram()
  {
    String startTimeString = START_TIME_STRING;
    String endTimeString = END_TIME_STRING;
    String currentTimeString = getCurrentSystemTime();

    if (getMiniutes(currentTimeString) < getMiniutes(startTimeString)
        || getMiniutes(currentTimeString) > getMiniutes(endTimeString))
      return true;
    return false;
  }

  public static String getCurrentSystemTime()
  {
    Date dNow = new Date();
    SimpleDateFormat ft = new SimpleDateFormat("hh:mm");

    return ft.format(dNow);
  }
  
//  public static void main(String args[])
//  {
//    System.out.println(getCurrentSystemTime());
//  }
}
