public class Test2 {
 public static void main(String[] args) throws Throwable {
java.util.List<Customer> people=new java.util.ArrayList<>();people.add(new Customer("A","B"));
Account a=new Account("a",10,people);
Account b=new Account("b",3,java.util.List.of(new Customer("C","D")));
Account added=a.deposit(5);
CS2030STest.check("original amount", 10.0, () -> a.getBalance());
CS2030STest.check("new amount", 15.0, () -> added.getBalance());
Pair<Account,Account> pair=a.transferTo(b,2);
CS2030STest.check("transfer source unchanged", 10.0, () -> a.getBalance());
CS2030STest.check("transfer target unchanged", 3.0, () -> b.getBalance());
CS2030STest.check("transfer first", 8.0, () -> pair.first().getBalance());
CS2030STest.check("transfer second", 5.0, () -> pair.second().getBalance());
Account closed=a.close();
CS2030STest.check("original state", false, () -> a.isClosed());
CS2030STest.check("new state", true, () -> closed.isClosed());
Account merged=a.combine(b);
CS2030STest.check("combined amount", 13.0, () -> merged.getBalance());
CS2030STest.check("combined people", 2, () -> merged.getOwners().size());
people.clear();
CS2030STest.check("constructor defensive copy", 1, () -> a.getOwners().size());
CS2030STest.check("getter cannot mutate internal list", 1, ()->{try {a.getOwners().clear();} catch(UnsupportedOperationException e) { } return a.getOwners().size();});
 CS2030STest.finish();
 }
}
