public final class Test1 {
  private Test1() { }

  public static void main(String[] args) throws Exception {
    TestSupport t = new TestSupport();
    ParcelDesk desk = new ParcelDesk(5);
    t.check("register positive days", "Registered: Camera (2 days)", desk.addParcel("Camera", 2));
    t.check("register overdue", "Registered: Tin (-2 days)", desk.addParcel("Tin", -2));
    t.check("register due today", "Registered: Badge (0 days)", desk.addParcel("Badge", 0));
    t.check("one day still uses days", "Registered: Map (1 days)", desk.addParcel("Map", 1));
    t.check("spaces in names preserved", "Registered: Red Box (4 days)", desk.addParcel("Red Box", 4));
    ParcelDesk other = new ParcelDesk(2);
    t.check("another desk can use same name", "Registered: Camera (8 days)", other.addParcel("Camera", 8));
    t.check("duplicate names allowed", "Registered: Camera (-1 days)", other.addParcel("Camera", -1));
    t.check("constructor and registration are silent", "", t.capture(() -> {
      ParcelDesk quiet = new ParcelDesk(1); quiet.addParcel("Quiet", 0);
    }));

    t.check("paper sample S1", "Registered: Camera (2 days)\nRegistered: Tin (-2 days)\nRegistered: Badge (0 days)\n", t.capture(Test1::paperSample));
    t.finish("Test1");
  }

  private static void paperSample() {
    ParcelDesk desk = new ParcelDesk(5);
    System.out.println(desk.addParcel("Camera", 2));
    System.out.println(desk.addParcel("Tin", -2));
    System.out.println(desk.addParcel("Badge", 0));
  }
}
