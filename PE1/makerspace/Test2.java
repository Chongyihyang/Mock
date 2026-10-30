public final class Test2 {
  private Test2() { }

  public static void main(String[] args) throws Exception {
    TestSupport t = new TestSupport();
    JobDesk desk = new JobDesk(10, 30);
    t.check("initial sheets", 10, desk.remainingSheets());
    t.check("initial minutes", 30, desk.remainingMinutes());
    t.check("initial revenue", 0, desk.totalCharged());
    WorkItem sign = new CutJob("Sign", 2, 3);
    Receipt first = desk.submit(sign);
    t.check("first id", 1, first.number());
    t.check("first label", "Sign", first.label());
    t.check("first price", 13, first.price());
    t.check("first receipt", "#1 Sign: $13", first.toString());
    t.check("sheets after first", 8, desk.remainingSheets());
    t.check("minutes after first", 23, desk.remainingMinutes());
    Receipt second = desk.submit(new PosterJob("Map", 3, 2));
    t.check("second id", 2, second.number());
    t.check("second receipt", "#2 Map: $10", second.toString());
    t.check("sheets after second", 5, desk.remainingSheets());
    t.check("minutes after second", 12, desk.remainingMinutes());
    t.check("revenue after second", 23, desk.totalCharged());
    t.check("old receipt stable", "#1 Sign: $13", first.toString());
    t.check("work description unchanged", "Sign [sheets=2, minutes=7, price=13]", sign.toString());
    Receipt repeat = desk.submit(sign);
    t.check("same object may be submitted again", "#3 Sign: $13", repeat.toString());
    t.check("repeat is charged", 36, desk.totalCharged());
    JobDesk other = new JobDesk(2, 7);
    Receipt independent = other.submit(sign);
    t.check("numbering belongs to each desk", 1, independent.number());
    t.check("exact sheets capacity", 0, other.remainingSheets());
    t.check("exact minutes capacity", 0, other.remainingMinutes());
    t.check("other desk unaffected", 3, desk.remainingSheets());
    t.check("desk construction and submission are silent", "", t.capture(() -> {
      JobDesk quiet = new JobDesk(1, 4);
      Receipt receipt = quiet.submit(new PosterJob("Quiet", 1, 1));
      receipt.number(); receipt.label(); receipt.price(); receipt.toString();
    }));
    t.check("sample output", "#1 Sign: $13\n#2 Map: $10\n5 sheets, 12 minutes, $23\n",
        t.capture(() -> {
          JobDesk sample = new JobDesk(10, 30);
          Receipt a = sample.submit(new CutJob("Sign", 2, 3));
          Receipt b = sample.submit(new PosterJob("Map", 3, 2));
          System.out.println(a); System.out.println(b);
          System.out.println(sample.remainingSheets() + " sheets, "
              + sample.remainingMinutes() + " minutes, $" + sample.totalCharged());
        }));
    t.finish();
  }
}
