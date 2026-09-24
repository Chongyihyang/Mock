/** Optional scratch test driver. Add your own calls inside main. */
public class Playground {
  public static void main(String[] args) {
    DayCalendar cal = new DayCalendar("inputs/Sample.txt");
    cal.printEventDetails();
    System.out.println("Busy hours: " + cal.getBusyPeriod());
  }
}
