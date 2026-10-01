public final class Test4 {
  private Test4() { }

  public static void main(String[] args) throws Exception {
    TestSupport t = new TestSupport();
    ParcelDesk desk = new ParcelDesk(7);
    t.check("chilled registration", "Registered: Herbs (0 days) [chilled]", desk.addChilled("Herbs", 0));
    t.check("chilled extension adds one day", "Extended: Herbs (1 days) {1 extensions} [chilled]", desk.extend(0));
    t.check("chilled extension only once", "Cannot extend: Herbs (1 days) {1 extensions} [chilled]", desk.extend(0));
    t.check("late chilled registration", "Registered: Milk (-3 days) [chilled]", desk.addChilled("Milk", -3));
    t.check("late chilled refused", "Cannot extend: Milk (-3 days) [chilled]", desk.extend(1));
    t.check("chilled daily fee", 12, desk.totalFees());
    t.check("locker registration", "Registered: Cable (-2 days) [locker B7]", desk.addLocker("Cable", -2, "B7"));
    t.check("locker fixed fee", 19, desk.totalFees());
    desk.addLocker("Wire", -9, "C3");
    t.check("locker fee not multiplied by days", 26, desk.totalFees());
    desk.addLocker("Today", 0, "A1");
    t.check("due today locker has no fee", 26, desk.totalFees());
    t.check("locker refuses even when not overdue", "Cannot extend: Today (0 days) [locker A1]", desk.extend(4));
    desk.addParcel("Standard", -1);
    t.check("mixed fees", 28, desk.totalFees());
    t.check("mixed overdue listing", "1: Milk (-3 days) [chilled]\n2: Cable (-2 days) [locker B7]\n3: Wire (-9 days) [locker C3]\n5: Standard (-1 days)\n", desk.overdueParcels());
    desk.addChilled("Fresh", 2);
    t.check("new chilled has its own extension count", "Extended: Fresh (3 days) {1 extensions} [chilled]", desk.extend(6));
    t.check("new constructors and operations are silent", "", t.capture(() -> {
      ParcelDesk quiet = new ParcelDesk(2); quiet.addChilled("Q", 0); quiet.addLocker("L", 0, "X1");
      quiet.extend(0); quiet.extend(1); quiet.totalFees();
    }));

    t.check("paper sample S4", "Registered: Camera (-2 days)\nRegistered: Herbs (0 days) [chilled]\nRegistered: Milk (-2 days) [chilled]\nRegistered: Cable (-3 days) [locker B7]\nExtended: Herbs (1 days) {1 extensions} [chilled]\nCannot extend: Herbs (1 days) {1 extensions} [chilled]\nCannot extend: Cable (-3 days) [locker B7]\nFees: $19\n", t.capture(Test4::paperSample));
    t.finish("Test4");
  }

  private static void paperSample() {
    ParcelDesk desk = new ParcelDesk(5);
    System.out.println(desk.addParcel("Camera", -2));
    System.out.println(desk.addChilled("Herbs", 0));
    System.out.println(desk.addChilled("Milk", -2));
    System.out.println(desk.addLocker("Cable", -3, "B7"));
    System.out.println(desk.extend(1));
    System.out.println(desk.extend(1));
    System.out.println(desk.extend(3));
    System.out.println("Fees: $" + desk.totalFees());
  }
}
