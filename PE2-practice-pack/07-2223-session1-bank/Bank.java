import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import cs2030s.fp.Maybe;
/** Imperative starter: preserve behavior while refactoring for Task 2. */


public class Bank {

 private final Map<String,Account> entries;

 public Bank(List<Account> initial) { 
   entries = new TreeMap<>();
   initial.stream().toList().forEach(x->entries.put(x.getId(),x)); 
 }

 public Account findAccount(String id) { 
   return entries.get(id); 
 }

 public void deposit(String id,double amount) { 
   Maybe.of(findAccount(id)).map(x -> x.deposit(amount));
 }

 public void withdraw(String id,double amount) { 
   Maybe.of(findAccount(id)).map(x -> x.withdraw(amount));
 }

 public void closeAccount(String id) { 
   Maybe.of(findAccount(id)).map(x -> x.close());
 }

 public double totalMoneyInBank() { 
   return this.entries
     .values()
     .stream()
     .filter(a -> !a.isClosed())
     .reduce(
         new Account("", 0, List.of()), 
         (x, y) -> y.combine(x))
     .getBalance();
 }

 public String allAccounts() {
  return this.entries
    .values()
    .stream()
    .map(x -> x.toString())
    .reduce("", (x, y) -> x + "\n" + y);
 }

 public void transfer(String from,String to,double amount) {
  Maybe
    .of(findAccount(from))
    .filter(a -> !a.isClosed())
    .filter(a -> a.getBalance() > 0)
    .map(a ->
        Maybe
        .of(findAccount(to))
        .filter(b -> !b.isClosed())
        .map(b -> a.transferTo(b, amount)));
 }

 public void combineBankAccount(String from,String to) {
  Maybe
    .of(findAccount(from))
    .filter(a -> !a.isClosed())
    .map(a ->
        Maybe
        .of(findAccount(to))
        .filter(b -> !b.isClosed())
        .map(b -> b.combine(a))
        .map(_ -> this.entries.remove(from)));
 }

 public boolean equals(Object o) { return o instanceof Bank && entries.equals(((Bank)o).entries); }

 public int hashCode() { return entries.hashCode(); }

}
