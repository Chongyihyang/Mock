public class Concat<T> extends Operation<T> {

  public Concat(Operand<T> expr1, Operand<T> expr2) {
    super(expr1, expr2);
  }

  @Override
  public Operand<T> eval() {
    Operand<T> e1 = super.getExpr1();
    Operand<T> e2 = super.getExpr2();
    try {
      String res = ((String) e1.eval() + (String) e2.eval());
      Operand<String> ret = new Operand<String>(res);
      @SuppressWarnings("unchecked")
      Operand<T> ret1 = (Operand<T>) ret;
      return ret1;
    } catch (Exception e) {
      return null;
    }
  }

}

  
