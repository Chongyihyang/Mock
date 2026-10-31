/** Mutable starter: refactor for Task 1. */
public class Customer {
 private String name; private String address;
 public Customer(String name,String address) { this.name=name; this.address=address; }
 public String getName() { return name; }
 public String getAddress() { return address; }
 public Customer changeName(String value) { this.name=value; return this; }
 public Customer changeAddress(String value) { this.address=value; return this; }
 public String toString() { return name+" ("+address+")"; }
 public boolean equals(Object o) { return o instanceof Customer && name.equals(((Customer)o).name) && address.equals(((Customer)o).address); }
 public int hashCode() { return java.util.Objects.hash(name,address); }
}
