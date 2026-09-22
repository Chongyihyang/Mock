public class Booking implements Comparable<Booking> {

  private final Car car;
  private final RideService service;
  private final Request request;

  public Booking(Car car, RideService service, Request request) throws IllegalArgumentException {
    if ((car instanceof Cab) && (service instanceof ShareARide)) {
      throw new IllegalArgumentException();
    } else if ((car instanceof PrivateCar) && (service instanceof TakeACab)) {
      throw new IllegalArgumentException();
    }

    this.car = car;
    this.service = service;
    this.request = request;

  }

  @Override
  public int compareTo(Booking booking) {

    int curFare = this.service.computeFare(this.request);
    int othFare = booking.service.computeFare(booking.request);
    if (curFare != othFare) {
      return curFare - othFare;
    }

    int time = this.car.compareTo(booking.car);
    if (time != 0) {
      return time;
    }

    return -1;
  }
}
