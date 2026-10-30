/** Complete the public API and choose your own supporting types.
 * @author YOUR_STUDENT_NUMBER
 */
public class RoverMission {
  private Seq<Job> seq;
  private int curr = 0;
  private int energy;
  private int used = 0;

  public RoverMission(int capacity, int energy) {
    // throw new UnsupportedOperationException("TODO: constructor");
    this.seq = new Seq<>(capacity);
    this.energy = energy;
  }

  public String addScan(String name, int cost) {
    Scan scan = new Scan(name, cost);
    this.seq.set(scan);
    this.curr += 1;
    return "Added: " + scan.toString(); 
    // throw new UnsupportedOperationException("TODO: addScan");
  }

  public String status() {
    // throw new UnsupportedOperationException("TODO: status");
    String res = "";
    for (int i = 0; i < this.curr; i ++) {
      res += i + ": " + this.seq.get(i).toString() + "\n";
    }
    return res;
  }

  public String pending() {
    // throw new UnsupportedOperationException("TODO: pending");
    String res = "";                                    
    for (int i = 0; i < this.curr; i ++) {
      Job j = this.seq.get(i);
      if (!j.hasExecuted()) {
        res += i + ": " + j.toString() + "\n";
      }
    }
    return res;
  }

  public String execute(int index) {
    Job j = this.seq.get(index);
    if (j.hasExecuted()) {
      return "Cannot execute: " + j.toString();
    }

    if (!j.enoughEnergy(this.energy)) {
      return "Insufficient energy: " + j.toString();
    }
    int before = this.energy;
    this.energy = j.execute(this.energy);
    this.used += before - this.energy;
    return "Executed: " + j.toString();

    // throw new UnsupportedOperationException("TODO: execute");
  }

  public int recharge(int units) {
    this.energy += units;
    return this.energy;
    // throw new UnsupportedOperationException("TODO: recharge");
  }

  public int energyLeft() {
    return this.energy;
  }

  public int energyUsed() {
    return this.used;
  }
  
  public String addSweep(String name, int cost, int sweep) {
    Sweep sweep_ = new Sweep(name, cost, sweep);
    this.seq.set(sweep_);
    this.curr += 1;
    return "Added: " + sweep_.toString(); 
    // throw new UnsupportedOperationException("TODO: addScan");
  }

  public String addBeacon(String name, int cost, int beacon) {
    Beacon sweep_ = new Beacon(name, cost, beacon);
    this.seq.set(sweep_);
    this.curr += 1;
    return "Added: " + sweep_.toString(); 
    // throw new UnsupportedOperationException("TODO: addScan");
  }
}
