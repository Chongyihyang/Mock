public abstract class Equipment implements Rentable {

  private String id;
  private boolean available = true;
  private int hours = 0;

  public Equipment(String id) {
    this.id = id;
  }

  public final String id() {
    return this.id;
  }

  @Override
  public final int rent(int hours) throws Exception {
    if (hours <= 0) {
      throw new InvalidDurationException(hours);
    }

    if (!this.available) {
      throw new EquipmentUnavailableException(this.id);
    }

    this.available = false;
    this.hours = hours;
    return this.rentalFee(this.hours);
  }

  @Override
  public final void returnItem() {
    this.available = true;
  }

  public abstract int rentalFee(int hours);

  @Override
  public String toString() {
    return String.format("%s: $%d", this.id, this.rentalFee(this.hours));
  }

}
