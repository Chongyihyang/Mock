public class PrivateCar extends Car {

  public PrivateCar(String licencePlate, int time) {
    super(licencePlate, time);
  }

  @Override
  public String toString() {
    return "PrivateCar " + super.toString();
  }

}

