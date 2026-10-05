public final class Test3 {
  private Test3() { }
  public static void main(String[] args) throws Exception {
    TestSupport t = new TestSupport();
    RoverMission m = new RoverMission(5, 0);
    t.check("empty full list", "", m.status());
    t.check("empty pending list", "", m.pending());
    m.addScan("A", 3); m.addScan("A", 1); m.addScan("C", 2);
    t.check("occupied slots only", "0: A {next 3 energy} [pending]\n1: A {next 1 energy} [pending]\n2: C {next 2 energy} [pending]\n", m.status());
    t.check("pending even with no energy", "0: A {next 3 energy} [pending]\n1: A {next 1 energy} [pending]\n2: C {next 2 energy} [pending]\n", m.pending());
    t.check("listing is silent", "", t.capture(() -> { m.status(); m.pending(); }));
    t.check("queries preserve state", "0: A {next 3 energy} [pending]\n1: A {next 1 energy} [pending]\n2: C {next 2 energy} [pending]\n", m.status());
    RoverMission other = new RoverMission(1, 2); other.addScan("D", 1);
    t.check("independent indices", "0: D {next 1 energy} [pending]\n", other.status());
    paperSample(t);
    t.finish("Test3");
  }
  private static void paperSample(TestSupport t) throws Exception {
    RoverMission mission = new RoverMission(4, 7);
    t.check("sample Q3 step 2", "Added: Crater {next 4 energy} [pending]", mission.addScan("Crater", 4));
    t.check("sample Q3 step 3", "Added: Ridge {next 6 energy} [pending]", mission.addScan("Ridge", 6));
    t.check("sample Q3 step 4", "0: Crater {next 4 energy} [pending]\n1: Ridge {next 6 energy} [pending]\n\n", t.capture(() -> { System.out.println(mission.status()); }));
    t.check("sample Q3 step 5", "0: Crater {next 4 energy} [pending]\n1: Ridge {next 6 energy} [pending]\n\n", t.capture(() -> { System.out.println(mission.pending()); }));
  }
}
