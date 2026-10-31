import cs2030s.fp.*;
public class Test3 {
 public static void main(String[] args) throws Throwable {
CS2030STest.check("flatMap success", 5, () -> Try.success(4).flatMap(x->Try.success(x+1)).get());
Transformer<Object, Try<Integer>> hash=x->Try.success(x.hashCode());
CS2030STest.check("flatMap PECS", 99162322, ()->{Try<Number> t=Try.success("hello").flatMap(hash); return t.get();});
Try<Integer> failed=Try.failure(new Error());
CS2030STest.check("flatMap failure skip", true, () -> failed.flatMap(x->{throw new AssertionError();}) == (Object) failed);
CS2030STest.throwsType("flatMap returns failure", java.io.IOException.class, ()->Try.success(4).flatMap(x->Try.failure(new java.io.IOException())).get());
 CS2030STest.finish();
 }
}
