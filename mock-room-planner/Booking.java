/**
 * A booking of one room over a half-open time interval.
 *
 * @author
 */
public final class Booking {
  private final String room;
  private final int start;
  private final int end;

  public Booking(String room, int start, int end) {
    this.room = room;
    this.start = start;
    this.end = end;
  }

  public String getRoom() {
    return this.room;
  }

  public int getStart() {
    return this.start;
  }

  public int getEnd() {
    return this.end;
  }

  public Booking shift(int minutes) {
    return new Booking(this.room, this.start + minutes, this.end + minutes);
  }

  public Booking relocate(String room) {
    return new Booking(room, this.start, this.end);
  }

  public boolean overlaps(Booking other) {
    return this.room.equals(other.room)
        && this.start < other.end && other.start < this.end;
  }

  @Override
  public String toString() {
    return this.room + " [" + this.start + ", " + this.end + ")";
  }
}
