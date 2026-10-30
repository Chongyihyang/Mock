public class Scan extends Job {

  private int energy;
  private boolean executed = false;

  public Scan(String name, int energy) {
    super(name);
    this.energy = energy;
  }
  
  @Override
  public boolean hasExecuted() {
    return this.executed;
  }

  @Override
  public String toString() {
    String res = super.toString() + " {next " + this.energy + " energy} [";
    if (this.hasExecuted()) {
      res += "done]";
    } else {
      res += "pending]";
    }

    return res;
  }

  @Override
  public boolean enoughEnergy(int curr) {
    return curr >= this.energy;
  }

  public int execute(int curr) {
    this.executed = true;
    int store = this.energy;
    this.energy = 0;
    return curr - store;
  }
  
}
