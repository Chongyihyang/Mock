public class Dumbbell extends Equipment {

  private final double weight;
  private int numberOfTimesRepaired = 0;

  public Dumbbell(double weight) {
    this.weight = weight;
  }

  @Override
  public void repair() {
    this.numberOfTimesRepaired++;
  }

  public double getWeight() {
    return this.weight;
  }

  @Override
  public String toString() {
    return "Dumbbell: " + weight + " kg";
  }

  public int getRepairCount() {
    return this.numberOfTimesRepaired;
  }

}
