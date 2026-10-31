public class Test1 {
 public static void main(String[] args) throws Throwable {
Customer original=new Customer("A","B");
Customer changed=original.changeName("C");
CS2030STest.check("original unchanged", "A (B)", () -> original.toString());
CS2030STest.check("new name", "C (B)", () -> changed.toString());
Customer updated=changed.changeAddress("D");
CS2030STest.check("previous unchanged", "C (B)", () -> changed.toString());
CS2030STest.check("new second value", "C (D)", () -> updated.toString());
CS2030STest.check("value equality", true, () -> updated.equals(new Customer("C","D")));

 CS2030STest.finish();
 }
}
