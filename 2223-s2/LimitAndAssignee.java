public class LimitAndAssignee extends Task {

  public LimitAndAssignee(String description, Integer deadline, String assignee) {
    super(deadline, assignee, description);
  }

  @Override
  public String sendReminder() {
    return "Sending a reminder to complete \"" + super.getDesciption() + "\" to " + super.getAssignee();
  }

}
