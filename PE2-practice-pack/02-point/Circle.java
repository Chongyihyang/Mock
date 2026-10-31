public class Circle {
 private final Point center;
 private final double radius;
 public Circle(Point center, double radius) { this.center=center; this.radius=radius; }
 public Point getCenter() { return this.center; }
 public double getRadius() { return this.radius; }
 public boolean contains(Point p) { double x=p.getX()-center.getX(); double y=p.getY()-center.getY(); return x*x+y*y<=radius*radius; }
 public String toString() { return "{ center: "+center+", radius: "+radius+" }"; }
}
