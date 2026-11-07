import java.util.List;
import java.util.stream.Stream;

/** @author YOUR_STUDENT_NUMBER */
public class Batch {
  private String name;
  private List<Integer> readings;

  public Batch(String name, List<Integer> readings) {
    this.name = name;
    this.readings = readings;
  }

  public String getName() { return this.name; }
  public List<Integer> getReadings() { return this.readings; }

  public Batch append(int reading) {
    throw new UnsupportedOperationException("Q1 append");
  }

  public Batch shift(int offset) {
    throw new UnsupportedOperationException("Q1 shift");
  }

  @Override
  public String toString() { return this.name + ": " + this.readings; }
}
