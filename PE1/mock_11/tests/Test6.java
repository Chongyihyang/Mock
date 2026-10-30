public final class Test6 {
  private Test6() { }
  public static void main(String[] args) throws Exception {
    TestSupport t = new TestSupport();
    RoverMission m = new RoverMission(5, 12); m.addScan("A", 3); m.addSweep("S", 2, 3); m.addBeacon("B", 2);
    t.check("cancel ordinary", "Cancelled: A {next 0 energy} [cancelled]", m.cancel(0));
    t.check("cancelled cannot execute", "Cannot execute: A {next 0 energy} [cancelled]", m.execute(0));
    t.check("repeat cancellation", "Cannot cancel: A {next 0 energy} [cancelled]", m.cancel(0));
    t.check("no energy used by cancellation", 0, m.energyUsed());
    t.check("cancel does not recharge", 12, m.energyLeft());
    m.execute(1);
    t.check("cancel partly executed sweep", "Cancelled: S {next 0 energy} [cancelled] [sweep 1/3]", m.cancel(1));
    t.check("sweep cannot execute after cancel", "Cannot execute: S {next 0 energy} [cancelled] [sweep 1/3]", m.execute(1));
    m.execute(2); m.execute(2);
    t.check("cancel beacon preserves count", "Cancelled: B {next 0 energy} [cancelled] [beacon 2]", m.cancel(2));
    t.check("beacon cannot execute after cancel", "Cannot execute: B {next 0 energy} [cancelled] [beacon 2]", m.execute(2));
    t.check("no refunds", 6, m.energyUsed());
    t.check("no refunds energy", 6, m.energyLeft());
    t.check("cancelled jobs retained", "0: A {next 0 energy} [cancelled]\n1: S {next 0 energy} [cancelled] [sweep 1/3]\n2: B {next 0 energy} [cancelled] [beacon 2]\n", m.status());
    t.check("none pending after cancellation", "", m.pending());
    m.addScan("A", 1);
    t.check("duplicate name new index", "3: A {next 1 energy} [pending]\n", m.pending());
    m.execute(3);
    t.check("cannot cancel completed", "Cannot cancel: A {next 0 energy} [done]", m.cancel(3));
    m.addSweep("Done", 1, 1); m.execute(4);
    t.check("cannot cancel completed sweep", "Cannot cancel: Done {next 0 energy} [done] [sweep 1/1]", m.cancel(4));
    t.check("total remains historical", 8, m.energyUsed());
    t.check("silent cancellation", "", t.capture(() -> { m.cancel(0); m.cancel(4); }));
    paperSample(t);
    t.finish("Test6");
  }
  private static void paperSample(TestSupport t) throws Exception {
    RoverMission mission = new RoverMission(4, 10);
    t.check("sample Q6 step 2", "Added: Basin {next 2 energy} [pending] [sweep 0/3]", mission.addSweep("Basin", 2, 3));
    t.check("sample Q6 step 3", "Added: Relay {next 3 energy} [pending] [beacon 0]", mission.addBeacon("Relay", 3));
    t.check("sample Q6 step 4", "Added: Crater {next 4 energy} [pending]", mission.addScan("Crater", 4));
    t.check("sample Q6 step 5", "Executed: Basin {next 4 energy} [pending] [sweep 1/3]", mission.execute(0));
    t.check("sample Q6 step 6", "Cancelled: Basin {next 0 energy} [cancelled] [sweep 1/3]", mission.cancel(0));
    t.check("sample Q6 step 7", "Cannot execute: Basin {next 0 energy} [cancelled] [sweep 1/3]", mission.execute(0));
    t.check("sample Q6 step 8", "Cannot cancel: Basin {next 0 energy} [cancelled] [sweep 1/3]", mission.cancel(0));
    t.check("sample Q6 step 9", "Executed: Crater {next 0 energy} [done]", mission.execute(2));
    t.check("sample Q6 step 10", "Cannot cancel: Crater {next 0 energy} [done]", mission.cancel(2));
    t.check("sample Q6 step 11", 4, mission.energyLeft());
    t.check("sample Q6 step 12", 6, mission.energyUsed());
    t.check("sample Q6 step 13", "1: Relay {next 3 energy} [pending] [beacon 0]\n\n", t.capture(() -> { System.out.println(mission.pending()); }));
  }
}
