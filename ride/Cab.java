public class Cab extends Car {

  public Cab(String licencePlate, int time) {
    super(licencePlate, time);
  }

  @Override
  public String toString() {
    return "Cab " + super.toString();
  }

}

