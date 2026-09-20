public class Printer {

  private boolean online = true;
  private int sheets;

  public Printer(int initialSheets) throws IllegalArgumentException {
    if (initialSheets < 0) {
      throw new IllegalArgumentException();
    }
    this.sheets = initialSheets;
  }

  public int paperRemaining() {
    return this.sheets;
  }
  
  public void addPaper(int sheets) throws IllegalArgumentException {
    if (sheets <= 0) {
      throw new IllegalArgumentException();
    }
    this.sheets += sheets;
  }

  public void setOnline(boolean online) {
    this.online = online;
  }

  public String print(Printable item) throws PrintingException {
    if (!this.online) {
      throw new PrintingException("Printer is offline");
    }
    int sheetsRequired = item.sheetsNeeded();
    if (sheetsRequired > this.sheets) {
      throw new InsufficientPaperException(item.name(), sheetsRequired, this.sheets);
    }
    this.sheets -= sheetsRequired;
    return String.format("PRINTED %s: %d sheet(s)", item.name(), sheetsRequired);
  }

}

