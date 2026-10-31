import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import cs2030s.fp.Maybe;
/** Imperative starter: preserve behavior while refactoring for Task 2. */
public class Competition {
 private final Map<String,Team> entries;
 public Competition(List<Team> initial) { entries=new TreeMap<>();initial.forEach(x->entries.put(x.getId(),x)); }
 public Team findTeam(String id) { return entries.get(id); }
 public void score(String id,int amount) { Team a=findTeam(id);if(a!=null) { a.score(amount); } }
 public void penalize(String id,int amount) { Team a=findTeam(id);if(a!=null) { a.penalize(amount); } }
 public void disqualify(String id) { Team a=findTeam(id);if(a!=null) { a.disqualify(); } }
 public int totalPoints() { int sum=0;for(Team a:entries.values()) { if(!a.isDisqualified()) { sum+=a.getPoints(); } }return sum; }
 public String leaderBoard() {
  List<Team> all=new ArrayList<>(entries.values());
  all.sort(Comparator.comparingInt(Team::getPoints).reversed());
  String result="";for(Team a:all) { if(!a.isDisqualified()) {result+=a+"\n";} }return result;
 }
 public void transfer(String from,String to,int amount) {
  Team a=findTeam(from);Team b=findTeam(to);
  if(a==null || b==null) {return;} if(a.getPoints()<=0 || b.getPoints()>=0) {return;} a.transferTo(b,amount);
 }
 public void mergeTeams(String from,String to) {
  Team a=findTeam(from);Team b=findTeam(to);
  if(a==null || b==null) {return;} if(a.isDisqualified() || b.isDisqualified()) {return;} b.mergeWith(a);entries.remove(from);
 }
 public boolean equals(Object o) { return o instanceof Competition && entries.equals(((Competition)o).entries); }
 public int hashCode() { return entries.hashCode(); }
}
