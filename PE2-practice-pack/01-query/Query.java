import java.util.*;
import java.util.function.*;
import java.util.stream.*;
public class Query {

  public static <T, S> Stream<Map.Entry<T, S>> getFilteredByKey(Map<T, S> table, Predicate<T> p) { 
    return table.entrySet().stream().filter(x -> p.test(x.getKey()));
  }

  public static Stream<Integer> getIdsFromName(Map<String, List<Integer>> customerTable, String name) { 
    return customerTable.entrySet()
      .stream()
      .filter(x -> x.getKey().equals(name))
      .map(x -> x.getValue().stream())
      .reduce((f, s) -> f)
      .orElse(Stream.of());
  }

  public static Stream<Double> getCostsFromIDs(Map<Integer, Double> salesTable, Stream<Integer> ids) { 
    return ids.map(x -> salesTable.get(x)).filter(x -> x != null);
  }


  public static Stream<String> allCustomerCosts(Map<String, List<Integer>> customerTable, Map<Integer, Double> salesTable) { 
    // 0. gert all ids through salesTable
    // 1. getIdsFromName will check if id is inside
    // 2. if inside it will append name and cost together
    return salesTable
      .entrySet()
      .stream()
      .map(x -> 
        customerTable
        .entrySet()
        .stream()
        .filter(y -> y.getValue().contains(x.getKey()))
        .map(y -> y.getKey() + ": " + x.getValue())
        .reduce((a, b) -> a)
        .orElse(null)
      )
      .filter(x -> x != null);
  }


  public static Stream<String> totaledCustomerCosts(Map<String, List<Integer>> customerTable, Map<Integer, Double> salesTable) { 
    return customerTable
      .entrySet()
      .stream()
      .map(x -> x.getKey().toString() + ": " +  
        Query
        .getCostsFromIDs(salesTable, 
          Query.getIdsFromName(customerTable, x.getKey()))
        .reduce(0.0, (z,y) -> z+ y)
        .toString()
      );
  }

}
