public class Meeting extends Event {

  private final int start;
  private final int end;
  private boolean ongoing = true;
  private final String person;

  public Meeting(String description, int start, int end, String person) {
    super(description);
    this.start = start;
    this.end = end;
    this.person = person;
  }

  @Override
  public boolean isCancelled() {
    return !this.ongoing;
  }

  @Override
  public String toString() {
    return super.printDescription() + " | " + this.start + " - " + this.end + " | Meet with " + this.person;
  }

  @Override
  public void cancel() throws IllegalCancellationException {
    this.ongoing = false;
  }

  @Override
  public int getBusy() {
    if (this.ongoing) {
      return this.end - this.start;
    }

    return 0;
  }

  @Override
  public boolean needsReminder(int time) {
    return this.ongoing && this.start >= time;
  }
}
  
