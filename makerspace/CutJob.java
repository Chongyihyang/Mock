public class CutJob extends WorkItem {

  private final int cuts;

  public CutJob(String label, int sheets, int cuts) {
    super(label, sheets);
    this.cuts = cuts;
  }

  @Override
  public int minutes() {
    return 2 * super.sheets() + this.cuts;
  }

  @Override
  public int price() {
    return 5 * this.sheets() + this.cuts;
  }

}
