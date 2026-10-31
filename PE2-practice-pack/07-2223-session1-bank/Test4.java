public class Test4 {
 public static void main(String[] args) throws Throwable {
Bank m=new Bank(java.util.List.of(new Account("a",10,java.util.List.of(new Customer("A","B")))));
CS2030STest.check("Maybe lookup", true, () -> m.findAccount("a").map(x->x.getBalance()==10).orElse(false));
CS2030STest.check("Maybe absent", false, () -> m.findAccount("no").map(x->true).orElse(false));

 CS2030STest.finish();
 }
}
