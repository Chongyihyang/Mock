public class RoadTrip {

  private final Vehicle vehicle;
  private final double distance;

  public RoadTrip(Vehicle vehicle, double distance) {
    this.vehicle = vehicle;
    this.distance = distance;
  }

  public void complete() {
    try {
      this.vehicle.move(this.distance);
    } catch (CannotMoveException e) {
      System.out.println("This trip cannot be completed.");
    }
  }

}
