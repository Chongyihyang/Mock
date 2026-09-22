public class TakeACab extends RideService {

  private final int rate = 33;
  
  @Override
  public int computeFare(Request request) {
    return this.rate * request.getDistance() + RideService.BOOKING_FEE;
  }

  @Override
  public String toString() {
    return "RideService";
  }
}
