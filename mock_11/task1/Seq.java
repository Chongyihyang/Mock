/** Supplied sequence. Modify only as directed in Question 1.
 * @author YOUR_STUDENT_NUMBER
 */
public class Seq<T> {
  private final T[] items;
  private int curr = 0;

  public Seq(int capacity) {
    @SuppressWarnings("unchecked")
    T[] array = (T[]) new Object[capacity];
    this.items = array;
  }

  public void set(T item) {
    this.items[this.curr] = item;
    this.curr += 1;
  }

  public int size() {
    return this.curr;
  }

  public T get(int index) {
    return this.items[index];
  }
}
