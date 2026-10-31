public class Test2 {
 public static void main(String[] args) throws Throwable {
java.util.List<Member> people=new java.util.ArrayList<>();people.add(new Member("A","B"));
Team a=new Team("a",10,people);
Team b=new Team("b",3,java.util.List.of(new Member("C","D")));
Team added=a.score(5);
CS2030STest.check("original amount", 10, () -> a.getPoints());
CS2030STest.check("new amount", 15, () -> added.getPoints());
Pair<Team,Team> pair=a.transferTo(b,2);
CS2030STest.check("transfer source unchanged", 10, () -> a.getPoints());
CS2030STest.check("transfer target unchanged", 3, () -> b.getPoints());
CS2030STest.check("transfer first", 8, () -> pair.first().getPoints());
CS2030STest.check("transfer second", 5, () -> pair.second().getPoints());
Team closed=a.disqualify();
CS2030STest.check("original state", false, () -> a.isDisqualified());
CS2030STest.check("new state", true, () -> closed.isDisqualified());
Team merged=a.mergeWith(b);
CS2030STest.check("combined amount", 13, () -> merged.getPoints());
CS2030STest.check("combined people", 2, () -> merged.getMembers().size());
people.clear();
CS2030STest.check("constructor defensive copy", 1, () -> a.getMembers().size());
CS2030STest.check("getter cannot mutate internal list", 1, ()->{try {a.getMembers().clear();} catch(UnsupportedOperationException e) { } return a.getMembers().size();});
 CS2030STest.finish();
 }
}
