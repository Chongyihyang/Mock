public class Gym {

  private final int capacity;
  private int current = 0;

  public Gym(int capacity) {
    this.capacity = capacity;
  }

  public void enter(Person person) {
    if (this.current < this.capacity) {
      System.out.println(person + " can enter");
    } else {
      System.out.println(person + "cannot enter");
    }
  }

  @Override
  public String toString() {
    return String.format("Gym Capacity: %d/%d", this.current, this.capacity);
  }
}
