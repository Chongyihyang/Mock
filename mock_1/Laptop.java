public class Laptop extends Equipment {

  private int initialCost = 25;
  private int laterCost = 15;
  private int hourLimit = 2;

  public Laptop(String id) {
    super(id);
  }

  @Override
  public int rentalFee(int hours) {
    if (hours <= this.hourLimit) {
      return hours * this.hourLimit;
    }

    int remainingHours = hours - this.hourLimit;
    return this.hourLimit * this.initialCost +  remainingHours * this.laterCost;
  }

}
