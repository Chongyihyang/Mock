public class Birthday extends Event {

  public Birthday(String person) {
    super("Birthday (" + person + ")");
  }

  @Override
  public String toString() {
    return super.printDescription();
  }

  
  @Override
  public void cancel() throws IllegalCancellationException {
    throw new IllegalCancellationException(this.toString());
  }

  @Override
  public int getBusy() {
    return 0;
  }

}
