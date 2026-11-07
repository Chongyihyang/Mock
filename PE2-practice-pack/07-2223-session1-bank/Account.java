import java.util.List;
import java.util.ArrayList;
/** Mutable starter: refactor for Task 1. */
public final class Account {

 private final String id; 
 private final double balance; 
 private final List<Customer> owners; 
 private boolean closed;

 public Account(String id,double amount,List<Customer> people) { 
   this.id = id;
   this.balance = amount;
   this.owners = people.stream().toList();
   this.closed = false; }

 public String getId() { return id; }

 public double getBalance() { return balance; }

 public List<Customer> getOwners() { return owners.stream().toList(); }

 public boolean isClosed() { return closed; }

 public Account deposit(double amount) { 
   return new Account(this.id, this.balance + amount, this.owners);
 }

 public Account withdraw(double amount) { 
   return new Account(this.id, this.balance - amount, this.owners);
 }

 public Account close() { 
   Account acc = new Account(this.id, this.balance, this.owners);
   acc.closed = true;
   return acc;
 }

 public Pair<Account,Account> transferTo(Account other,double amount) {
   Account res1 = new Account(this.id, this.balance - amount, this.owners);
   Account res2 = new Account(other.id, other.balance + amount, other.owners);
   res1.closed = this.closed;
   res2.closed = other.closed;
   return new Pair<>(res1, res2);
 }

 public Account combine(Account other) {
  List<Customer> all = new ArrayList<>(this.owners);
  all.addAll(other.owners);
  Account res1 = new Account(
      this.id, 
      this.balance + other.balance,
      all);
  res1.closed = this.closed;
  return res1;
 }

 public String toString() { return id+": "+balance+" ["+closed+"] "+owners; }

 public boolean equals(Object o) { return o instanceof Account && id.equals(((Account)o).id) && balance==((Account)o).balance && closed==((Account)o).closed && owners.equals(((Account)o).owners); }

 public int hashCode() { return java.util.Objects.hash(id,balance,closed,owners); }
}
