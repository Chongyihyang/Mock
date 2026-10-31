/** Mutable starter for Task 1. */
public class Product {
 private String productId; private String name; private double price;
 public Product(String id,String name,double price) { this.productId=id;this.name=name;this.price=price; }
 public String getProductId() { return productId; }
 public String getName() { return name; }
 public double getPrice() { return price; }
 public Product setPrice(double price) { this.price=price;return this; }
 public Product setName(String name) { this.name=name;return this; }
 public Product setProductId(String id) { this.productId=id;return this; }
 public Pair<Product,Product> swapPrice(Product other) { double old=price;price=other.price;other.price=old;return new Pair<>(this,other); }
 public Product bundle(Product other) { price=(price+other.price)*0.9;name=name+" + "+other.name;return this; }
 public String toString() { return productId+": "+name+" @ "+price; }
 public boolean equals(Object o) { return o instanceof Product && productId.equals(((Product)o).productId) && name.equals(((Product)o).name) && price==((Product)o).price; }
 public int hashCode() { return java.util.Objects.hash(productId,name,price); }
}
