public class ShareARide extends RideService {

  private final int rate  = 50;
  
  @Override
  public int computeFare(Request request) {
    if (request.requiresSurcharge()) {
      return request.getPerPersonAmt(this.rate * request.getDistance() + RideService.SURCHARGE_FEE); 
    }

    return request.getPerPersonAmt(this.rate);
  }

  @Override
  public String toString() {
    return "ShareARide";
  }
}
