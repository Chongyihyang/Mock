public class Birthday extends Event {

  public Birthday(String person) {
    String res = "Birthday (" + person + ")";
    System.out.println(res);
    super(res);
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
