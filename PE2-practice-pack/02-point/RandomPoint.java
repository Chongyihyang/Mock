public class RandomPoint extends Point {
 private static java.util.Random random = new java.util.Random(1);
 public RandomPoint(double minX, double maxX, double minY, double maxY) {
  super(minX+(maxX-minX)*random.nextDouble(), minY+(maxY-minY)*random.nextDouble());
 }
 public static void setSeed(long seed) { random=new java.util.Random(seed); }
}
