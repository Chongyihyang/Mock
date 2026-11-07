import java.util.List;
import java.util.ArrayList;
public class Test1 {
  public static void main(String[] args) {
    Checks.test("constructor snapshot", () -> {
      List<Integer> input = new ArrayList<>(List.of(2, -1, 2));
      Batch b = new Batch("A", input); input.set(0, 99);
      Checks.equal("A: [2, -1, 2]", b.toString());
    });
    Checks.test("getter protects stored values", () -> {
      Batch b = new Batch("A", List.of(2, 3));
      try { b.getReadings().set(0, 99); } catch (UnsupportedOperationException e) { }
      Checks.equal(List.of(2, 3), b.getReadings());
    });
    Checks.test("class and fields", () -> {
      Checks.yes(java.lang.reflect.Modifier.isFinal(Batch.class.getModifiers()));
      for (java.lang.reflect.Field f : Batch.class.getDeclaredFields()) {
        Checks.yes(java.lang.reflect.Modifier.isPrivate(f.getModifiers()));
        Checks.yes(java.lang.reflect.Modifier.isFinal(f.getModifiers()));
      }
    });
    Checks.test("append order and independence", () -> {
      Batch b = new Batch("A", List.of(2, -1, 2));
      Checks.equal("A: [2, -1, 2, 0]", b.append(0).toString());
      Checks.equal("A: [2, -1, 2]", b.toString());
    });
    Checks.test("shift negative offset", () -> {
      Batch b = new Batch("A", List.of(3, 0, -2));
      Checks.equal("A: [1, -2, -4]", b.shift(-2).toString());
      Checks.equal(List.of(3, 0, -2), b.getReadings());
    });
    Checks.test("zero shift is new object", () -> {
      Batch b = new Batch("A", List.of(1)); Checks.yes(b.shift(0) != b);
    });
    Checks.test("empty operations", () -> {
      Batch b = new Batch("", List.of());
      Checks.equal(": [7]", b.append(7).toString());
      Checks.equal(": []", b.shift(3).toString()); Checks.yes(b.shift(3) != b);
    });
    Checks.test("chaining", () -> Checks.equal("B: [3, 4]",
        new Batch("B", List.of(1)).append(2).shift(2).toString()));
    Checks.finish();
  }
}
