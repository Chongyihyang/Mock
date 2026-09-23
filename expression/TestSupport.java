import java.util.Objects;

/** Test reporting only; contains no expression implementation. */
public final class TestSupport {
  private int passed;
  private int failed;

  public void check(String label, Object expected, Object actual) {
    if (Objects.equals(expected, actual)) {
      this.passed++;
      System.out.println("PASS: " + label);
    } else {
      this.fail(label, "expected " + expected + "; actual " + actual);
    }
  }

  private void fail(String label, String details) {
    this.failed++;
    System.out.println("FAIL: " + label + " — " + details);
  }

  public void expectInvalid(String label, char operator, Runnable evaluation) {
    try {
      evaluation.run();
      this.fail(label, "expected InvalidOperandException; nothing was thrown");
    } catch (InvalidOperandException e) {
      this.check(label, "ERROR: Invalid operand for operator " + operator, e.getMessage());
    } catch (RuntimeException e) {
      this.fail(label, "wrong exception: " + e);
    }
  }

  public void finish() {
    System.out.println(this.passed + " passed; " + this.failed + " failed.");
    if (this.failed != 0) {
      System.exit(1);
    }
  }
}
