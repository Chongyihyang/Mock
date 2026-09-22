public abstract class Car implements Comparable<Car> {

  private final String licencePlate;
  private int waitingTime;

  public Car(String licencePlate, int waitingTime) {
    this.licencePlate = licencePlate;
    this.waitingTime = waitingTime;
  }

  @Override
  public String toString() {
    String res = this.licencePlate;
    if (this.waitingTime <= 1) {
      res += " (" + this.waitingTime + " min away)";
    } else {
      res += " (" + this.waitingTime + " mins away)";
    }
    return res;
  }

  @Override
  public int compareTo(Car car) {
    return this.waitingTime - car.waitingTime;
  }

}

  


