import java.util.*;
import java.util.function.*;
import java.util.stream.*;
public class Test1 {
 public static void main(String[] args) throws Throwable {
CS2030STest.check("book format", "1984 (S$ 5.50)", () -> new Book("1984","sci-fi",()->5.5).toString());
CS2030STest.check("title", "1984", () -> new Book("1984","sci-fi",()->5.5).getTitle());
CS2030STest.check("genre", "sci-fi", () -> new Book("1984","sci-fi",()->5.5).getGenre());
CS2030STest.check("price", 5.5, () -> new Book("1984","sci-fi",()->5.5).getPrice());
CS2030STest.check("title value equality", true, () -> new Book("1984","sci-fi",()->5.5).hasTitle(new String("1984")));
CS2030STest.check("different title", false, () -> new Book("1984","sci-fi",()->5.5).hasTitle("1985"));

 CS2030STest.finish();
 }
}
