public class Cut extends Job {

  private int pieces = 0;

  public Cut(String name, int pieces) {
    if (pieces <= 0) {
      throw new IllegalArgumentException("Pieces must be positive");
    }
    super(name);
    this.pieces = pieces;
  }

  @Override
  public String toString() {
    String addOn = String.format(" [pieces=%d]", this.pieces);
    return super.toString() + addOn;
  }

  @Override
  public void execute() {
    this.pieces -= 1;
  }

  @Override
  public boolean isCompleted() {
    return this.pieces == 0;
  }

}
