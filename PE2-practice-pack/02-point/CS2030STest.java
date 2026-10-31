public final class CS2030STest {
  private static int passed = 0;
  private static int failed = 0;
  public interface Check { Object run() throws Throwable; }
  public static void check(String name, Object expected, Check action) {
    try {
      Object actual = action.run();
      if (java.util.Objects.equals(expected, actual)) { passed++; }
      else { failed++; System.out.println("FAIL " + name + " | expected: " + expected + " | actual: " + actual); }
    } catch (Throwable t) { failed++; System.out.println("FAIL " + name + " | threw: " + t); }
  }
  public static void throwsType(String name, Class<? extends Throwable> type, Check action) {
    try { action.run(); failed++; System.out.println("FAIL " + name + " | expected " + type.getName()); }
    catch (Throwable t) { if (type.isInstance(t)) { passed++; } else { failed++; System.out.println("FAIL " + name + " | threw " + t); } }
  }
  public static void finish() {
    System.out.println(passed + " passed; " + failed + " failed");
    if (failed != 0) { throw new AssertionError("Tests failed"); }
  }
}
