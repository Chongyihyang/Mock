import java.util.*;
import java.util.function.*;
import java.util.stream.*;
public class Test1 {
 public static void main(String[] args) throws Throwable {
Map<String, List<Integer>> customers = Map.of("Michelle", List.of(12,56), "Enzio", List.of(34,90), "Michael", List.of(78));
Map<Integer, Double> sales = Map.of(12,12.0,34,6.0,56,7.5,78,9.0,90,17.0);
CS2030STest.check("filter", List.of("Enzio=[34, 90]"), () -> Query.getFilteredByKey(customers, x -> x.equals("Enzio")).map(Object::toString).collect(Collectors.toList()));
CS2030STest.check("ids", List.of(12,56), () -> Query.getIdsFromName(customers,"Michelle").collect(Collectors.toList()));
CS2030STest.check("absent name", List.of(), () -> Query.getIdsFromName(customers,"Sam").collect(Collectors.toList()));
CS2030STest.check("ordered costs", List.of(17.0,12.0,17.0), () -> Query.getCostsFromIDs(sales,Stream.of(90,7,12,90)).collect(Collectors.toList()));
CS2030STest.check("large ID value equality", List.of(9.0), () -> Query.getCostsFromIDs(Map.of(1000,9.0),Stream.of(Integer.valueOf("1000"))).collect(Collectors.toList()));

 }
}
