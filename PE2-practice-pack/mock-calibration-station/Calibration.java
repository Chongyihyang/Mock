import cs2030s.fp.Maybe;
import cs2030s.fp.Transformer;
import java.util.List;
import java.util.stream.Stream;

/** @author YOUR_STUDENT_NUMBER */
public final class Calibration {
  private final Transformer<? super Integer, ? extends Integer> correction;

  public Calibration(Transformer<? super Integer, ? extends Integer> correction) {
    this.correction = correction;
  }

  public Maybe<Integer> apply(int reading) {
    return Maybe
      .some(this.correction.transform(reading))
      .filter(x -> x >= 0);
  }

  public Maybe<Batch> applyAll(Batch batch) {
    return Maybe
      .some(batch
          .getReadings()
          .stream()
          .<Integer>map(x -> this.correction.transform(x)))
      .map(a -> new Batch(batch.getName(), a.toList()))
      .filter(b -> !b
          .getReadings()
          .stream()
          .anyMatch(x -> x < 0));
  }
}
