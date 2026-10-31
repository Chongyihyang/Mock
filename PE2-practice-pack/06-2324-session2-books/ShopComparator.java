/** Reconstructed provided helper. Empty shops sort last. */
public class ShopComparator<T extends Book> implements java.util.Comparator<BookShop<? extends T>> {
 public int compare(BookShop<? extends T> a, BookShop<? extends T> b) {
  return Double.compare(a.getBooks().stream().mapToDouble(Book::getPrice).min().orElse(Double.POSITIVE_INFINITY), b.getBooks().stream().mapToDouble(Book::getPrice).min().orElse(Double.POSITIVE_INFINITY));
 }
}
