public abstract class Task {

  private final Integer deadline;
  private final String assignee;
  private final String description;
  private boolean isDone = false;

  public Task(Integer deadline, String assignee, String description) {
    this.description = description;
    this.assignee = assignee;
    this.deadline = deadline;
  }

  @Override
  public String toString() {
    String res = this.description;
    if (this.deadline != null) {
      res += " | Due in " + this.deadline + " days";
    }

    if (this.assignee != null) {
      res += " | Assigned to " + this.assignee;
    }

    return res;
  }

  public boolean isDone() {
    return this.isDone;
  }

  public void completeTask() {
    this.isDone = true;
  }

  public String getDesciption() {
    return this.description;
  }

  public Integer getPoints() {
    return this.deadline;
  }

  public boolean dueToday() {
    if (this.deadline == null) {
      return false;
    }

    return this.deadline.equals(0);
  }

  public Integer getDue() {
    return this.deadline;
  }

  public String getAssignee() {
    return this.assignee;
  }

  public abstract String sendReminder();
}

  
