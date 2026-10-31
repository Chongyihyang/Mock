/** Mutable starter: refactor for Task 1. */
public class Member {
 private String email; private String phoneNumber;
 public Member(String email,String phoneNumber) { this.email=email; this.phoneNumber=phoneNumber; }
 public String getEmail() { return email; }
 public String getPhoneNumber() { return phoneNumber; }
 public Member updateEmail(String value) { this.email=value; return this; }
 public Member updatePhoneNumber(String value) { this.phoneNumber=value; return this; }
 public String toString() { return email+" ("+phoneNumber+")"; }
 public boolean equals(Object o) { return o instanceof Member && email.equals(((Member)o).email) && phoneNumber.equals(((Member)o).phoneNumber); }
 public int hashCode() { return java.util.Objects.hash(email,phoneNumber); }
}
