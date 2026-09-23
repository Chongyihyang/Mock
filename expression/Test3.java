import java.lang.reflect.Modifier;

/** Extra tests of the written requirements; no prescribed helper hierarchy. */
public final class Test3 {
  private Test3() {
  }

  public static void main(String[] args) {
    TestSupport t = new TestSupport();
    Object marker = new Object();
    t.check("operand preserves arbitrary object identity", true, new Operand(marker).eval() == marker);
    t.check("operand supports other reference types", 2.5, new Operand(2.5).eval());
    t.check("Operation is abstract", true, Modifier.isAbstract(Operation.class.getModifiers()));
    t.check("another unsupported operator", null,
        Operation.of('?', new Operand("a"), new Operand("b")));
    t.check("zero product", 0, Operation.of('*', new Operand(0), new Operand(7)).eval());
    t.check("negative product", -12, Operation.of('*', new Operand(-3), new Operand(4)).eval());
    t.check("empty left string", "r", Operation.of('+', new Operand(""), new Operand("r")).eval());
    t.check("empty right string", "l", Operation.of('+', new Operand("l"), new Operand("")).eval());
    t.check("false XOR false", false,
        Operation.of('^', new Operand(false), new Operand(false)).eval());
    t.check("false XOR true", true,
        Operation.of('^', new Operand(false), new Operand(true)).eval());
    t.check("true XOR true", false,
        Operation.of('^', new Operand(true), new Operand(true)).eval());
    Operation left = Operation.of('+', new Operand("a"), new Operand("b"));
    Operation right = Operation.of('+', new Operand("c"), new Operand("d"));
    Operation text = Operation.of('+', left, right);
    t.check("nested concatenation order", "abcd", text.eval());
    t.check("repeat evaluation", "abcd", text.eval());
    Operation truth = Operation.of('^', new Operand(true), new Operand(false));
    t.check("nested XOR", false, Operation.of('^', truth, new Operand(true)).eval());
    Operation repeated = Operation.of('*', new Operand(2), new Operand(3));
    t.check("same child reused", 36, Operation.of('*', repeated, repeated).eval());
    Operation badRight = Operation.of('*', new Operand(2), new Operand("3"));
    t.expectInvalid("multiplication validates right input", '*', () -> badRight.eval());
    Operation badLeftXor = Operation.of('^', new Operand(1), new Operand(true));
    t.expectInvalid("XOR validates left input", '^', () -> badLeftXor.eval());
    Operation badRightText = Operation.of('+', new Operand("a"), new Operand(2));
    t.expectInvalid("concatenation validates right input", '+', () -> badRightText.eval());
    Operation badLeftText = Operation.of('+', new Operand(2), new Operand("a"));
    t.expectInvalid("concatenation validates left input", '+', () -> badLeftText.eval());
    Operation notInteger = Operation.of('*', new Operand(2.0), new Operand(3));
    t.expectInvalid("no numeric coercion", '*', () -> notInteger.eval());
    Operation parent = Operation.of('+', new Operand("prefix"), badRight);
    t.expectInvalid("right nested failure propagates", '*', () -> parent.eval());
    t.finish();
  }
}
