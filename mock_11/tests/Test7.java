public final class Test7 {
  private Test7() { }
  public static void main(String[] args) throws Exception {
    TestSupport t = new TestSupport();
    RoverMission m = new RoverMission(5, 3);
    t.check("empty queue", "No pending jobs", m.runNext());
    m.addScan("Big", 5); m.addScan("Small", 1);
    t.check("do not skip unaffordable first job", "Insufficient energy: Big {next 5 energy} [pending]", m.runNext());
    t.check("no partial work during failed selection", 0, m.energyUsed());
    m.cancel(0);
    t.check("skip cancelled", "Executed: Small {next 0 energy} [done]", m.runNext());
    t.check("all terminal", "No pending jobs", m.runNext());
    m.addSweep("Sweep", 1, 2); m.addScan("Later", 1);
    t.check("select newly registered job", "Executed: Sweep {next 2 energy} [pending] [sweep 1/2]", m.runNext());
    t.check("same sweep retains its position", "Insufficient energy: Sweep {next 2 energy} [pending] [sweep 1/2]", m.runNext());
    m.recharge(2);
    t.check("retry sweep", "Executed: Sweep {next 0 energy} [done] [sweep 2/2]", m.runNext());
    t.check("advance only when terminal", "Executed: Later {next 0 energy} [done]", m.runNext());
    m.addBeacon("B", 1);
    t.check("beacon selected with no energy", "Insufficient energy: B {next 1 energy} [pending] [beacon 0]", m.runNext());
    m.recharge(2);
    t.check("beacon first next", "Executed: B {next 1 energy} [pending] [beacon 1]", m.runNext());
    t.check("beacon still first", "Executed: B {next 1 energy} [pending] [beacon 2]", m.runNext());
    m.cancel(4);
    t.check("no pending after beacon cancelled", "No pending jobs", m.runNext());
    t.check("all uses accounted", 7, m.energyUsed());
    RoverMission other = new RoverMission(1, 1); other.addScan("X", 1); other.runNext();
    t.check("mission totals independent", 7, m.energyUsed());
    t.check("second mission uses its own energy", 0, other.energyLeft());
    t.check("runNext silent", "", t.capture(() -> { m.runNext(); other.runNext(); }));
    paperSample(t);
    t.finish("Test7");
  }
  private static void paperSample(TestSupport t) throws Exception {
    RoverMission mission = new RoverMission(4, 3);
    t.check("sample Q7 step 2", "Added: Basin {next 2 energy} [pending] [sweep 0/2]", mission.addSweep("Basin", 2, 2));
    t.check("sample Q7 step 3", "Added: Ridge {next 1 energy} [pending]", mission.addScan("Ridge", 1));
    t.check("sample Q7 step 4", "Executed: Basin {next 4 energy} [pending] [sweep 1/2]", mission.runNext());
    t.check("sample Q7 step 5", "Insufficient energy: Basin {next 4 energy} [pending] [sweep 1/2]", mission.runNext());
    t.check("sample Q7 step 6", "Cancelled: Basin {next 0 energy} [cancelled] [sweep 1/2]", mission.cancel(0));
    t.check("sample Q7 step 7", "Executed: Ridge {next 0 energy} [done]", mission.runNext());
    t.check("sample Q7 step 8", "No pending jobs", mission.runNext());
    t.check("sample Q7 step 9", 3, mission.energyUsed());
  }
}
