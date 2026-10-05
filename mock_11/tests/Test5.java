public final class Test5 {
  private Test5() { }
  public static void main(String[] args) throws Exception {
    TestSupport t = new TestSupport();
    RoverMission m = new RoverMission(4, 20);
    t.check("sweep registration", "Added: S {next 2 energy} [pending] [sweep 0/3]", m.addSweep("S", 2, 3));
    t.check("first pass", "Executed: S {next 4 energy} [pending] [sweep 1/3]", m.execute(0));
    t.check("first cost", 18, m.energyLeft());
    t.check("second pass", "Executed: S {next 6 energy} [pending] [sweep 2/3]", m.execute(0));
    t.check("second cost", 14, m.energyLeft());
    t.check("last pass completes", "Executed: S {next 0 energy} [done] [sweep 3/3]", m.execute(0));
    t.check("progressive total", 12, m.energyUsed());
    t.check("completed sweep refuses", "Cannot execute: S {next 0 energy} [done] [sweep 3/3]", m.execute(0));
    t.check("beacon registration", "Added: B {next 3 energy} [pending] [beacon 0]", m.addBeacon("B", 3));
    t.check("beacon first", "Executed: B {next 3 energy} [pending] [beacon 1]", m.execute(1));
    t.check("beacon second", "Executed: B {next 3 energy} [pending] [beacon 2]", m.execute(1));
    t.check("beacon remains pending", "1: B {next 3 energy} [pending] [beacon 2]\n", m.pending());
    t.check("beacon cannot afford next", "Insufficient energy: B {next 3 energy} [pending] [beacon 2]", m.execute(1));
    t.check("failed run not counted", 18, m.energyUsed());
    m.addSweep("One", 1, 1);
    t.check("one-pass sweep", "Executed: One {next 0 energy} [done] [sweep 1/1]", m.execute(2));
    m.addScan("Ordinary", 1);
    t.check("ordinary still works", "Executed: Ordinary {next 0 energy} [done]", m.execute(3));
    t.check("all energy spent", 0, m.energyLeft());
    RoverMission n = new RoverMission(1, 2); n.addSweep("N", 2, 2); n.execute(0);
    t.check("failed second sweep unchanged", "Insufficient energy: N {next 4 energy} [pending] [sweep 1/2]", n.execute(0));
    t.check("pending queries do not run jobs", "0: N {next 4 energy} [pending] [sweep 1/2]\n", n.pending());
    t.check("special operations silent", "", t.capture(() -> { RoverMission q = new RoverMission(2, 9); q.addSweep("S", 1, 1); q.addBeacon("B", 1); q.execute(0); q.execute(1); }));
    paperSample(t);
    t.finish("Test5");
  }
  private static void paperSample(TestSupport t) throws Exception {
    RoverMission mission = new RoverMission(4, 12);
    t.check("sample Q5 step 2", "Added: Basin {next 2 energy} [pending] [sweep 0/3]", mission.addSweep("Basin", 2, 3));
    t.check("sample Q5 step 3", "Added: Relay {next 3 energy} [pending] [beacon 0]", mission.addBeacon("Relay", 3));
    t.check("sample Q5 step 4", "Executed: Basin {next 4 energy} [pending] [sweep 1/3]", mission.execute(0));
    t.check("sample Q5 step 5", "Executed: Relay {next 3 energy} [pending] [beacon 1]", mission.execute(1));
    t.check("sample Q5 step 6", "Executed: Basin {next 6 energy} [pending] [sweep 2/3]", mission.execute(0));
    t.check("sample Q5 step 7", "Insufficient energy: Basin {next 6 energy} [pending] [sweep 2/3]", mission.execute(0));
    t.check("sample Q5 step 8", 6, mission.recharge(3));
    t.check("sample Q5 step 9", "Executed: Basin {next 0 energy} [done] [sweep 3/3]", mission.execute(0));
    t.check("sample Q5 step 10", 0, mission.energyLeft());
    t.check("sample Q5 step 11", 15, mission.energyUsed());
    t.check("sample Q5 step 12", "1: Relay {next 3 energy} [pending] [beacon 1]\n\n", t.capture(() -> { System.out.println(mission.pending()); }));
  }
}
