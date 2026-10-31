import java.util.*;
import java.util.function.*;
import java.util.stream.*;
public class Test1 {
 public static void main(String[] args) throws Throwable {
CS2030STest.check("fruit string", "Apple (expires in 5 days)", () -> new Fruit(()->"Apple",()->5).toString());
CS2030STest.check("name", "Apple", () -> new Fruit(()->"Apple",()->5).getName());
CS2030STest.check("expiry", 5, () -> new Fruit(()->"Apple",()->5).getDaysToExpiry());
int[] day={5}; Fruit live=new Fruit(()->"Apple",()->day[0]); day[0]=2;
CS2030STest.check("delegates to function", 2, () -> live.getDaysToExpiry());

 CS2030STest.finish();
 }
}
