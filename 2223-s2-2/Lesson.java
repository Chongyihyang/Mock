public class Lesson extends Event {

  private final int start;
  private final int end;

  public Lesson(String code, int start, int end) {
    super(code);
    this.start = start;
    this.end = end;
  }

  @Override
  public String toString() {
    return super.printDescription() + " | " + this.start + " - " + this.end;
  }

  @Override
  public void cancel() throws IllegalCancellationException {
    throw new IllegalCancellationException(super.printDescription());
  }

  @Override
  public int getBusy() {
    return this.end - this.start;
  }

  @Override
  public boolean needsReminder(int time) {
    return this.start >= time;
  }
}

