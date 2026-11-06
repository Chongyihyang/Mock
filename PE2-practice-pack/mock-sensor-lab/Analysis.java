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
    return Maybe
      .some("")
      .filter(x -> series.getValues().size() > 1)
      .map(x -> 
          IntStream
          .iterate(0, i -> i + 1)
          .limit(series.getValues().size() - 1)
          .map(y -> series.getValues().get(y) - series.getValues().get(y+1))
          .boxed()
          .takeWhile(z -> z <= 0)
          .toList()
          .size() + 1)
      .filter(x -> x != series.getValues().size());
  }

  public static List<Integer> movingTotals(Series series, int width) {
    return Maybe
      .some(series.getValues())
      .filter(x -> x.size() > 0 && x.size() >= width)
      .map(d -> 
      IntStream
      .iterate(0, x -> x + 1)
      .limit(series.getValues().size() - width + 1)
      .map(x -> series
          .getValues()
          .stream()
          .skip(x)
          .limit(width)
          .reduce(0, (z, y) -> z + y))
      .boxed()
      .toList())
      .orElse(List.of());
  }

  public static Maybe<Series> combine(Series first, Series second,
      Combiner<? super Integer, ? super Integer, ? extends Integer> operation) {
    return Maybe
      .some(new Series("", List.of()))
      .filter(x -> first.getValues().size() == second.getValues().size())
      .map(x -> new Series(
            first.getName() + "+" + second.getName(),
            IntStream
            .iterate(0, y -> y + 1)
            .limit(first.getValues().size())
            .map(a -> operation.combine(
                first.getValues().get(a), 
                second.getValues().get(a)))
            .boxed()
            .toList()));
  }
}
