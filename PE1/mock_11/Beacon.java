public class Beacon extends Scan {
  
  private int curr = 0;
  private int max_ = 0;

  public Beacon(String name, int energy, int sweep) {
    super(name, energy);
    this.max_ = sweep;
  }

  @Override
  public String toString() {
    return super.toString() + " [beacon " + this.curr + "]";
  }


}
