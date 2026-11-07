import cs2030s.fp.Lazy;
import cs2030s.fp.Maybe;
import java.util.List;
public class Test3 {
  public static void main(String[] args) {
    Checks.test("rise uses adjacent pair and later index", () -> {
      Checks.equal(Maybe.some(2), Inspection.firstRise(new Batch("A", List.of(2, 3, 7, 12)), 4));
      Checks.equal(Maybe.none(), Inspection.firstRise(new Batch("A", List.of(0, 2, 4)), 3));
    });
    Checks.test("rise boundary and negatives", () -> {
      Checks.equal(Maybe.some(1), Inspection.firstRise(new Batch("A", List.of(-5, -2)), 3));
      Checks.equal(Maybe.none(), Inspection.firstRise(new Batch("A", List.of(5, 5, 1)), 1));
    });
    Checks.test("rise empty and singleton", () -> {
      Checks.equal(Maybe.none(), Inspection.firstRise(new Batch("A", List.of()), 1));
      Checks.equal(Maybe.none(), Inspection.firstRise(new Batch("A", List.of(8)), 1));
    });
    Checks.test("overlapping windows sample", () ->
        Checks.equal(List.of(5, 10, 11), Inspection.windowTotals(new Batch("A", List.of(2, 3, 7, 4)), 2)));
    Checks.test("window boundaries", () -> {
      Batch b = new Batch("A", List.of(-2, 0, 3));
      Checks.equal(List.of(-2, 0, 3), Inspection.windowTotals(b, 1));
      Checks.equal(List.of(1), Inspection.windowTotals(b, 3));
      Checks.equal(List.of(), Inspection.windowTotals(b, 4));
      Checks.equal(List.of(), Inspection.windowTotals(new Batch("E", List.of()), 1));
    });
    Checks.test("deferred creation and display do no work", () -> {
      int[] calls = {0}; int[] fallback = {0};
      Lazy<String> report = Inspection.deferredReport(new Calibration(x -> { calls[0]++; return x; }),
          new Batch("A", List.of(1, 2)), 2, () -> { fallback[0]++; return "bad"; });
      report.toString(); Checks.equal(0, calls[0]); Checks.equal(0, fallback[0]);
    });
    Checks.test("successful report and caching", () -> {
      int[] calls = {0};
      Lazy<String> r = Inspection.deferredReport(new Calibration(x -> { calls[0]++; return x + 1; }),
          new Batch("A", List.of(2, 3, 7, 4)), 2, () -> { throw new AssertionError(); });
      Checks.equal("A: [7, 12, 13]", r.get()); Checks.equal("A: [7, 12, 13]", r.get());
      Checks.equal(4, calls[0]);
    });
    Checks.test("failed report fallback once", () -> {
      int[] calls = {0};
      Lazy<String> r = Inspection.deferredReport(new Calibration(x -> x),
          new Batch("A", List.of(1, -1)), 1, () -> { calls[0]++; return "bad"; });
      Checks.equal("bad", r.get()); Checks.equal("bad", r.get()); Checks.equal(1, calls[0]);
    });
    Checks.test("empty and too wide are successful", () -> {
      Calibration c = new Calibration(x -> x);
      Checks.equal("E: []", Inspection.deferredReport(c, new Batch("E", List.of()), 2,
          () -> { throw new AssertionError(); }).get());
      Checks.equal("A: []", Inspection.deferredReport(c, new Batch("A", List.of(1)), 2,
          () -> { throw new AssertionError(); }).get());
    });
    Checks.test("validate readings even when no complete window", () ->
        Checks.equal("bad", Inspection.deferredReport(new Calibration(x -> x),
            new Batch("A", List.of(-1)), 5, () -> "bad").get()));
    Checks.test("empty fallback cached independently", () -> {
      int[] n = {0}; Calibration c = new Calibration(x -> -1); Batch b = new Batch("A", List.of(1));
      Lazy<String> a = Inspection.deferredReport(c, b, 1, () -> { n[0]++; return ""; });
      Lazy<String> d = Inspection.deferredReport(c, b, 1, () -> { n[0]++; return ""; });
      Checks.equal("", a.get()); a.get(); Checks.equal("", d.get()); Checks.equal(2, n[0]);
    });
    Checks.finish();
  }
}
