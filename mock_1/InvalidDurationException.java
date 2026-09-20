class InvalidDurationException extends IllegalArgumentException {

  public InvalidDurationException(int hours) {
    super(String.format("Invalid duration: %d", hours));
  }

}
