public final class SingleSidedDocument extends AbstractDocument {

  public SingleSidedDocument(String name, int pages) {
    super(name, pages);
  }

  @Override
  public int sheetsNeeded() {
    return super.pageCount();
  }

}
