import cs2030s.fp.*;
public class Test2 {
 public static void main(String[] args) throws Throwable {
CS2030STest.check("map chain", 3, () -> Try.success(4).map(x->8/x).map(x->x+1).get());
CS2030STest.check("map PECS", 99162322, () -> Try.success("hello").map((Object x)->x.hashCode()).get());
CS2030STest.throwsType("map captures arithmetic", ArithmeticException.class, ()->Try.success(0).map(x->8/x).get());
Try<Integer> failed=Try.failure(new Error());
CS2030STest.check("failure identity and skip", true, () -> failed.map(x->{throw new AssertionError();}) == (Object) failed);
CS2030STest.check("original unaffected", 4, () -> Try.success(4).get());

 CS2030STest.finish();
 }
}
