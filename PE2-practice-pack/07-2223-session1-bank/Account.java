import java.util.List;
import java.util.ArrayList;
/** Mutable starter: refactor for Task 1. */
public class Account {
 private String id; private double balance; private List<Customer> owners; private boolean closed;
 public Account(String id,double amount,List<Customer> people) { this.id=id;this.balance=amount;this.owners=people;this.closed=false; }
 public String getId() { return id; }
 public double getBalance() { return balance; }
 public List<Customer> getOwners() { return owners; }
 public boolean isClosed() { return closed; }
 public Account deposit(double amount) { this.balance+=amount;return this; }
 public Account withdraw(double amount) { this.balance-=amount;return this; }
 public Account close() { this.closed=true;return this; }
 public Pair<Account,Account> transferTo(Account other,double amount) { this.withdraw(amount);other.deposit(amount);return new Pair<>(this,other); }
 public Account combine(Account other) {
  this.balance+=other.balance;
  List<Customer> all=new ArrayList<>(this.owners);all.addAll(other.owners);this.owners=all;return this;
 }
 public String toString() { return id+": "+balance+" ["+closed+"] "+owners; }
 public boolean equals(Object o) { return o instanceof Account && id.equals(((Account)o).id) && balance==((Account)o).balance && closed==((Account)o).closed && owners.equals(((Account)o).owners); }
 public int hashCode() { return java.util.Objects.hash(id,balance,closed,owners); }
}
