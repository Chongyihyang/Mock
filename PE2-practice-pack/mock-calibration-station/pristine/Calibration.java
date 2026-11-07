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
    throw new UnsupportedOperationException("Q2 apply");
  }

  public Maybe<Batch> applyAll(Batch batch) {
    throw new UnsupportedOperationException("Q2 applyAll");
  }
}
