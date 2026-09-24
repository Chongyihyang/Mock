public class WithLimit extends Task {

  public WithLimit(String description, Integer deadline) {
    super(deadline, null, description);
  }

  @Override
  public String sendReminder() {
    return "The task \"" + super.getDesciption() + "\" is due in " + super.getDue() + " days";
  }
}
