public class Sweep extends Scan {
  
  private int curr = 0;
  private int sweep = 0;

  public Sweep(String name, int energy, int sweep) {
    super(name, energy);
    this.sweep = sweep;
  }

  @Override
  public String toString() {
    return super.toString() + " [sweep " + this.curr + "/" + this.sweep + "]";
  }

}
