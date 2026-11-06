import java.util.List;
import java.util.stream.Stream;

/**
 * One named series of sensor readings. Question 1.
 *
 * @author
 */
public class Series {
  private String name;
  private List<Integer> values;

  public Series(String name, List<Integer> values) {
    this.name = name;
    this.values = values;
  }

  public String getName() {
    return this.name;
  }

  public List<Integer> getValues() {
    return this.values;
  }

  public Series append(int value) {
    this.values.add(value);
    return this;
  }

  public Series shift(int offset) {
    for (int i = 0; i < this.values.size(); i++) {
      this.values.set(i, this.values.get(i) + offset);
    }
    return this;
  }

  @Override
  public String toString() {
    return this.name + ": " + this.values;
  }
}
