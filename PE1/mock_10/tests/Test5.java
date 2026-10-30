public final class Test5 {
  private Test5() { }

  public static void main(String[] args) throws Exception {
    TestSupport t = new TestSupport();
    ParcelDesk desk = new ParcelDesk(4);
    t.check("initial collected fees", 0, desk.feesCollected());
    desk.addParcel("Box", -2);
    desk.addChilled("Milk", -1);
    desk.addLocker("Cable", -5, "B7");
    t.check("initial mixed outstanding fees", 15, desk.totalFees());
    t.check("collect standard", "Collected: Box (-2 days); paid $4", desk.collect(0));
    t.check("collection increases paid total", 4, desk.feesCollected());
    t.check("collection removes outstanding fee", 11, desk.totalFees());
    t.check("repeat collection", "Already collected: Box (-2 days)", desk.collect(0));
    t.check("repeat does not charge twice", 4, desk.feesCollected());
    t.check("repeat does not alter outstanding", 11, desk.totalFees());
    t.check("collection preserves other indices", "1: Milk (-1 days) [chilled]\n2: Cable (-5 days) [locker B7]\n", desk.allParcels());
    t.check("collected parcel absent from overdue", "1: Milk (-1 days) [chilled]\n2: Cable (-5 days) [locker B7]\n", desk.overdueParcels());
    t.check("collect chilled", "Collected: Milk (-1 days) [chilled]; paid $4", desk.collect(1));
    t.check("collect locker", "Collected: Cable (-5 days) [locker B7]; paid $7", desk.collect(2));
    t.check("all fees collected", 15, desk.feesCollected());
    t.check("no outstanding fees", 0, desk.totalFees());
    t.check("all collected listing empty", "", desk.allParcels());
    t.check("all collected overdue empty", "", desk.overdueParcels());
    desk.addParcel("New", 2);
    t.check("collection does not reuse indices", "3: New (2 days)\n", desk.allParcels());
    t.check("collected extension refusal", "Cannot extend: Box (-2 days)", desk.extend(0));

    t.check("paper sample S5", "Collected: Camera (-2 days); paid $4\nAlready collected: Camera (-2 days)\nCannot extend: Camera (-2 days)\n1: Herbs (0 days) [chilled]\n2: Cable (-3 days) [locker B7]\nOutstanding: $7\nCollected fees: $4\nCollected: Herbs (0 days) [chilled]; paid $0\nCollected: Cable (-3 days) [locker B7]; paid $7\nOutstanding: $0\nCollected fees: $11\n", t.capture(Test5::paperSample));
    t.finish("Test5");
  }

  private static void paperSample() {
    ParcelDesk desk = new ParcelDesk(4);
    desk.addParcel("Camera", -2);
    desk.addChilled("Herbs", 0);
    desk.addLocker("Cable", -3, "B7");
    System.out.println(desk.collect(0));
    System.out.println(desk.collect(0));
    System.out.println(desk.extend(0));
    System.out.print(desk.allParcels());
    System.out.println("Outstanding: $" + desk.totalFees());
    System.out.println("Collected fees: $" + desk.feesCollected());
    System.out.println(desk.collect(1));
    System.out.println(desk.collect(2));
    System.out.print(desk.allParcels());
    System.out.println("Outstanding: $" + desk.totalFees());
    System.out.println("Collected fees: $" + desk.feesCollected());
  }
}
