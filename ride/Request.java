public class Request {

  private final int distance;
  private final int pax;
  private final int time;

  public Request(int distance, int pax, int time) {
    this.distance = distance;
    this.pax = pax;
    this.time = time;
  }

  public boolean requiresSurcharge() {
    return (time >= RideService.START_TIME && time <= RideService.END_TIME);
  }

  public int getPerPersonAmt(int num) {
    return num / this.pax;
  }

  public int getDistance() {
    return this.distance;
  }

}
  
