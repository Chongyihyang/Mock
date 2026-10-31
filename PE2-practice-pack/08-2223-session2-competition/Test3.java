public class Test3 {
 public static void main(String[] args) throws Throwable {
Competition m=new Competition(java.util.List.of(new Team("a",10,java.util.List.of(new Member("A","B"))),new Team("b",-3,java.util.List.of(new Member("C","D")))));
CS2030STest.check("initial total", 7, () -> m.totalPoints());
m.score("a",2);m.penalize("b",1);
CS2030STest.check("updated total", 8, () -> m.totalPoints());
m.score("missing",99);m.transfer("a","missing",1);
CS2030STest.check("missing no op", 8, () -> m.totalPoints());
m.transfer("a","b",2);
CS2030STest.check("transfer conserves total", 8, () -> m.totalPoints());
CS2030STest.check("transfer values", true, () -> m.leaderBoard().contains("a: 10") && m.leaderBoard().contains("b: -2"));
m.transfer("b","a",2);
CS2030STest.check("invalid transfer no op", true, () -> m.leaderBoard().startsWith("a: 10"));
m.mergeTeams("a","b");
CS2030STest.check("merge total", 8, () -> m.totalPoints());
CS2030STest.check("source removed", false, () -> m.leaderBoard().contains("a:"));
m.disqualify("b");
CS2030STest.check("disqualified excluded", 0, () -> m.totalPoints());
CS2030STest.check("empty leaderboard", "", () -> m.leaderBoard());

 CS2030STest.finish();
 }
}
