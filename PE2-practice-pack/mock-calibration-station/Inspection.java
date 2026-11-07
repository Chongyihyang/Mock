import cs2030s.fp.Lazy;
import cs2030s.fp.Maybe;
import cs2030s.fp.Producer;
import java.util.List;
import java.util.stream.IntStream;

/** @author YOUR_STUDENT_NUMBER */
public final class Inspection {
  public static Maybe<Integer> firstRise(Batch batch, int gap) {
    return Maybe
      .some(0)
      .filter(_ -> batch.getReadings().size() > 1)
      .map(_ -> IntStream
      .range(1, batch.getReadings().size())
      .filter(x -> 
        batch.getReadings().get(x) - batch.getReadings().get(x - 1) >= gap)
      .limit(1)
      .sum())
      .filter(x -> x > 0);
  }

  public static List<Integer> windowTotals(Batch batch, int width) {
    return Maybe
      .some(0)
      .filter(_ -> batch.getReadings().size() >= width)
      .map(_ -> IntStream
          .range(0, batch.getReadings().size() - width + 1)
          .map(x -> batch
            .getReadings()
            .stream()
            .skip(x)
            .limit(width)
            .reduce(0, (e, r) -> e + r))
          .boxed()
          .toList())
      .orElse(List.of());
  }

  public static Lazy<String> deferredReport(Calibration calibration,
      Batch batch, int width, Producer<? extends String> fallback) {
    return Lazy
      .of(() -> calibration
          .applyAll(batch)
          .map(x -> batch.getName() + ": " + windowTotals(x, width))
          .orElseGet(fallback));
  }
}
