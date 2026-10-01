/** Fixed-capacity support sequence. Do not modify this supplied file. */
public final class Seq<T> {
  private final T[] values;

  public Seq(int size) {
    @SuppressWarnings("unchecked")
    T[] temporary = (T[]) new Object[size];
    this.values = temporary;
  }

  public void set(int index, T item) { this.values[index] = item; }
  public T get(int index) { return this.values[index]; }
}
