import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import cs2030s.fp.Maybe;
/** Imperative starter: preserve behavior while refactoring for Task 2. */
public class Bank {
 private final Map<String,Account> entries;
 public Bank(List<Account> initial) { entries=new TreeMap<>();initial.forEach(x->entries.put(x.getId(),x)); }
 public Account findAccount(String id) { return entries.get(id); }
 public void deposit(String id,double amount) { Account a=findAccount(id);if(a!=null) { a.deposit(amount); } }
 public void withdraw(String id,double amount) { Account a=findAccount(id);if(a!=null) { a.withdraw(amount); } }
 public void closeAccount(String id) { Account a=findAccount(id);if(a!=null) { a.close(); } }
 public double totalMoneyInBank() { double sum=0;for(Account a:entries.values()) { if(!a.isClosed()) { sum+=a.getBalance(); } }return sum; }
 public String allAccounts() {
  List<Account> all=new ArrayList<>(entries.values());
  
  String result="";for(Account a:all) { result+=a+"\n"; }return result;
 }
 public void transfer(String from,String to,double amount) {
  Account a=findAccount(from);Account b=findAccount(to);
  if(a==null || b==null) {return;} if(a.isClosed() || b.isClosed() || a.getBalance()<=0) {return;} a.transferTo(b,amount);
 }
 public void combineBankAccount(String from,String to) {
  Account a=findAccount(from);Account b=findAccount(to);
  if(a==null || b==null) {return;} if(!a.isClosed() || b.isClosed()) {return;} b.combine(a);entries.remove(from);
 }
 public boolean equals(Object o) { return o instanceof Bank && entries.equals(((Bank)o).entries); }
 public int hashCode() { return entries.hashCode(); }
}
