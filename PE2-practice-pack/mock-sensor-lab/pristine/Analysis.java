import cs2030s.fp.Combiner;
import cs2030s.fp.Maybe;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Operations on ordered readings. Question 3.
 *
 * @author
 */
public class Analysis {
  public static Maybe<Integer> firstDrop(Series series) {
    return Maybe.none();
  }

  public static List<Integer> movingTotals(Series series, int width) {
    return List.of();
  }

  public static Maybe<Series> combine(Series first, Series second,
      Combiner<? super Integer, ? super Integer, ? extends Integer> operation) {
    return Maybe.none();
  }
}
