public final class Test4 {
  private Test4() { }

  public static void main(String[] args) throws Exception {
    TestSupport t = new TestSupport();
    WorkItem sign = new CutJob("Sign", 2, 3);
    WorkItem map = new PosterJob("Map", 3, 2);
    WorkItem[] input = { sign, map };
    WorkItem fair = new JobBundle("Fair", input);
    t.check("bundle sheets", 5, fair.sheets());
    t.check("bundle minutes", 18, fair.minutes());
    t.check("bundle discount", 21, fair.price());
    t.check("bundle label", "Fair", fair.label());
    t.check("bundle display", "Fair [sheets=5, minutes=18, price=21]", fair.toString());
    input[0] = new CutJob("Replacement", 9, 9);
    t.check("input replacement cannot change sheets", 5, fair.sheets());
    t.check("input replacement cannot change minutes", 18, fair.minutes());
    t.check("input replacement cannot change price", 21, fair.price());
    JobDesk desk = new JobDesk(6, 22);
    Receipt receipt = desk.submit(fair);
    t.check("bundle gets one receipt", "#1 Fair: $21", receipt.toString());
    t.check("bundle consumes aggregate sheets", 1, desk.remainingSheets());
    t.check("bundle consumes aggregate minutes", 4, desk.remainingMinutes());
    t.check("bundle charged once", 21, desk.totalCharged());
    t.check("next submission gets next number", "#2 Extra: $4",
        desk.submit(new PosterJob("Extra", 1, 1)).toString());
    WorkItem nested = new JobBundle("Whole fair", new WorkItem[] {
      fair, new PosterJob("Extra", 1, 1)
    });
    t.check("nested sheets", 6, nested.sheets());
    t.check("nested minutes", 22, nested.minutes());
    t.check("discount applies at each bundle", 23, nested.price());
    t.check("nested bundle submission", "#1 Whole fair: $23",
        new JobDesk(6, 22).submit(nested).toString());
    WorkItem empty = new JobBundle("Empty", new WorkItem[0]);
    t.check("empty bundle sheets", 0, empty.sheets());
    t.check("empty bundle minutes", 0, empty.minutes());
    t.check("empty bundle price floor", 0, empty.price());
    t.check("empty bundle display", "Empty [sheets=0, minutes=0, price=0]", empty.toString());
    t.check("empty bundle can be accepted", "#1 Empty: $0", new JobDesk(0, 0).submit(empty).toString());
    WorkItem repeated = new JobBundle("Two signs", new WorkItem[] { sign, sign });
    t.check("repeated reference means two jobs", 4, repeated.sheets());
    t.check("repeated reference minutes", 14, repeated.minutes());
    t.check("repeated reference price", 24, repeated.price());
    JobDesk small = new JobDesk(4, 100);
    t.failure("whole bundle rejected", CapacityException.class,
        "Insufficient sheets: need 5, available 4", () -> small.submit(fair));
    t.check("bundle refusal leaves sheets", 4, small.remainingSheets());
    t.check("bundle refusal leaves minutes", 100, small.remainingMinutes());
    t.check("bundle refusal leaves revenue", 0, small.totalCharged());
    t.check("bundle refusal consumes no number", "#1 Sign: $13", small.submit(sign).toString());
    t.check("receipt remains stable", "#1 Fair: $21", receipt.toString());
    t.check("bundle construction and queries are silent", "", t.capture(() -> {
      WorkItem quiet = new JobBundle("Quiet", new WorkItem[] { sign });
      quiet.label(); quiet.sheets(); quiet.minutes(); quiet.price(); quiet.toString();
    }));
    t.check("sample output", "Fair [sheets=5, minutes=18, price=21]\n"
        + "Whole fair [sheets=6, minutes=22, price=23]\n#1 Whole fair: $23\n",
        t.capture(() -> {
          System.out.println(fair); System.out.println(nested);
          System.out.println(new JobDesk(6, 22).submit(nested));
        }));
    t.finish();
  }
}
