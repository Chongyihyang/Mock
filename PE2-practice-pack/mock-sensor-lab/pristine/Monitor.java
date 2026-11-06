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
    List<String> names = new ArrayList<>();
    for (Series s : this.series) {
      boolean exceeds = false;
      for (int value : s.getValues()) {
        if (value > threshold) {
          exceeds = true;
        }
      }
      if (exceeds) {
        names.add(s.getName());
      }
    }
    return names;
  }

  public Monitor truncate(int limit) {
    List<Series> shortened = new ArrayList<>();
    for (Series s : this.series) {
      List<Integer> values = new ArrayList<>();
      for (int i = 0; i < Math.min(limit, s.getValues().size()); i++) {
        values.add(s.getValues().get(i));
      }
      shortened.add(new Series(s.getName(), values));
    }
    return new Monitor(shortened);
  }

  public List<Integer> flatten() {
    List<Integer> values = new ArrayList<>();
    for (Series s : this.series) {
      for (int value : s.getValues()) {
        values.add(value);
      }
    }
    return values;
  }

  @Override
  public String toString() {
    return this.series.stream().map(s -> "[" + s + "]").reduce("", (a, b) -> a + b);
  }
}
