public class NoLimit extends Task {

  public NoLimit(String description) {
    super(null, null, description);
  }

  @Override
  public Integer getPoints() {
    return 0;
  }

  @Override
  public String sendReminder() {
    return null;
  }

}
