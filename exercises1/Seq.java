/**
 * The Seq class implements a simple sequence data structure
 * with limited capacity that can store any Object instances.
 *
 * @author Adi Yoga S. Prabawa
 */
public class Seq<T> {
  private T[] array;

  public Seq(int size) {
    // The only way we can put an object into array is through
    // the method set() and we only put object of type T inside.
    // So it is safe to cast `Object[]` to `T[]`.
    @SuppressWarnings("unchecked")
    T[] a = (T[]) new Object[size];
    this.array = a;
  }

  public void set(int index, T item) {
    this.array[index] = item;
  }

  public T get(int index) {
    return this.array[index];
  }
}