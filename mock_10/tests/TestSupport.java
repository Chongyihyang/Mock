import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/** Test infrastructure. No implementation classes are bundled in test.jar. */
public final class TestSupport {
  private int passed;
  private int failed;

  public interface Action { public void run() throws Exception; }

  public void check(String name, Object expected, Object actual) {
    if (Objects.equals(expected, actual)) {
      this.passed++;
      System.out.println("PASS: " + name);
    } else {
      this.failed++;
      System.out.println("FAIL: " + name);
      System.out.println("  expected: " + show(expected));
      System.out.println("  actual:   " + show(actual));
    }
  }

  private static String show(Object value) {
    return String.valueOf(value).replace("\n", "\\n").replace("\r", "\\r");
  }

  public String capture(Action action) throws Exception {
    PrintStream old = System.out;
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    try (PrintStream replacement = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
      System.setOut(replacement);
      action.run();
      replacement.flush();
    } finally {
      System.setOut(old);
    }
    return bytes.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
  }

  public void finish(String name) {
    System.out.println(name + ": " + this.passed + " passed; " + this.failed + " failed.");
    if (this.failed != 0) { System.exit(1); }
  }
}
