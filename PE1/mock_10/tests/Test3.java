public final class Test3 {
  private Test3() { }

  public static void main(String[] args) throws Exception {
    TestSupport t = new TestSupport();
    ParcelDesk desk = new ParcelDesk(5);
    t.check("empty total fees", 0, desk.totalFees());
    desk.addParcel("Today", 0);
    desk.addParcel("Late", -2);
    desk.addParcel("Later", 4);
    t.check("initial fees", 4, desk.totalFees());
    t.check("first extension at zero days", "Extended: Today (3 days) {1 extensions}", desk.extend(0));
    t.check("second extension", "Extended: Today (6 days) {2 extensions}", desk.extend(0));
    t.check("third extension refused", "Cannot extend: Today (6 days) {2 extensions}", desk.extend(0));
    t.check("repeated refusal changes nothing", "Cannot extend: Today (6 days) {2 extensions}", desk.extend(0));
    t.check("overdue extension refused", "Cannot extend: Late (-2 days)", desk.extend(1));
    t.check("independent extension count", "Extended: Later (7 days) {1 extensions}", desk.extend(2));
    t.check("status uses state after extension", "0: Today (6 days) {2 extensions}\n1: Late (-2 days)\n2: Later (7 days) {1 extensions}\n", desk.allParcels());
    t.check("fees remain unchanged after queries and extension", 4, desk.totalFees());
    t.check("overdue listing still correct", "1: Late (-2 days)\n", desk.overdueParcels());
    desk.addParcel("Also late", -3);
    t.check("sum fees across parcels", 10, desk.totalFees());
    ParcelDesk quiet = new ParcelDesk(1); quiet.addParcel("Q", 0);
    t.check("extension and fee queries are silent", "", t.capture(() -> {
      quiet.extend(0); quiet.extend(0); quiet.extend(0); quiet.totalFees();
    }));

    t.check("paper sample S3", "Cannot extend: Tin (-2 days)\nExtended: Badge (3 days) {1 extensions}\nExtended: Badge (6 days) {2 extensions}\nCannot extend: Badge (6 days) {2 extensions}\n0: Camera (2 days)\n1: Tin (-2 days)\n2: Badge (6 days) {2 extensions}\nFees: $4\n", t.capture(Test3::paperSample));
    t.finish("Test3");
  }

  private static void paperSample() {
    ParcelDesk desk = new ParcelDesk(5);
    desk.addParcel("Camera", 2);
    desk.addParcel("Tin", -2);
    desk.addParcel("Badge", 0);
    System.out.println(desk.extend(1));
    System.out.println(desk.extend(2));
    System.out.println(desk.extend(2));
    System.out.println(desk.extend(2));
    System.out.print(desk.allParcels());
    System.out.println("Fees: $" + desk.totalFees());
  }
}
