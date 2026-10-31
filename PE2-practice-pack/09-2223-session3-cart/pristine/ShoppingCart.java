import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import cs2030s.fp.Maybe;
/** Imperative starter for Task 2. */
public class ShoppingCart {
 private final Map<String,Pair<Product,Integer>> entries;
 public ShoppingCart(List<Pair<Product,Integer>> initial) { entries=new TreeMap<>();initial.forEach(x->entries.put(x.first().getProductId(),x)); }
 public Product findProduct(String id) { Pair<Product,Integer> pair=entries.get(id);if(pair==null) {return null;}return pair.first(); }
 public void updateProductPrice(String id,double price) { Product p=findProduct(id);if(p!=null) {p.setPrice(price);} }
 public void updateProductQuantity(String id,int quantity) { Pair<Product,Integer> pair=entries.get(id);if(pair!=null) {entries.put(id,new Pair<>(pair.first(),quantity));} }
 public void removeProduct(String id) { entries.remove(id); }
 public double getTotal() { double total=0;for(Pair<Product,Integer> pair:entries.values()) {total+=pair.first().getPrice()*pair.second();}return total; }
 public Product bundleProduct(String first,String second) { Product a=findProduct(first);Product b=findProduct(second);if(a==null || b==null) {return null;}return new Product(a.getProductId(),a.getName(),a.getPrice()).bundle(b); }
 public String cartContents() {
  List<Pair<Product,Integer>> all=new ArrayList<>(entries.values());
  all.sort(Comparator.comparingDouble((Pair<Product,Integer> p)->p.first().getPrice()).reversed());
  String result="";for(Pair<Product,Integer> p:all) {result+=p.first()+" x "+p.second()+"\n";}return result;
 }
 public boolean equals(Object o) { if(!(o instanceof ShoppingCart)) {return false;}ShoppingCart c=(ShoppingCart)o;return cartContents().equals(c.cartContents()); }
 public int hashCode() { return cartContents().hashCode(); }
}
