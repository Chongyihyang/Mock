public abstract class WorkItem {

  private final String label;
  private final int sheets;

  public WorkItem(String label, int sheets) {
    this.label = label;
    this.sheets = sheets;
  }

  public String label() {
    return this.label;
  }

  public int sheets() {
    return this.sheets;
  }

  public abstract int minutes();

  public abstract int price();

  @Override
  public String toString() {
    return String.format("%s [sheets=%d, minutes=%d, price=%d]",
        this.label(), this.sheets(), this.minutes(), this.price());
  }

}
