public class JustRide extends RideService {

  private final int rate  = 22;
  
  @Override
  public int computeFare(Request request) {
    if (request.requiresSurcharge()) {
      return this.rate * request.getDistance() + RideService.SURCHARGE_FEE; 
    }

    return this.rate;
  }

  @Override
  public String toString() {
    return "JustRide";
  }
}
