import java.util.List;
import java.util.ArrayList;
/** Mutable starter: refactor for Task 1. */
public class Team {
 private String id; private int points; private List<Member> members; private boolean disqualified;
 public Team(String id,int amount,List<Member> people) { this.id=id;this.points=amount;this.members=people;this.disqualified=false; }
 public String getId() { return id; }
 public int getPoints() { return points; }
 public List<Member> getMembers() { return members; }
 public boolean isDisqualified() { return disqualified; }
 public Team score(int amount) { this.points+=amount;return this; }
 public Team penalize(int amount) { this.points-=amount;return this; }
 public Team disqualify() { this.disqualified=true;return this; }
 public Pair<Team,Team> transferTo(Team other,int amount) { this.penalize(amount);other.score(amount);return new Pair<>(this,other); }
 public Team mergeWith(Team other) {
  this.points+=other.points;
  List<Member> all=new ArrayList<>(this.members);all.addAll(other.members);this.members=all;return this;
 }
 public String toString() { return id+": "+points+" ["+disqualified+"] "+members; }
 public boolean equals(Object o) { return o instanceof Team && id.equals(((Team)o).id) && points==((Team)o).points && disqualified==((Team)o).disqualified && members.equals(((Team)o).members); }
 public int hashCode() { return java.util.Objects.hash(id,points,disqualified,members); }
}
