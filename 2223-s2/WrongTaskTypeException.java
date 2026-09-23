public class WrongTaskTypeException extends Exception {

  public WrongTaskTypeException(Integer code) {
    super("Invalid task type in input: " + code);
  }

}
