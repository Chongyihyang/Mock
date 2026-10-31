import java.util.*;
import java.util.function.*;
import java.util.stream.*;
public class Test2 {
 public static void main(String[] args) throws Throwable {
List<Apple> apples=List.of(new Apple(1),new Apple(9));
FruitStall<Apple> a=new FruitStall<>(apples);
FruitStall<Fruit> b=new FruitStall<>(List.of(new Apple(2)));
FruitStall<Fruit> c=new FruitStall<>(List.of(new Apple(8),new Mango()));
FruitStall<Mango> m=new FruitStall<>(List.of(new Mango()));
FruitStall<Devil> devil=new FruitStall<>(List.of(new Devil(),new Devil()));
CS2030STest.check("empty format", "", () -> new FruitStall<Fruit>().toString());
CS2030STest.check("stall format", "\n- Apple (expires in 1 days)\n- Apple (expires in 9 days)\n", () -> a.toString());
List<Fruit> widened=new FruitStall<Fruit>(apples).getFruits();
CS2030STest.check("constructor widening", 2, () -> widened.size());
CS2030STest.check("find", List.of("Mango (expires in 4 days)"), () -> c.findFruitsByName("Mango").stream().map(Object::toString).collect(Collectors.toList()));
CS2030STest.check("missing", 0, () -> a.findFruitsByName("Banana").size());
FruitStall<Fruit> merged=Q4.mergeStalls(a,b);
CS2030STest.check("merge order", List.of(1,9,2), () -> merged.getFruits().stream().map(Fruit::getDaysToExpiry).collect(Collectors.toList()));
CS2030STest.check("merge preserves input", 2, () -> a.getFruits().size());
CS2030STest.check("unique names", List.of("Apple","Devil","Mango"), () -> Q5.findUniqueFruitTypes(List.of(a,b,c,m,devil)));

 CS2030STest.finish();
 }
}
