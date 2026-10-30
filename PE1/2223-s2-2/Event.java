public abstract class Event {

  private String description;

  public Event(String description) {
    this.description = description;
  }

  public String printDescription() {
    return this.description;
  }

  public abstract void cancel() throws IllegalCancellationException;

  public abstract int getBusy();

  public boolean isCancelled() {
    return false;
  }

  public boolean needsReminder(int time) {
    return false;
  }
}
