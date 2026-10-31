public class Test1 {
 public static void main(String[] args) throws Throwable {
Member original=new Member("A","B");
Member changed=original.updateEmail("C");
CS2030STest.check("original unchanged", "A (B)", () -> original.toString());
CS2030STest.check("new name", "C (B)", () -> changed.toString());
Member updated=changed.updatePhoneNumber("D");
CS2030STest.check("previous unchanged", "C (B)", () -> changed.toString());
CS2030STest.check("new second value", "C (D)", () -> updated.toString());
CS2030STest.check("value equality", true, () -> updated.equals(new Member("C","D")));

 CS2030STest.finish();
 }
}
