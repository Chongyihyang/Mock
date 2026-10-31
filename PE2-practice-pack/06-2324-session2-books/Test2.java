import java.util.*;
import java.util.function.*;
import java.util.stream.*;
public class Test2 {
 public static void main(String[] args) throws Throwable {
Classic je=new Classic("Jane Eyre",8.4); Classic wh=new Classic("Wuthering Heights",6.6);
SciFi tb=new SciFi("The Three-Body Problem",22.4);
BookShop<Book> a=new BookShop<>(List.of(je,tb));
BookShop<Classic> b=new BookShop<>(List.of(wh));
BookShop<Book> c=new BookShop<>(List.of(new Classic("Jane Eyre",7),tb));
BookShop<ClearanceBook> d=new BookShop<>(List.of(new ClearanceBook("Effective Java","education"),new ClearanceBook("Jane Eyre","romance")));
CS2030STest.check("empty format", "", () -> new BookShop<Book>().toString());
CS2030STest.check("shop format", "\n- Wuthering Heights (S$ 6.60)\n", () -> b.toString());
List<Classic> classics=List.of(je,wh); List<Book> wide=new BookShop<Book>(classics).getBooks();
CS2030STest.check("widen", 2, () -> wide.size());
CS2030STest.check("has book", true, () -> a.hasBook("Jane Eyre"));
CS2030STest.check("no book", false, () -> a.hasBook("John Eyre"));
CS2030STest.check("filter genre", List.of(je), () -> a.findBooksByGenre("classics"));
BookShop<Book> merged=Q4.mergeShops(a,b);
CS2030STest.check("merge order", List.of(je,tb,wh), () -> merged.getBooks());
CS2030STest.check("unique genres", List.of("classics","education","romance","sci-fi"), () -> Q5.findUniqueGenres(List.of(a,b,c,d)));

 CS2030STest.finish();
 }
}
