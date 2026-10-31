import java.util.*;
import java.util.function.*;
import java.util.stream.*;
public class Test2 {
 public static void main(String[] args) throws Throwable {
Map<String, List<Integer>> customers = Map.of("Michelle", List.of(12,56), "Enzio", List.of(34,90), "Michael", List.of(78));
Map<Integer, Double> sales = Map.of(12,12.0,34,6.0,56,7.5,78,9.0,90,17.0);
CS2030STest.check("all rows", List.of("Enzio: 17.0","Enzio: 6.0","Michael: 9.0","Michelle: 12.0","Michelle: 7.5"), () -> Query.allCustomerCosts(customers,sales).sorted().collect(Collectors.toList()));
CS2030STest.check("totals", List.of("Enzio: 23.0","Michael: 9.0","Michelle: 19.5"), () -> Query.totaledCustomerCosts(customers,sales).sorted().collect(Collectors.toList()));
CS2030STest.check("missing purchases", List.of("Enzio: 0.0","Michael: 0.0","Michelle: 0.0"), () -> Query.totaledCustomerCosts(customers,Map.of()).sorted().collect(Collectors.toList()));
CS2030STest.check("empty table", 0L, () -> Query.allCustomerCosts(Map.of(),sales).count());
CS2030STest.check("duplicates counted", List.of("A: 24.0"), () -> Query.totaledCustomerCosts(Map.of("A",List.of(12,12)),sales).collect(Collectors.toList()));

 }
}
