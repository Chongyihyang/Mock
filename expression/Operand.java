public class Operand<T> {

  private T item;

  public Operand(T item) {
    this.item = item;
  }

  public T eval() {
    return this.item;
  }

}
