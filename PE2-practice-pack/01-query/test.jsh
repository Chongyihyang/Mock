Map<String, List<Integer>> customerTable = Map.of(
 "Michelle", List.of(12, 56),
 "Enzio",    List.of(34, 90),
 "Michael", List.of(78));
Map<Integer, Double> salesTable = Map.of(
 12, 12.0,
 34, 6.0,
 56, 7.5,
 78, 9.0,
 90, 17.0)
Map<String, List<Integer>> badCustomerTable = Map.of(
 "Bill", List.of(17),
 "Sam", List.of(19));
Map<Integer, Double> badSalesTable = Map.of(
 99, 3.0,
 98, 2.0);
Query.allCustomerCosts(customerTable,
salesTable).forEach(System.out::println);

System.out.println("------");

Map<String, List<Integer>> customerTable = Map.of(
 "Michelle", List.of(12, 56),
 "Enzio",    List.of(34, 90),
 "Michael", List.of(78));
Map<Integer, Double> salesTable = Map.of(
 12, 12.0,
 34, 6.0,
 56, 7.5,
 78, 9.0,
 90, 17.0)
Map<String, List<Integer>> badCustomerTable = Map.of(
 "Bill", List.of(17),
 "Sam", List.of(19));
Map<Integer, Double> badSalesTable = Map.of(
 99, 3.0,
 98, 2.0);
Query.totaledCustomerCosts(customerTable,salesTable).forEach(System.out::println);
