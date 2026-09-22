public abstract class Workshop {

  private final String code;
  private final int fixedAdPrice;
  private final int capacity;
  private int currentOccupancy = 0;

  public Workshop(String code, int fixedAdPrice, int capacity) throws IllegalArgumentException {
    // check for name first
    if (code == null || code.trim().equals("")) {
      throw new IllegalArgumentException();
    }

    if (fixedAdPrice <= 0) {
      throw new IllegalArgumentException();
    }

    this.code = code;
    this.fixedAdPrice = fixedAdPrice;
    this.capacity = capacity;

  }

  public String join(Pass pass) throws FullWorkshopException {
    if (this.capacity <= this.currentOccupancy) {
      throw new FullWorkshopException("0");
    }
    this.currentOccupancy += 1;
    return pass.toString();
  }

}
