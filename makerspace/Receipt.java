public class Receipt {

  private final int RNnumber;
  private final WorkItem item;

  public Receipt(WorkItem item, int RNnumber) {
    this.RNnumber = RNnumber;
    this.item = item;
  }

  public int number() {
    return this.RNnumber;
  }

  public String label() {
    return this.item.label();
  }

  public int price() {
    return this.item.price();
  }

  @Override
  public String toString() {
    return String.format("#%d %s: $%d",
        this.RNnumber, this.label(), this.price());
  }

}
