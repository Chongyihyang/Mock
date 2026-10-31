import cs2030s.fp.*;
public class Test4 {
 public static void main(String[] args) throws Throwable {
Try<Integer> good=Try.success(4);
Try<Integer> bad=Try.failure(new Error("old"));
int[] calls={0};
CS2030STest.check("success skips handler", true, () -> good.onFailure(e->{throw new AssertionError();})==good);
CS2030STest.check("handler identity", true, () -> bad.onFailure(e->calls[0]++)==bad);
CS2030STest.check("handler count", 1, () -> calls[0]);
CS2030STest.check("recover success identity", true, () -> good.recover(e->{throw new AssertionError();})==good);
CS2030STest.check("recover failure", 10, () -> bad.recover(e->10).get());
CS2030STest.check("recover PECS", 8, () -> Try.<Number>failure(new Error()).recover((Object e)->Integer.valueOf(8)).get());
CS2030STest.throwsType("handler fails", ArithmeticException.class, ()->bad.onFailure(e->{throw new ArithmeticException();}).get());
CS2030STest.throwsType("recovery fails", IllegalStateException.class, ()->bad.recover(e->{throw new IllegalStateException();}).get());
 CS2030STest.finish();
 }
}
