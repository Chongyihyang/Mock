/** Supplied sequence. Modify only as directed in Question 1.
 * @author YOUR_STUDENT_NUMBER
 */
public class Seq<T> {
  private final T[] items;

  public Seq(int capacity) {
    @SuppressWarnings("unchecked")
    T[] array = (T[]) new Object[capacity];
    this.items = array;
  }

  public void set(int index, T item) {
    this.items[index] = item;
  }

  public T get(int index) {
    return this.items[index];
  }
}
