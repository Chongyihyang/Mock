import java.util.*;
import java.util.function.*;
import java.util.stream.*;
public class Main {

  public static Stream<Point> pointStream(Point point, Function<Point, Point> f) {
    return Stream.iterate(point, p -> f.apply(p));
  }

  public static Stream<Point> generateGrid(Point point, int n) {
    return pointStream(point, p -> new Point(p.getX() + 1, p.getY()))
      .limit(n)
      .flatMap(r -> 
          pointStream(r, s -> new Point(s.getX(), s.getY() + 1)).limit(n)
          );
  }

  public static Stream<Circle> concentricCircles(Circle circle, Function<Double, Double> f) {
    return Stream
      .iterate(circle, c -> 
          new Circle(c.getCenter(), f.apply(c.getRadius()))
          );
  }

  public static Stream<Point> pointStreamFromCircle(Stream<Circle> circles) {
    return circles.map(c -> c.getCenter());
  }

  public static double estimatePi(Circle circle, Supplier<RandomPoint> supplier, int k) {
    return Stream
      .generate(supplier)
      .limit(k)
      .filter(x -> circle.contains(x))
      .count() * 4 / (double) k;
  }

}
