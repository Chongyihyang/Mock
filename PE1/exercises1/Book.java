public class Book extends LoanItem {
 
  private final int maxRenew = 3;
  private int currentRenew = 0;

  public Book(String name, int daysLeft) {
    super(name, daysLeft);
  }

  @Override
  public String renew() {
    String res = "";
    if (this.currentRenew <  this.maxRenew && !this.isOverDue()) {
      this.currentRenew++;
      res += super.renew(14);
    } else {
      return "Unable to Renew: " + this.toString();
    }
    return res;
  }

  @Override
  public int calcFines() {
    return super.calcFines() * 2;
  }


  @Override
  public String toString() {
    String res = super.toString();
    if (currentRenew > 0) {
      res += " {" + this.currentRenew + " times}";
    }
    return res;
  }

}
