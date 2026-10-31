public class Test1 {
 public static void main(String[] args) throws Throwable {
UserAccount original=new UserAccount("A","B");
UserAccount changed=original.setName("C");
CS2030STest.check("original unchanged", "A (B)", () -> original.toString());
CS2030STest.check("new name", "C (B)", () -> changed.toString());
UserAccount updated=changed.setEmail("D");
CS2030STest.check("previous unchanged", "C (B)", () -> changed.toString());
CS2030STest.check("new second value", "C (D)", () -> updated.toString());
CS2030STest.check("value equality", true, () -> updated.equals(new UserAccount("C","D")));

 CS2030STest.finish();
 }
}
