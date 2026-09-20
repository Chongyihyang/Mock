public class InsufficientPaperException extends PrintingException {

  public InsufficientPaperException(String name, int required, int available) {
    super(String.format("%s needs %d sheets; %d available", name, required, available));
  }

}
