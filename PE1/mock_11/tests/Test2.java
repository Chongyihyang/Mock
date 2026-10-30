public final class Test2 {
  private Test2() { }
  public static void main(String[] args) throws Exception {
    TestSupport t = new TestSupport();
    RoverMission m = new RoverMission(3, 0);
    t.check("first addition", "Added: North Ridge {next 4 energy} [pending]", m.addScan("North Ridge", 4));
    t.check("same name allowed", "Added: North Ridge {next 1 energy} [pending]", m.addScan("North Ridge", 1));
    t.check("fill capacity", "Added: Crater {next 2 energy} [pending]", m.addScan("Crater", 2));
    RoverMission other = new RoverMission(1, 9);
    t.check("independent mission", "Added: Crater {next 7 energy} [pending]", other.addScan("Crater", 7));
    t.check("registration is silent", "", t.capture(() -> { RoverMission q = new RoverMission(1, 0); q.addScan("Q", 1); }));
    paperSample(t);
    t.finish("Test2");
  }
  private static void paperSample(TestSupport t) throws Exception {
    RoverMission mission = new RoverMission(4, 7);
    t.check("sample Q2 step 2", "Added: Crater {next 4 energy} [pending]", mission.addScan("Crater", 4));
    t.check("sample Q2 step 3", "Added: Ridge {next 6 energy} [pending]", mission.addScan("Ridge", 6));
  }
}
