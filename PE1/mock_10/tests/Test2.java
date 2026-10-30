public final class Test2 {
  private Test2() { }

  public static void main(String[] args) throws Exception {
    TestSupport t = new TestSupport();
    ParcelDesk desk = new ParcelDesk(6);
    t.check("empty listing", "", desk.allParcels());
    t.check("empty overdue listing", "", desk.overdueParcels());
    desk.addParcel("A", 1);
    desk.addParcel("B", -1);
    desk.addParcel("C", 0);
    desk.addParcel("D", -4);
    t.check("lists only occupied positions", "0: A (1 days)\n1: B (-1 days)\n2: C (0 days)\n3: D (-4 days)\n", desk.allParcels());
    t.check("overdue uses original indices", "1: B (-1 days)\n3: D (-4 days)\n", desk.overdueParcels());
    t.check("queries are silent", "", t.capture(() -> { desk.allParcels(); desk.overdueParcels(); }));
    t.check("queries do not change state", "1: B (-1 days)\n3: D (-4 days)\n", desk.overdueParcels());
    ParcelDesk other = new ParcelDesk(1);
    other.addParcel("Other", 3);
    t.check("other desk starts indexing at zero", "0: Other (3 days)\n", other.allParcels());
    t.check("no overdue parcels means empty string", "", other.overdueParcels());

    t.check("paper sample S2", "0: Camera (2 days)\n1: Tin (-2 days)\n2: Badge (0 days)\nOverdue:\n1: Tin (-2 days)\n", t.capture(Test2::paperSample));
    t.finish("Test2");
  }

  private static void paperSample() {
    ParcelDesk desk = new ParcelDesk(5);
    desk.addParcel("Camera", 2);
    desk.addParcel("Tin", -2);
    desk.addParcel("Badge", 0);
    System.out.print(desk.allParcels());
    System.out.println("Overdue:");
    System.out.print(desk.overdueParcels());
  }
}
