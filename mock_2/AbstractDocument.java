public abstract class AbstractDocument implements Printable {

  private String name;
  private int pages;

  public AbstractDocument(String name, int pages) throws IllegalArgumentException {
    if (name == null || name.equals("")) {
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
