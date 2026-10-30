public final class DuplexDocument extends AbstractDocument {

  public DuplexDocument(String name, int pages) {
    super(name, pages);
  }

  @Override
  public int sheetsNeeded() {
    int size = super.pageCount();
    return size / 2 + size % 2;
  }

}
