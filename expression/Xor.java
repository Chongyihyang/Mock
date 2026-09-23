public class Xor<T> extends Operation<T> {

  public Xor(Operand<T> expr1, Operand<T> expr2) {
    super(expr1, expr2);
  }

  @Override
  public Operand<T> eval() {
    Operand<T> e1 = super.getExpr1();
    Operand<T> e2 = super.getExpr2();
    try {
      Boolean res = ((Boolean) e1.eval() ^ (Boolean) e2.eval());
      Operand<Boolean> ret = new Operand<Boolean>(res);
      @SuppressWarnings("unchecked")
      Operand<T> ret1 = (Operand<T>) ret;
      return ret1;
    } catch (Exception e) {
      return null;
    }
  }
}


