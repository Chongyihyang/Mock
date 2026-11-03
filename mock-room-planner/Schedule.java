import cs2030s.fp.BooleanCondition;
import cs2030s.fp.InfiniteList;
import cs2030s.fp.Maybe;
import cs2030s.fp.Transformer;
import java.util.ArrayList;
import java.util.List;

/**
 * An ordered sequence of room bookings.
 *
 * @author
 */
public final class Schedule {
  private final InfiniteList<Booking> bookings;

  public Schedule(InfiniteList<Booking> bookings) {
    this.bookings = bookings;
  }

  public InfiniteList<Booking> getBookings() {
    return this.bookings;
  }

  public Schedule cancel(BooleanCondition<? super Booking> condition) {
    return new Schedule(this.bookings.filter(x -> !condition.test(x)));
  }

  public Schedule revise(BooleanCondition<? super Booking> condition,
      Transformer<? super Booking, ? extends Booking> change) {
    return new Schedule(this.bookings.map(x -> 
          Maybe
          .some(x)
          .filter(y -> condition.test(y))
          .map(y -> (Booking) change.transform(y))
          .orElse(x)));
  }

  public String describe(Transformer<? super Booking, String> formatter) {
    return this.bookings.reduce("", (x, y) -> 
        x + "[" + formatter.transform(y) + "]" );
  }

  @Override
  public String toString() {
    return this.bookings.reduce("", (text, b) -> text + "[" + b + "]");
  }
}
