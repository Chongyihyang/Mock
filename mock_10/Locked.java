public class Locked extends Parcel {

  private final String code;

  public Locked(String name, int daysLeft, String code) {
    super(name, daysLeft);
    this.code = code;
  }

  @Override
  public String toString() {
    return super.toString() + " [locker " + this.code + "]";
  }

  @Override
  public boolean canRenew() {
    return false;
  }

  @Override
  public int overdueFees() {
    int res = super.overdueFees();
    if (res == 0) {
      return 0;
    }

    return 7;
  }

  @Override
  public boolean extend() {
    return false;
  }

}
