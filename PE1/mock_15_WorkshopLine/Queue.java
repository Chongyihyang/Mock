public class Queue<T> {
  private final T[] items;
  private int head;
  private int count;

  public Queue(int capacity) {
    if (capacity <= 0) { throw new IllegalArgumentException("Capacity must be positive"); }
    @SuppressWarnings("unchecked")
    T[] storage = (T[]) new Object[capacity];
    this.items = storage;
  }

  public void enqueue(T item) {
    if (this.count == this.items.length) { throw new IllegalStateException("Queue is full"); }
    this.items[(this.head + this.count) % this.items.length] = item;
    this.count++;
  }

  public T dequeue() {
    if (this.count == 0) { throw new java.util.NoSuchElementException("Queue is empty"); }
    T item = this.items[this.head];
    this.items[this.head] = null;
    this.head = (this.head + 1) % this.items.length;
    this.count--;
    return item;
  }

  public String queueInfo() {
    String res = "";
    for (int i = 0; i < this.count; i ++) {
      T item = this.dequeue();
      res += String.format("%d: %s\n", i, item.toString());
      this.enqueue(item);
    }

    return res;
  }

  public int size() { return this.count; }
}
