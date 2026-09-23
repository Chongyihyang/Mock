/** Part 1 only: does not reference Operation or any operation subclass. */
public final class Test1 {
  private Test1() {
  }

  public static void main(String[] args) {
    TestSupport t = new TestSupport();
    t.check("integer operand", 5, new Operand(5).eval());
    t.check("string operand", "string", new Operand("string").eval());
    t.check("Boolean operand", true, new Operand(true).eval());
    // Assignment also requires the custom exception to be unchecked.
    RuntimeException e = new InvalidOperandException('!');
    t.check("exception message", "ERROR: Invalid operand for operator !", e.getMessage());
    t.finish();
  }
}
