public class Parcel {

  private final String name;
  private int daysLeft;
  private int extensions = 0;
  private final static int maxRenew = 2;
  private boolean collected = false;

  public Parcel(String name, int daysLeft) {
    this.name = name;
    this.daysLeft = daysLeft;
  }

  @Override
  public String toString() {
    String res = "";
    res += this.name + " (" + this.daysLeft + " days)";
    if (this.extensions != 0) {
      res += " {" + this.extensions + " extensions}";
    }

    return res;

  }

  public int getExtensions() {
    return this.extensions;
  }

  public boolean isOverdue() {
    return this.daysLeft < 0;
  }

  public boolean canRenew() {
    return this.extensions < Parcel.maxRenew;
  }

  public int overdueFees() {
    if (!this.isOverdue()) {
      return 0;
    }

    return this.daysLeft * -2;
  }

  public void addDays(int days) {
    this.daysLeft += days;
    this.extensions ++;
  }

  public boolean extend() {
    if (!this.isOverdue() && this.canRenew()) {
      this.addDays(3);
      return true;
    }

    return false;
  }

  public boolean hasBeenCollected() {
    return this.collected;
  }

  public boolean collectItem() {
    if (this.hasBeenCollected()) {
      return false;
    }

    this.collected = true;
    return true;
  }

}
