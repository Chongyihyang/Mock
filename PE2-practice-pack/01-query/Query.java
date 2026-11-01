import java.util.*;
import java.util.function.*;
import java.util.stream.*;
public class Query {

  public static <T, S> Stream<Map.Entry<T, S>> getFilteredByKey(Map<T, S> table, Predicate<T> p) { 
    return table
      .entrySet()
      .stream()
      .filter(x -> p.test(x.getKey()));
  }

  public static Stream<Integer> getIdsFromName(Map<String, List<Integer>> customerTable, String name) { 
    return customerTable.entrySet()
      .stream()
      .filter(x -> x.getKey().equals(name))
      .flatMap(x -> x.getValue().stream());
  }

  public static Stream<Double> getCostsFromIDs(Map<Integer, Double> salesTable, Stream<Integer> ids) { 
    return ids
      .map(x -> salesTable.get(x))
      .filter(x -> x != null);
  }


  public static Stream<String> allCustomerCosts(Map<String, List<Integer>> customerTable, Map<Integer, Double> salesTable) { 
    return customerTable
      .entrySet()
      .stream()
      .flatMap(x -> 
        x
        .getValue()
        .stream()
        .map(y -> salesTable.get(y))
        .filter(z -> z != null)
        .map(z -> x.getKey() + ": " + z)
      );
  }


  public static Stream<String> totaledCustomerCosts(Map<String, List<Integer>> customerTable, Map<Integer, Double> salesTable) { 
    return customerTable
      .entrySet()
      .stream()
      .map(x -> x.getKey().toString() + ": " +  
          Query
          .getCostsFromIDs(salesTable, Query.getIdsFromName(customerTable, x.getKey()))
          .reduce(0.0, (z,y) -> z + y)
          .toString()
          );
  }

}
