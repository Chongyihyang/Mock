public class Bike extends Vehicle implements Foldable {

  private double totalDistance = 0;
  private boolean folded = false;

  public Bike() {}

  @Override
  public void fold() {
    folded = true;
  }

  @Override
  public void unfold() {
    folded = false;
  }

  @Override
  public String toString() {
    return "Bike distance: " + helperRounder.round2DP(this.totalDistance) + " folded: " + this.folded;
  }

  @Override
  public void move(double distance) throws CannotMoveException {
    if (this.folded) {
      throw new CannotMoveException();
    }

    this.totalDistance += distance;
  }

}
