public class JustRide extends RideService {

  private final int rate  = 22;
  
  @Override
  public int computeFare(Request request) {
    int ret = this.rate * request.getDistance();
    if (request.requiresSurcharge()) {
      ret += RideService.SURCHARGE_FEE; 
    }

    return ret;
  }

  @Override
  public String toString() {
    return "JustRide";
  }
}
