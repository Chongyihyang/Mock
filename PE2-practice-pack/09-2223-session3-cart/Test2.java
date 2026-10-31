public class Test2 {
 public static void main(String[] args) throws Throwable {
Product a=new Product("a","Pen",10); Product b=new Product("b","Book",20);
Product changed=a.setPrice(5);
CS2030STest.check("original price", 10.0, () -> a.getPrice());
CS2030STest.check("changed price", 5.0, () -> changed.getPrice());
Pair<Product,Product> swap=a.swapPrice(b);
CS2030STest.check("source unchanged", 10.0, () -> a.getPrice());
CS2030STest.check("other unchanged", 20.0, () -> b.getPrice());
CS2030STest.check("swap first", 20.0, () -> swap.first().getPrice());
CS2030STest.check("swap second", 10.0, () -> swap.second().getPrice());
Product bundle=a.bundle(b);
CS2030STest.check("bundle discount", 27.0, () -> bundle.getPrice());
CS2030STest.check("bundle identity", "a", () -> bundle.getProductId());
CS2030STest.check("bundle name", "Pen + Book", () -> bundle.getName());
CS2030STest.check("bundle original unchanged", 10.0, () -> a.getPrice());
Product renamed=b.setName("Text"); Product reid=renamed.setProductId("c");
CS2030STest.check("name unchanged", "Book", () -> b.getName());
CS2030STest.check("id unchanged", "b", () -> renamed.getProductId());
CS2030STest.check("new id", "c", () -> reid.getProductId());

 CS2030STest.finish();
 }
}
