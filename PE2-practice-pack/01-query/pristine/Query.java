import java.util.*;
import java.util.function.*;
import java.util.stream.*;
public class Query {
 public static <T, S> Stream<Map.Entry<T, S>> getFilteredByKey(Map<T, S> table, Predicate<T> p) { throw new UnsupportedOperationException("TODO: getFilteredByKey"); }
 public static Stream<Integer> getIdsFromName(Map<String, List<Integer>> customerTable, String name) { throw new UnsupportedOperationException("TODO: getIdsFromName"); }
 public static Stream<Double> getCostsFromIDs(Map<Integer, Double> salesTable, Stream<Integer> ids) { throw new UnsupportedOperationException("TODO: getCostsFromIDs"); }
 public static Stream<String> allCustomerCosts(Map<String, List<Integer>> customerTable, Map<Integer, Double> salesTable) { throw new UnsupportedOperationException("TODO: allCustomerCosts"); }
 public static Stream<String> totaledCustomerCosts(Map<String, List<Integer>> customerTable, Map<Integer, Double> salesTable) { throw new UnsupportedOperationException("TODO: totaledCustomerCosts"); }
}
