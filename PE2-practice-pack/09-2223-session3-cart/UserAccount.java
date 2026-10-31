/** Mutable starter: refactor for Task 1. */
public class UserAccount {
 private String name; private String email;
 public UserAccount(String name,String email) { this.name=name; this.email=email; }
 public String getName() { return name; }
 public String getEmail() { return email; }
 public UserAccount setName(String value) { this.name=value; return this; }
 public UserAccount setEmail(String value) { this.email=value; return this; }
 public String toString() { return name+" ("+email+")"; }
 public boolean equals(Object o) { return o instanceof UserAccount && name.equals(((UserAccount)o).name) && email.equals(((UserAccount)o).email); }
 public int hashCode() { return java.util.Objects.hash(name,email); }
}
