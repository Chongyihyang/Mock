# Local reconstruction contract
The PDF omits the original source API. This baseline is an adaptation, not official files.
- `UserAccount(String name,String email)` and `Product(String id,String name,double price)` supply the API in the starter files.
- Task 1: mutation methods return new objects; swapPrice returns receiver first, other second. Bundle retains receiver ID, name becomes `receiverName + " + " + otherName`, price is 90% of summed prices.
- ShoppingCart is mutable; constructor accepts `List<Pair<Product,Integer>>` with unique IDs and nonnegative quantities. Updates must store replacement immutable products.
- Contents sorted by decreasing price, ties by ID alphabetically; each row `id: name @ price x quantity\n`. Empty result is empty string. Quantities of zero remain visible.
- Missing updates/removals do nothing. Bundling creates a product without changing either input or cart; IDs are distinct.
- Task 2: change both `findProduct` AND `bundleProduct` to return `Maybe<Product>` (the latter is a local adaptation to remove null for missing IDs). No loops, conditional statements, ternaries, explicit null, block lambdas, or new methods in ShoppingCart, except equals. Constructor setup is exempt.
- Tests 1/2 intentionally fail on mutable starter; Test3 passes initially. Test4 targets the final Maybe API and only compiles AFTER Task 2. Run individual tests while working.
- Provided Pair exposes first()/second(). Maybe is provided reconstructed infrastructure.
