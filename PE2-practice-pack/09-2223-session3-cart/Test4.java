public class Test4 {
 public static void main(String[] args) throws Throwable {
ShoppingCart cart=new ShoppingCart(java.util.List.of(new Pair<>(new Product("a","Pen",10),2),new Pair<>(new Product("b","Book",20),1)));
CS2030STest.check("lookup Maybe", "Pen", () -> cart.findProduct("a").map(Product::getName).orElse("missing"));
CS2030STest.check("missing Maybe", "missing", () -> cart.findProduct("no").map(Product::getName).orElse("missing"));
CS2030STest.check("bundle discount", 27.0, () -> cart.bundleProduct("a","b").map(Product::getPrice).orElse(-1.0));
CS2030STest.check("bundle name", "Pen + Book", () -> cart.bundleProduct("a","b").map(Product::getName).orElse("missing"));
CS2030STest.check("missing bundle", "missing", () -> cart.bundleProduct("a","no").map(Product::getName).orElse("missing"));
CS2030STest.check("bundle leaves cart", 40.0, () -> cart.getTotal());

 CS2030STest.finish();
 }
}
