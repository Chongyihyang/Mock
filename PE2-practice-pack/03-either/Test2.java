import cs2030s.fp.*;
import java.util.List;
public class Test2 {
 public static void main(String[] args) throws Throwable {
Transformer<Object, Integer> hash = Object::hashCode;
Transformer<Object, Either<String, Integer>> choose = x -> x.equals(8) ? Either.left(x.toString()) : Either.right(x.hashCode());
CS2030STest.check("map left", "Left[4]", () -> Either.<Integer,String>left(2).map(x->x+2,s->s+" + 2").toString());
CS2030STest.check("map right", "Right[2 + 2]", () -> Either.<Integer,String>right("2").map(x->x+2,s->s+" + 2").toString());
CS2030STest.check("map PECS", "Left[2]", () -> { Either<Number,Number> e=Either.left(2).map(hash,hash); return e.toString(); });
CS2030STest.check("flatMap PECS", "Right[2]", () -> { Either<Object,Number> e=Either.left(2).flatMap(choose,choose); return e.toString(); });
CS2030STest.check("flatMap switches side", "Left[8]", () -> Either.right(8).flatMap(choose,choose).toString());
CS2030STest.check("fold", 3, () -> Either.<List<Integer>,String>left(List.of(1,2,3)).fold(List::size,String::length));
CS2030STest.check("fold PECS", Integer.valueOf(2), () -> Either.left(2).<Number>fold(hash,hash));
CS2030STest.check("filter keep", "Right[8]", () -> Either.<Throwable,Integer>right(8).filterOrElse((Number x)->x.intValue()>0,(Object x)->new IllegalStateException(x+" is illegal")).toString());
CS2030STest.check("filter fail", "Left[is false]", () -> Either.<String,Boolean>right(false).filterOrElse(x->x,x->"is false").toString());
CS2030STest.check("skip left callbacks", "Left[keep]", () -> Either.<String,Boolean>left("keep").filterOrElse(x->{throw new AssertionError();},x->{throw new AssertionError();}).toString());
CS2030STest.check("skip unselected map", "Left[3]", () -> Either.<Integer,String>left(2).map(x->x+1,s->{throw new AssertionError();}).toString());

 CS2030STest.finish();
 }
}
