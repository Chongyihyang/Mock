import cs2030s.fp.InfiniteList;
import cs2030s.fp.Maybe;

/**
 * Queries on room schedules.
 *
 * @author
 */
public class RoomPlanner {
  public static Maybe<Booking> firstConflict(Schedule schedule, Booking candidate) {
    return Maybe
      .some(candidate)
      .filter(y -> schedule
          .getBookings()
          .filter(x -> x.overlaps(y))
          .count() != 0)
      .map(y -> schedule
          .getBookings()
          .filter(x -> x.overlaps(y))
          .head());
  }

  public static InfiniteList<String> availableRooms(Schedule schedule,
      InfiniteList<String> rooms, int start, int end) {
    return rooms.filter(x -> 
        schedule
        .getBookings()
        .filter(y -> y.overlaps(new Booking(x, start, end)))
        .count() == 0);
  }

  public static Schedule intersections(Schedule first, Schedule second) {
    return new Schedule(first
        .getBookings()
        .flatMap(f -> 
          second
          .getBookings()
          .filter(s -> s.overlaps(f))
          .map(s -> new Booking(f.getRoom(), 
              Maybe.some(f.getStart()).filter(x -> x > s.getStart())
              .orElse(s.getStart()),
              Maybe.some(f.getEnd()).filter(x -> x < s.getEnd())
              .orElse(s.getEnd())))));
  }
}
