/** All seven observable examples from Session II. */
public final class Test1 {
  private Test1() {
  }

  public static void main(String[] args) {
    TestSupport t = new TestSupport();
    String all = "0 CS2030S | 12 - 14\n"
        + "1 Birthday (Ah Huat)\n"
        + "2 Discuss Project | 11 - 12 | Meet with Ahmad\n"
        + "3 MA2101 | 8 - 10\n"
        + "4 Breakfast | 10 - 11 | Meet with Devi\n";
    DayCalendar first = new DayCalendar("inputs/Sample.txt");
    t.check("sample descriptions", "0 CS2030S\n1 Birthday (Ah Huat)\n"
        + "2 Discuss Project\n3 MA2101\n4 Breakfast\n",
        TestSupport.capture(first::printEventDescriptions));
    t.check("sample details", all, TestSupport.capture(first::printEventDetails));
    DayCalendar cancelled = new DayCalendar("inputs/Sample.txt");
    t.check("sample meeting cancellation", "0 CS2030S | 12 - 14\n"
        + "1 Birthday (Ah Huat)\n3 MA2101 | 8 - 10\n"
        + "4 Breakfast | 10 - 11 | Meet with Devi\n",
        TestSupport.capture(() -> { cancelled.cancelEvent(2); cancelled.printEventDetails(); }));
    DayCalendar rejected = new DayCalendar("inputs/Sample.txt");
    t.check("sample rejected cancellations", "Unable to cancel event: CS2030S\n"
        + "Unable to cancel event: Birthday (Ah Huat)\n" + all,
        TestSupport.capture(() -> {
          rejected.cancelEvent(0);
          rejected.cancelEvent(1);
          rejected.printEventDetails();
        }));
    t.check("sample reminders", "0 CS2030S | 12 - 14\n"
        + "2 Discuss Project | 11 - 12 | Meet with Ahmad\n"
        + "4 Breakfast | 10 - 11 | Meet with Devi\n",
        TestSupport.capture(() -> first.remind(10)));
    t.check("sample reminders after cancellation", "0 CS2030S | 12 - 14\n"
        + "4 Breakfast | 10 - 11 | Meet with Devi\n",
        TestSupport.capture(() -> cancelled.remind(10)));
    t.check("sample busy period after cancellation", 5, cancelled.getBusyPeriod());
    t.finish();
  }
}
