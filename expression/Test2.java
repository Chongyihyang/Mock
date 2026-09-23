/** Every Part 2 result from the PDF, including nested error propagation. */
public final class Test2 {
  private Test2() {
  }

  public static void main(String[] args) {
    TestSupport t = new TestSupport();
    Operation product = Operation.of('*', new Operand(2), new Operand(3));
    t.check("multiplication", 6, product.eval());
    Operation concat = Operation.of('+', new Operand("hello"), new Operand("world"));
    t.check("concatenation", "helloworld", concat.eval());
    Operation xor = Operation.of('^', new Operand(true), new Operand(false));
    t.check("XOR", true, xor.eval());
    t.check("unsupported operator", null, Operation.of('!', new Operand(2), new Operand(3)));
    Operation o1 = Operation.of('*', new Operand(2), new Operand(3));
    Operation nested = Operation.of('*', o1, new Operand(4));
    t.check("operation and operand", 24, nested.eval());
    Operation o2 = Operation.of('*', new Operand(2), new Operand(4));
    Operation both = Operation.of('*', o1, o2);
    t.check("two operation inputs", 48, both.eval());

    // Construct outside the exception checks: the PDF requires errors at eval().
    Operation badProduct = Operation.of('*', new Operand("1"), new Operand(3));
    t.expectInvalid("wrong multiplication input", '*', () -> badProduct.eval());
    Operation badConcat = Operation.of('+', new Operand(1), new Operand(4));
    t.expectInvalid("wrong concatenation inputs", '+', () -> badConcat.eval());
    Operation badXor = Operation.of('^', new Operand(false), new Operand(3));
    t.expectInvalid("wrong XOR input", '^', () -> badXor.eval());
    Operation validLeft = Operation.of('*', new Operand(1), new Operand(3));
    Operation validRight = Operation.of('^', new Operand(false), new Operand(false));
    Operation wrongOuter = Operation.of('+', validLeft, validRight);
    t.expectInvalid("outer operation refuses evaluated types", '+', () -> wrongOuter.eval());
    Operation invalidLeft = Operation.of('*', new Operand(1), new Operand("3"));
    Operation anotherRight = Operation.of('^', new Operand(false), new Operand(false));
    Operation nestedFailure = Operation.of('+', invalidLeft, anotherRight);
    t.expectInvalid("nested failure keeps its operator", '*', () -> nestedFailure.eval());
    t.finish();
  }
}
