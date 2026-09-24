/** Additional behavioural checks; no hierarchy or helper signatures prescribed. */
public final class Test2 {
  private Test2() {
  }

  public static void main(String[] args) {
    TestSupport t = new TestSupport();
    DayCalendar cal = new DayCalendar("inputs/Boundaries.txt");
    String timed = "0 Quick check | 9 - 9 | Meet with Li Wei\n"
        + "2 Studio | 10 - 12\n3 Review | 14 - 17 | Meet with Ana\n";
    t.check("zero-duration event contributes zero hours", 5, cal.getBusyPeriod());
    t.check("reminder includes exact start and skips birthdays", timed,
        TestSupport.capture(() -> cal.remind(9)));
    t.check("reminder before all timed events", timed,
        TestSupport.capture(() -> cal.remind(1)));
    t.check("already-started lesson is excluded even while ongoing",
        "3 Review | 14 - 17 | Meet with Ana\n", TestSupport.capture(() -> cal.remind(11)));
    t.check("meeting at exact start is included",
        "3 Review | 14 - 17 | Meet with Ana\n", TestSupport.capture(() -> cal.remind(14)));
    t.check("ongoing meeting is excluded", "", TestSupport.capture(() -> cal.remind(15)));
    t.check("after all start times", "", TestSupport.capture(() -> cal.remind(23)));
    t.check("successful cancellation is silent", "", TestSupport.capture(() -> cal.cancelEvent(3)));
    t.check("cancelled meeting contributes no hours", 2, cal.getBusyPeriod());
    t.check("repeated cancellation is silent", "", TestSupport.capture(() -> cal.cancelEvent(3)));
    t.check("repeated cancellation does not subtract twice", 2, cal.getBusyPeriod());
    t.check("descriptions retain original indices", "0 Quick check\n1 Birthday (Mei Ling)\n2 Studio\n",
        TestSupport.capture(cal::printEventDescriptions));
    t.check("details omit cancelled meetings", "0 Quick check | 9 - 9 | Meet with Li Wei\n"
        + "1 Birthday (Mei Ling)\n2 Studio | 10 - 12\n", TestSupport.capture(cal::printEventDetails));
    t.check("cancelled meeting is not reminded", "", TestSupport.capture(() -> cal.remind(14)));
    t.check("lesson cancellation reports description only", "Unable to cancel event: Studio\n",
        TestSupport.capture(() -> cal.cancelEvent(2)));
    t.check("birthday cancellation uses birthday description",
        "Unable to cancel event: Birthday (Mei Ling)\n",
        TestSupport.capture(() -> cal.cancelEvent(1)));
    t.check("failed cancellation preserves busy period", 2, cal.getBusyPeriod());
    t.check("failed cancellation preserves reminder", "2 Studio | 10 - 12\n",
        TestSupport.capture(() -> cal.remind(10)));
    DayCalendar independent = new DayCalendar("inputs/Boundaries.txt");
    t.check("calendars have independent state", 5, independent.getBusyPeriod());
    DayCalendar birthdays = new DayCalendar("inputs/Birthdays.txt");
    t.check("birthday-only calendar busy period", 0, birthdays.getBusyPeriod());
    t.check("birthday-only calendar has no timed reminders", "",
        TestSupport.capture(() -> birthdays.remind(1)));
    DayCalendar meetings = new DayCalendar("inputs/Meetings.txt");
    t.check("all meetings can be cancelled silently", "", TestSupport.capture(() -> {
      meetings.cancelEvent(0);
      meetings.cancelEvent(1);
    }));
    t.check("all cancelled descriptions empty", "", TestSupport.capture(meetings::printEventDescriptions));
    t.check("all cancelled details empty", "", TestSupport.capture(meetings::printEventDetails));
    t.check("all cancelled reminders empty", "", TestSupport.capture(() -> meetings.remind(1)));
    t.check("all cancelled busy period zero", 0, meetings.getBusyPeriod());
    DayCalendar stdin = TestSupport.fromInput("2\n0,Jean Tan\n2,Pair work,13,15,Sam Lee\n");
    t.check("stdin constructor preserves spaces", "0 Birthday (Jean Tan)\n"
        + "1 Pair work | 13 - 15 | Meet with Sam Lee\n", TestSupport.capture(stdin::printEventDetails));
    t.check("stdin calendar busy period", 2, stdin.getBusyPeriod());
    t.finish();
  }
}
