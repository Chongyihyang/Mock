/** Reconstructed provided helper: fractionOff=0.5 means 50% off. */
public class DiscountedBook extends Book {
 public DiscountedBook(Book book, double fractionOff) {
  super(book.getTitle(),book.getGenre(),()->book.getPrice()*(1-fractionOff));
 }
}
