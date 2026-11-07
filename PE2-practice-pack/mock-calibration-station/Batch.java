import java.util.List;
import java.util.stream.Stream;

/** @author YOUR_STUDENT_NUMBER */
public final class Batch {
  private final String name;
  private final List<Integer> readings;

  public Batch(String name, List<Integer> readings) {
    this.name = name;
    this.readings = readings.stream().toList();
  }

  public final String getName() { return this.name; }
  public final List<Integer> getReadings() { return this.readings.stream().toList(); }

  public final Batch append(int reading) {
    return new Batch(
        this.name, 
        Stream
        .of(
          this.readings.stream(), 
          Stream.of(reading))
        .flatMap(x -> x)
        .toList());
  }

  public final Batch shift(int offset) {
    return new Batch(
        this.name,
        this.readings.stream().map(x -> x + offset).toList()
        );
  }

  @Override
  public final String toString() { return this.name + ": " + this.readings; }
}
