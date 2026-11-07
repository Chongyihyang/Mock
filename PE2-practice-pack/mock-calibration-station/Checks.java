/** Supplied test harness. Do not edit. */
public final class Checks {
  private static int passed;
  private static int failed;
  public static void test(String name, Runnable test) {
    try { test.run(); passed++; System.out.println("PASS: " + name); }
    catch (Throwable e) { failed++; System.out.println("FAIL: " + name + " -- " + e); }
  }
  public static void equal(Object expected, Object actual) {
    if (!java.util.Objects.equals(expected, actual)) {
      throw new AssertionError("expected <" + expected + "> but got <" + actual + ">");
    }
  }
  public static void yes(boolean value) {
    if (!value) { throw new AssertionError("condition was false"); }
  }
  public static void finish() {
    System.out.println("Result: " + passed + " passed; " + failed + " failed.");
    if (failed > 0) { System.exit(1); }
  }
}
