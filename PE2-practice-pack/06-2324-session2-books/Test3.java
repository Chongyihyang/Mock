import java.util.*;
import java.util.function.*;
import java.util.stream.*;
public class Test3 {
 public static void main(String[] args) throws Throwable {
Classic je=new Classic("Jane Eyre",8.4); Classic wh=new Classic("Wuthering Heights",6.6);
SciFi tb=new SciFi("The Three-Body Problem",22.4);
BookShop<Book> a=new BookShop<>(List.of(je,tb));
BookShop<Classic> b=new BookShop<>(List.of(wh));
BookShop<Book> c=new BookShop<>(List.of(new Classic("Jane Eyre",7),tb));
BookShop<ClearanceBook> d=new BookShop<>(List.of(new ClearanceBook("Effective Java","education"),new ClearanceBook("Jane Eyre","romance")));
List<BookShop<Book>> grouped=Q6.consolidateShopsbyGenre(List.of(a,b,c,d));
CS2030STest.check("group order", List.of("classics","education","romance","sci-fi"), () -> grouped.stream().map(s->s.getBooks().get(0).getGenre()).collect(Collectors.toList()));
CS2030STest.check("group size", List.of(3,1,1,2), () -> grouped.stream().map(s->s.getBooks().size()).collect(Collectors.toList()));
List<BookShop<DiscountedBook>> discounted=Q7.discountRemainingStock(List.of(a,b,c,d),2);
CS2030STest.check("only small shops", 1, () -> discounted.size());
CS2030STest.check("discount amount", 3.3, () -> discounted.get(0).getBooks().get(0).getPrice());
CS2030STest.check("original price unchanged", 6.6, () -> wh.getPrice());
CS2030STest.check("strict threshold", 0, () -> Q7.discountRemainingStock(List.of(b),1).size());
CS2030STest.check("search sorted prices", List.of(0.5,7.0,8.4), () -> Q8.searchForBook(List.of(a,b,c,d),"Jane Eyre").stream().map(s->s.getBooks().get(0).getPrice()).collect(Collectors.toList()));
CS2030STest.check("search keeps versions", List.of(2,1), () -> Q8.searchForBook(List.of(c,Q4.mergeShops(a,d)),"Jane Eyre").stream().map(s->s.getBooks().size()).collect(Collectors.toList()));
CS2030STest.check("missing title", 0, () -> Q8.searchForBook(List.of(a,b,c,d),"Missing").size());

 CS2030STest.finish();
 }
}
