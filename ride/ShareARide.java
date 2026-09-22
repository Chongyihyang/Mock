public class ShareARide extends RideService {

  private final int rate  = 50;
  
  @Override
  public int computeFare(Request request) {
    int ret = this.rate * request.getDistance();
    if (request.requiresSurcharge()) {
      ret += RideService.SURCHARGE_FEE;
    }

    return request.getPerPersonAmt(ret);
  }

  @Override
  public String toString() {
    return "ShareARide";
  }
}
