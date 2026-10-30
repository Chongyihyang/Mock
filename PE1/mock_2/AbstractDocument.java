public abstract class AbstractDocument implements Printable {

  private final String name;
  private final int pages;

  public AbstractDocument(String name, int pages) throws IllegalArgumentException {
    if (name == null || name.trim().equals("")) {
      throw new IllegalArgumentException();
    } else if (pages <= 0) {
      throw new IllegalArgumentException();
    } 
    this.name = name;
    this.pages = pages;
  }

  @Override
  public final String name() {
    return this.name;
  }

  public final int pageCount() {
    return this.pages;
  }

}
