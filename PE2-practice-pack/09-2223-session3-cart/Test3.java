public class Test3 {
 public static void main(String[] args) throws Throwable {
ShoppingCart cart=new ShoppingCart(java.util.List.of(new Pair<>(new Product("a","Pen",10),2),new Pair<>(new Product("b","Book",20),1)));
CS2030STest.check("initial total", 40.0, () -> cart.getTotal());
CS2030STest.check("listing order", "b: Book @ 20.0 x 1\na: Pen @ 10.0 x 2\n", () -> cart.cartContents());
cart.updateProductPrice("a",5);cart.updateProductQuantity("b",3);
CS2030STest.check("update total", 70.0, () -> cart.getTotal());
cart.updateProductPrice("missing",9);cart.updateProductQuantity("missing",99);
CS2030STest.check("missing no op", 70.0, () -> cart.getTotal());
cart.removeProduct("b");
CS2030STest.check("remove", 10.0, () -> cart.getTotal());
cart.removeProduct("a");
CS2030STest.check("empty total", 0.0, () -> cart.getTotal());
CS2030STest.check("empty contents", "", () -> cart.cartContents());

 CS2030STest.finish();
 }
}
