import java.util.*;
import java.util.function.*;
import java.util.stream.*;
public class Test3 {
 public static void main(String[] args) throws Throwable {
List<Apple> apples=List.of(new Apple(1),new Apple(9));
FruitStall<Apple> a=new FruitStall<>(apples);
FruitStall<Fruit> b=new FruitStall<>(List.of(new Apple(2)));
FruitStall<Fruit> c=new FruitStall<>(List.of(new Apple(8),new Mango()));
FruitStall<Mango> m=new FruitStall<>(List.of(new Mango()));
FruitStall<Devil> devil=new FruitStall<>(List.of(new Devil(),new Devil()));
List<FruitStall<Fruit>> consolidated=Q6.consolidateStallsbyType(List.of(c,m,devil));
CS2030STest.check("group names", List.of("Apple","Devil","Mango"), () -> consolidated.stream().map(s->s.getFruits().get(0).getName()).collect(Collectors.toList()));
CS2030STest.check("group sizes", List.of(1,2,2), () -> consolidated.stream().map(s->s.getFruits().size()).collect(Collectors.toList()));
CS2030STest.check("minimum tie", true, () -> new Q7<>().compare(c,m)==0);
CS2030STest.check("compare direction", true, () -> new Q7<>().compare(m,devil)<0);
CS2030STest.check("empty last", true, () -> new Q7<>().compare(new FruitStall<Fruit>(),m)>0);
CS2030STest.check("stable order", List.of(a,b,c,m,devil), () -> Q8.sortByShortestExpiry(List.of(devil,c,m,b,a)));
CS2030STest.check("empty grouping", List.of(), () -> Q6.consolidateStallsbyType(List.of()));

 CS2030STest.finish();
 }
}
