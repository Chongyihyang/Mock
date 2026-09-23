public class InvalidOperandException extends RuntimeException {

  public InvalidOperandException(char msg) {
    super("ERROR: Invalid operand for operator " + msg);
  }

}
