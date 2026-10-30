public class Treadmill extends Equipment {

  private double speed = 0.0;

  public double getSpeed() {
    return this.speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }
  
  @Override
  public void repair() {
    this.speed = 0;
  }

  @Override
  public String toString() {
    return "Treadmill: " + this.speed + " km/h";
  }
}
