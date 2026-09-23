public class Multiply<T> extends Operation<T> {

  public Multiply(Operand<T> expr1, Operand<T> expr2) {
    super(expr1, expr2);
  }

  @Override
  public Operand<T> eval() {
    Operand<T> e1 = super.getExpr1();
    Operand<T> e2 = super.getExpr2();
    try {
      Integer res = ((Integer) e1.eval() * (Integer) e2.eval());
      Operand<Integer> ret = new Operand<Integer>(res);
      @SuppressWarnings("unchecked")
      Operand<T> ret1 = (Operand<T>) ret;
      return ret1;
    } catch (Exception e) {
      return null;
    }
  }
}

  
