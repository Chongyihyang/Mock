public class Test4 {
 public static void main(String[] args) throws Throwable {
Competition m=new Competition(java.util.List.of(new Team("a",10,java.util.List.of(new Member("A","B")))));
CS2030STest.check("Maybe lookup", true, () -> m.findTeam("a").map(x->x.getPoints()==10).orElse(false));
CS2030STest.check("Maybe absent", false, () -> m.findTeam("no").map(x->true).orElse(false));

 CS2030STest.finish();
 }
}
