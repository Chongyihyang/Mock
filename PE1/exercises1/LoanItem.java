public abstract class LoanItem {

  private final String name;
  private int daysLeft;

  public LoanItem(String name, int daysLeft) {
    this.name = name;
    this.daysLeft = daysLeft;
  }

  @Override
  public String toString() {
    return this.name + " (" + this.daysLeft + " days)";
  }

  public boolean isOverDue() {
    return this.daysLeft < 0;
  }

  public String renew(int days) {
    if (this.daysLeft < 0) {
      return "Unable to Renew: " + this.toString(); 
    }
    this.daysLeft += days;
    return "Successfully Renewed: " + this.toString(); 
  }

  public abstract String renew();

  public int calcFines() {
    if (this.daysLeft >= 0) {
      return 0;
    }

    return this.daysLeft * -1;
  }

}
