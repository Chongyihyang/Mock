public class IllegalCancellationException extends Exception {

  public IllegalCancellationException(String msg) {
    super("Unable to cancel event: " + msg);
  }

}
