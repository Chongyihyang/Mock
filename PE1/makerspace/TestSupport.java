import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/** Supplied testing machinery, not part of your implementation. */
public final class TestSupport {
  private int passed;
  private int failed;

  public interface Action {
    public void run() throws Exception;
  }

  public void check(String name, Object expected, Object actual) {
    if (Objects.equals(expected, actual)) {
      this.passed++;
      System.out.println("PASS: " + name);
    } else {
      this.failed++;
      System.out.println("FAIL: " + name);
      System.out.println("  expected: " + expected);
      System.out.println("  actual:   " + actual);
    }
  }

  public String capture(Action action) throws Exception {
    PrintStream original = System.out;
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    try (PrintStream replacement = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
      System.setOut(replacement);
      action.run();
      replacement.flush();
    } finally {
      System.setOut(original);
    }
    return bytes.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
  }

  public void failure(String name, Class<? extends Exception> type,
      String message, Action action) throws Exception {
    Exception caught = null;
    try {
      action.run();
    } catch (Exception e) {
      caught = e;
    }
    boolean correct = caught != null && type.isInstance(caught)
        && Objects.equals(message, caught.getMessage());
    this.check(name, true, correct);
    if (!correct) {
      System.out.println("  expected exception: " + type.getSimpleName() + ": " + message);
      System.out.println("  actual exception: " + caught);
    }
  }

  public void finish() {
    System.out.println(this.passed + " passed; " + this.failed + " failed.");
    if (this.failed != 0) {
      System.exit(1);
    }
  }
}
