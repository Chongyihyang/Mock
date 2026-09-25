public class Car extends Vehicle {

  public final static double FUEL_EFFICIENCY = 5.0;
  private double fuelLevel;

  public Car(double fuelLevel) {
    this.fuelLevel = fuelLevel;
  }

  @Override
  public String toString() {
    return "Car fuelLevel: " + helperRounder.round2DP(this.fuelLevel);
  }

  public void refuel(double fuel) {
    this.fuelLevel += fuel;
  }

  @Override
  public void move(double distance) throws CannotMoveException {
    double fuelRequired = distance / Car.FUEL_EFFICIENCY;
    if (fuelRequired > this.fuelLevel) {
      throw new CannotMoveException();
    }

    this.fuelLevel -= fuelRequired;
  }

}
