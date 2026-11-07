import cs2030s.fp.Maybe;
import cs2030s.fp.Transformer;
import java.util.List;
import java.util.ArrayList;
public class Test2 {
  public static void main(String[] args) {
    Checks.test("apply validates corrected value", () -> {
      Calibration c = new Calibration(x -> x + 2);
      Checks.equal(Maybe.some(1), c.apply(-1));
      Checks.equal(Maybe.some(0), c.apply(-2));
      Checks.equal(Maybe.none(), c.apply(-3));
    });
    Checks.test("apply calls correction exactly once", () -> {
      int[] count = {0}; Calibration c = new Calibration(x -> { count[0]++; return x; });
      c.apply(-1); Checks.equal(1, count[0]);
    });
    Checks.test("PECS callback", () -> {
      Transformer<Number, Integer> f = x -> x.intValue() + 1;
      Checks.equal(Maybe.some(5), new Calibration(f).apply(4));
    });
    Checks.test("complete batch and input unchanged", () -> {
      Batch b = new Batch("A", List.of(1, 0, 1));
      Checks.equal("[A: [3, 2, 3]]", new Calibration(x -> x + 2).applyAll(b).toString());
      Checks.equal("A: [1, 0, 1]", b.toString());
    });
    Checks.test("failure at each position", () -> {
      Calibration c = new Calibration(x -> x);
      Checks.equal(Maybe.none(), c.applyAll(new Batch("A", List.of(-1, 2, 3))));
      Checks.equal(Maybe.none(), c.applyAll(new Batch("A", List.of(1, -2, 3))));
      Checks.equal(Maybe.none(), c.applyAll(new Batch("A", List.of(1, 2, -3))));
    });
    Checks.test("empty batch is successful without callback", () -> {
      Checks.equal("[E: []]", new Calibration(x -> { throw new AssertionError(); })
          .applyAll(new Batch("E", List.of())).toString());
    });
    Checks.test("successful callbacks once each in order", () -> {
      List<Integer> seen = new ArrayList<>();
      Calibration c = new Calibration(x -> { seen.add(x); return x + 1; });
      c.applyAll(new Batch("A", List.of(3, 1, 3)));
      Checks.equal(List.of(3, 1, 3), seen);
    });
    Checks.test("successful result is new including empty", () -> {
      Calibration c = new Calibration(x -> x);
      Batch b = new Batch("A", List.of(1)); Batch e = new Batch("E", List.of());
      Checks.yes(c.applyAll(b).map(x -> x != b).orElse(false));
      Checks.yes(c.applyAll(e).map(x -> x != e).orElse(false));
    });
    Checks.test("repeated calls", () -> {
      Calibration c = new Calibration(x -> x + 1); Batch b = new Batch("A", List.of(2));
      Checks.equal(c.applyAll(b).toString(), c.applyAll(b).toString());
    });
    Checks.finish();
  }
}
