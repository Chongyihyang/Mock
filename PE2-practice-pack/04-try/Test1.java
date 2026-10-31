import cs2030s.fp.*;
public class Test1 {
 public static void main(String[] args) throws Throwable {
CS2030STest.check("success", 1, () -> Try.success(1).get());
CS2030STest.check("null success", null, () -> Try.success(null).get());
CS2030STest.check("of PECS", 2, () -> Try.<Number>of((Producer<Integer>)()->2).get());
CS2030STest.check("value equality", true, () -> Try.success(3).equals(Try.success(3)));
CS2030STest.check("null equality", true, () -> Try.success(null).equals(Try.success(null)));
CS2030STest.check("different types", false, () -> Try.success(3).equals(Try.success("3")));
CS2030STest.check("same failure text", true, () -> Try.failure(new Error("x")).equals(Try.failure(new Error("x"))));
CS2030STest.check("different failure text", false, () -> Try.failure(new Error("x")).equals(Try.failure(new Error("y"))));
CS2030STest.check("different states", false, () -> Try.failure(new Error()).equals(Try.success(new Error())));
Error err=new Error("original");
CS2030STest.check("original throwable", true, ()->{try { Try.failure(err).get(); return false; } catch(Throwable t) { return t==err; }});
CS2030STest.throwsType("of captures checked exception", java.io.IOException.class, ()->Try.of(()->{throw new java.io.IOException();}).get());
 CS2030STest.finish();
 }
}
