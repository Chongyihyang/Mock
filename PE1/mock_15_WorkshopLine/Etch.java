public class Etch extends Job {

  private String status = "prepare";
  private String alternate = "etch";
  private int plates;

  public Etch(String name, int pieces) {
    if (pieces <= 0) {
      throw new IllegalArgumentException("Plates must be positive");
    }
    super(name);
    this.plates = pieces;
  }

  @Override
  public String toString() {
    String addOn = String.format(" [plates=%d, stage=%s]", this.plates, this.status);
    return super.toString() + addOn;
  }

  @Override
  public void execute() {
    if (this.status == "etch") {
      this.plates -= 1;
      if (this.plates == 0) {
        this.status = "done";
      }
      return;
    }
    String tmp = this.status;
    this.status = this.alternate;
    this.alternate = tmp;
  }

  @Override
  public boolean isCompleted() {
    if (this.status == "prepare") {
      return false;
    }
    return this.plates == 0;
  }

}
