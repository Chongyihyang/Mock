public class CapacityException extends Exception {

  public CapacityException(String type, int need, int avail) {
    super(String.format("Insufficient %s: need %d, available %d",
          type, need, avail));
  }

}
