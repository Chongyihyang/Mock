public final class Test4 {
  private Test4() { }
  public static void main(String[] args) throws Exception {
    TestSupport t = new TestSupport();
    RoverMission m = new RoverMission(4, 5);
    t.check("initial energy", 5, m.energyLeft());
    t.check("initial used", 0, m.energyUsed());
    m.addScan("A", 3); m.addScan("B", 4);
    t.check("successful execution", "Executed: A {next 0 energy} [done]", m.execute(0));
    t.check("energy spent before state change", 2, m.energyLeft());
    t.check("used accumulates", 3, m.energyUsed());
    t.check("failed energy request", "Insufficient energy: B {next 4 energy} [pending]", m.execute(1));
    t.check("failure preserves energy", 2, m.energyLeft());
    t.check("failure preserves used", 3, m.energyUsed());
    t.check("failure preserves status", "1: B {next 4 energy} [pending]\n", m.pending());
    t.check("cannot execute done", "Cannot execute: A {next 0 energy} [done]", m.execute(0));
    t.check("done still listed", "0: A {next 0 energy} [done]\n1: B {next 4 energy} [pending]\n", m.status());
    t.check("recharge return", 4, m.recharge(2));
    t.check("recharge not counted as use", 3, m.energyUsed());
    t.check("exactly enough energy", "Executed: B {next 0 energy} [done]", m.execute(1));
    t.check("energy reaches zero", 0, m.energyLeft());
    t.check("total spent", 7, m.energyUsed());
    t.check("no pending jobs", "", m.pending());
    t.check("recharge after completion", 1, m.recharge(1));
    t.check("completion never frees index", "Added: C {next 1 energy} [pending]", m.addScan("C", 1));
    t.check("new index preserved", "2: C {next 1 energy} [pending]\n", m.pending());
    RoverMission z = new RoverMission(1, 0); z.addScan("Z", 1);
    t.check("zero energy fails", "Insufficient energy: Z {next 1 energy} [pending]", z.execute(0));
    t.check("other mission independent", 0, z.energyUsed());
    t.check("operations silent", "", t.capture(() -> { z.execute(0); z.recharge(1); z.execute(0); z.execute(0); z.energyLeft(); z.energyUsed(); }));
    paperSample(t);
    t.finish("Test4");
  }
  private static void paperSample(TestSupport t) throws Exception {
    RoverMission mission = new RoverMission(4, 7);
    t.check("sample Q4 step 2", "Added: Crater {next 4 energy} [pending]", mission.addScan("Crater", 4));
    t.check("sample Q4 step 3", "Added: Ridge {next 6 energy} [pending]", mission.addScan("Ridge", 6));
    t.check("sample Q4 step 4", "Executed: Crater {next 0 energy} [done]", mission.execute(0));
    t.check("sample Q4 step 5", 3, mission.energyLeft());
    t.check("sample Q4 step 6", 4, mission.energyUsed());
    t.check("sample Q4 step 7", "Insufficient energy: Ridge {next 6 energy} [pending]", mission.execute(1));
    t.check("sample Q4 step 8", "Cannot execute: Crater {next 0 energy} [done]", mission.execute(0));
    t.check("sample Q4 step 9", 6, mission.recharge(3));
    t.check("sample Q4 step 10", "Executed: Ridge {next 0 energy} [done]", mission.execute(1));
    t.check("sample Q4 step 11", 0, mission.energyLeft());
    t.check("sample Q4 step 12", 10, mission.energyUsed());
    t.check("sample Q4 step 13", "\n", t.capture(() -> { System.out.println(mission.pending()); }));
    t.check("sample Q4 step 14", "0: Crater {next 0 energy} [done]\n1: Ridge {next 0 energy} [done]\n\n", t.capture(() -> { System.out.println(mission.status()); }));
  }
}
