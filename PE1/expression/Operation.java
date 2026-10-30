public abstract class Operation<T> {

  private Operand<T> expr1;
  private Operand<T> expr2;

  public Operation(Operand<T> expr1, Operand<T> expr2) {
    this.expr1 = expr1;
    this.expr2 = expr2;
  }

  public static Operation of(char symb, Operand expr1, Operand expr2) {
    try {
      if (symb == '*') {
        return new Multiply(expr1, expr2);
      } else if (symb == '+') {
        return new Concat(expr1, expr2);
      } else if (symb == '^') {
        return new Xor(expr1, expr2);
      }
    } catch (Exception e) {
      return null;
    }
    return null;
  }

  public Operand<T> getExpr1() {
    return this.expr1;
  }

  public Operand<T> getExpr2() {
    return this.expr2;
  }

  public abstract Operand<T> eval();
}


