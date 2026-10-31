public class Test3 {
 public static void main(String[] args) throws Throwable {
Bank m=new Bank(java.util.List.of(new Account("a",10,java.util.List.of(new Customer("A","B"))),new Account("b",-3,java.util.List.of(new Customer("C","D")))));
CS2030STest.check("initial total", 7.0, () -> m.totalMoneyInBank());
m.deposit("a",2);m.withdraw("b",1);
CS2030STest.check("updated total", 8.0, () -> m.totalMoneyInBank());
m.deposit("missing",99);m.transfer("a","missing",1);
CS2030STest.check("missing no op", 8.0, () -> m.totalMoneyInBank());
m.transfer("a","b",2);
CS2030STest.check("transfer conserves total", 8.0, () -> m.totalMoneyInBank());
CS2030STest.check("transfer values", true, () -> m.allAccounts().contains("a: 10.0") && m.allAccounts().contains("b: -2.0"));
m.closeAccount("a");m.transfer("a","b",1);
CS2030STest.check("closed excluded", -2.0, () -> m.totalMoneyInBank());
m.combineBankAccount("a","b");
CS2030STest.check("merge total", 8.0, () -> m.totalMoneyInBank());
CS2030STest.check("source removed", false, () -> m.allAccounts().contains("a:"));

 CS2030STest.finish();
 }
}
