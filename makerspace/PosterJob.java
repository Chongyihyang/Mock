public class PosterJob extends WorkItem {

  private final int colour;

  public PosterJob(String label, int sheets, int colour) {
    super(label, sheets);
    this.colour = colour;
  }

  @Override
  public int minutes() {
    return 3 * super.sheets() + this.colour;
  }

  @Override
  public int price() {
    return 2 * this.sheets() + 2 * this.colour;
  }

}
