public final class Test3 {
  private Test3() { }

  public static void main(String[] args) throws Exception {
    TestSupport t = new TestSupport();
    t.check("CapacityException is checked", true,
        Exception.class.isAssignableFrom(CapacityException.class)
        && !RuntimeException.class.isAssignableFrom(CapacityException.class));
    JobDesk desk = new JobDesk(2, 6);
    t.failure("sheets checked before minutes", CapacityException.class,
        "Insufficient sheets: need 3, available 2",
        () -> desk.submit(new PosterJob("Too big", 3, 1)));
    t.check("sheets unchanged after refusal", 2, desk.remainingSheets());
    t.check("minutes unchanged after refusal", 6, desk.remainingMinutes());
    t.check("revenue unchanged after refusal", 0, desk.totalCharged());
    t.failure("minutes checked when sheets fit", CapacityException.class,
        "Insufficient minutes: need 7, available 6",
        () -> desk.submit(new CutJob("Too slow", 2, 3)));
    t.check("no partial sheets deduction", 2, desk.remainingSheets());
    t.check("no partial minutes deduction", 6, desk.remainingMinutes());
    Receipt first = desk.submit(new CutJob("Small", 1, 1));
    t.check("refusals do not consume receipt numbers", "#1 Small: $6", first.toString());
    t.check("revenue counts only success", 6, desk.totalCharged());
    t.failure("refusal uses current capacity", CapacityException.class,
        "Insufficient minutes: need 4, available 3",
        () -> desk.submit(new PosterJob("Later", 1, 1)));
    Receipt second = desk.submit(new CutJob("Last", 1, 0));
    t.check("success after refusal", "#2 Last: $5", second.toString());
    t.check("final sheets", 0, desk.remainingSheets());
    t.check("final minutes", 1, desk.remainingMinutes());
    t.check("final revenue", 11, desk.totalCharged());
    t.check("old receipt survives refusals", "#1 Small: $6", first.toString());
    t.check("failure is thrown without printing", "", t.capture(() -> {
      try {
        new JobDesk(0, 0).submit(new CutJob("No room", 1, 0));
        throw new AssertionError("Missing CapacityException");
      } catch (CapacityException expected) {
        // The client handles the refusal; the desk must not print it.
      }
    }));
    t.check("sample refusal output", "Insufficient minutes: need 7, available 6\n"
        + "2 sheets, 6 minutes, $0\n#1 Small: $6\n", t.capture(() -> {
          JobDesk sample = new JobDesk(2, 6);
          try {
            sample.submit(new CutJob("Sign", 2, 3));
          } catch (CapacityException e) {
            System.out.println(e.getMessage());
          }
          System.out.println(sample.remainingSheets() + " sheets, "
              + sample.remainingMinutes() + " minutes, $" + sample.totalCharged());
          System.out.println(sample.submit(new CutJob("Small", 1, 1)));
        }));
    t.finish();
  }
}
