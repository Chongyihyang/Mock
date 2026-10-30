public abstract class RideService {

  public static final int BOOKING_FEE = 200;
  public static final int SURCHARGE_FEE = 500;
  public static final int START_TIME = 600;
  public static final int END_TIME = 900; 
  
  public abstract int computeFare(Request request);

}
