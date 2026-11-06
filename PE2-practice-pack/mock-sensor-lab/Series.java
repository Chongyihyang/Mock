import java.util.List;
import java.util.stream.Stream;

/**
 * One named series of sensor readings. Question 1.
 *
 * @author
 */
public final class Series {
  private final String name;
  private final List<Integer> values;

  public Series(String name, List<Integer> values) {
    this.name = name;
    this.values = values.stream().toList();
  }

  public String getName() {
    return this.name;
  }

  public List<Integer> getValues() {
    return this.values.stream().toList();
  }

  public Series append(int value) {
    return new Series(this.name, 
        Stream
        .of(this.values.stream(), Stream.of(value))
        .flatMap(x -> x)
        .toList());
  }

  public Series shift(int offset) {
    return new Series(
        this.name, 
        this.values.stream().map(x -> x + offset).toList());
  }

  @Override
  public String toString() {
    return this.name + ": " + this.values;
  }
}
