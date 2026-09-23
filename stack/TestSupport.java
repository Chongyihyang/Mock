import java.util.Objects;

/** Small, dependency-free assertion helper. Not a stack implementation. */
public final class TestSupport {
  private int passed;
  private int failed;

  public void check(String label, Object expected, Object actual) {
    if (Objects.equals(expected, actual)) {
      this.passed++;
      System.out.println("PASS: " + label);
    } else {
      this.failed++;
      System.out.println("FAIL: " + label);
      System.out.println("  expected: " + expected);
      System.out.println("  actual:   " + actual);
    }
  }

  public void finish() {
    System.out.println(this.passed + " passed; " + this.failed + " failed.");
    if (this.failed != 0) {
      System.exit(1);
    }
  }
}
