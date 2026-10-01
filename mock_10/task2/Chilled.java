public class Chilled extends Parcel {

  private final static int maxRenew = 1;

  public Chilled(String name, int daysLeft) {
    super(name, daysLeft);
  }

  @Override
  public String toString() {
    return super.toString() + " [chilled]";

  }

  @Override
  public boolean canRenew() {
    return super.getExtensions() < Chilled.maxRenew;
  }

  @Override
  public int overdueFees() {
    return super.overdueFees() / 2 * 4;
  }

  @Override
  public boolean extend() {
    if (!this.isOverdue() && this.canRenew()) {
      super.addDays(1);
      return true;
    }

    return false;
  }


}
