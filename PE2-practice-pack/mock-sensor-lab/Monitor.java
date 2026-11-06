import java.util.ArrayList;
import java.util.List;

/**
 * An ordered collection of sensor series. Question 2.
 *
 * @author
 */
public class Monitor {
  private List<Series> series;

  public Monitor(List<Series> series) {
    this.series = List.copyOf(series);
  }

  public List<Series> getSeries() {
    return this.series;
  }

  public List<String> exceeding(int threshold) {
    return this.series
      .stream()
      .filter(x -> 
          x
          .getValues()
          .stream()
          .filter(y -> y > threshold)
          .count() != 0)
      .map(x -> 
          x.getName())
      .toList();
  }

  public Monitor truncate(int limit) {
    return new Monitor(
        this.series
        .stream()
        .map(s -> 
          new Series(
            s.getName(),
            s.getValues().stream().limit(limit).toList()))
        .toList());
  }

  public List<Integer> flatten() {
    return this.series
      .stream()
      .flatMap(x -> x.getValues().stream())
      .toList();
  }

  @Override
  public String toString() {
    return this.series.stream().map(s -> "[" + s + "]").reduce("", (a, b) -> a + b);
  }
}
