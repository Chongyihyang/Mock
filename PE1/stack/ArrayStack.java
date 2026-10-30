public class ArrayStack<T> implements Stack<T> {

  private int depth;
  private T[] arr;
  private int curr = 0;

  public ArrayStack(int depth) {
    this.depth = depth;
    @SuppressWarnings("unchecked")
    T[] tmp = (T[]) new Object[depth];
    this.arr = tmp;
  }

  @Override
  public void push(T item) {
    if (this.curr < this.depth) {
      this.arr[this.curr] = item;
      this.curr++;
    }
  }

  @Override
  public T pop() {
    if (this.curr == 0) {
      return null;
    }

    this.curr--;
    T res = this.arr[this.curr];
    this.arr[this.curr] = null;
    return res;
  }

  @Override
  public int getStackSize() {
    return this.curr;
  }

  @Override
  public String toString() {
    String res = "Stack:";
    for (T item : this.arr) {
      if (item != null) {
        res += " " + item.toString();
      }
    }
    return res;
  }

  public static <T> ArrayStack<T> of(T[] arr, int size) {
    ArrayStack<T> newArr = new ArrayStack<>(size);
    for (T item : arr) {
      newArr.push(item);
    }
    return newArr;
  }

  public void pushAll(ArrayStack<? extends T> source) {
    int length = source.getStackSize();
    for (int i = 0; i < length; i++) {
      this.push(source.pop());
    }
  }

  public void popAll(ArrayStack<? super T> dest) {
    int length = this.getStackSize();
    for (int i = 0; i < length; i++) {
      dest.push(this.pop());
    }
  }
}
