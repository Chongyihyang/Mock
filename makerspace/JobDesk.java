public class JobDesk {

  private int counter = 0;
  private int totalCharge;
  private int sheets;
  private int minutes;

  public JobDesk(int sheets, int minutes) {
    this.sheets = sheets;
    this.minutes = minutes;
  }

  public Receipt submit(WorkItem item) throws CapacityException {
    if (this.sheets < item.sheets()) {
      throw new CapacityException("sheets", item.sheets(), this.sheets);
    }

    if (this.minutes < item.minutes()) {
      throw new CapacityException("minutes", item.minutes(), this.minutes);
    }
    this.sheets -= item.sheets();
    this.minutes -= item.minutes();
    this.totalCharge += item.price();
    counter++;
    return new Receipt(item, counter);
  }

  public int remainingSheets() {
    return this.sheets;
  }

  public int remainingMinutes() {
    return this.minutes;
  }

  public int totalCharged() {
    return this.totalCharge;
  }

}
