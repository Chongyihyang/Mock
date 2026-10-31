import java.util.*;
import java.util.function.*;
import java.util.stream.*;
public class Test1 {
 public static void main(String[] args) throws Throwable {
CS2030STest.check("iterate", List.of("(0.0, 0.0)","(0.0, 1.0)","(0.0, 2.0)"), () -> Main.pointStream(new Point(0,0),p->new Point(p.getX(),p.getY()+1)).limit(3).map(Object::toString).collect(Collectors.toList()));
CS2030STest.check("grid order", List.of("(-1.0, 0.0)","(-1.0, 1.0)","(0.0, 0.0)","(0.0, 1.0)"), () -> Main.generateGrid(new Point(-1,0),2).map(Object::toString).collect(Collectors.toList()));
CS2030STest.check("empty grid", 0L, () -> Main.generateGrid(new Point(0,0),0).count());
CS2030STest.check("radii", List.of(1.0,1.5,2.0), () -> Main.concentricCircles(new Circle(new Point(0,0),1),r->r+0.5).limit(3).map(Circle::getRadius).collect(Collectors.toList()));
CS2030STest.check("centers", List.of("(1.0, 2.0)"), () -> Main.pointStreamFromCircle(Stream.of(new Circle(new Point(1,2),5))).map(Object::toString).collect(Collectors.toList()));
RandomPoint.setSeed(10);
CS2030STest.check("pi seed 10", 3.1284, () -> Main.estimatePi(new Circle(new Point(0.5,0.5),0.5),()->new RandomPoint(0,1,0,1),10000));
RandomPoint.setSeed(55);
CS2030STest.check("pi seed 55", 3.1216, () -> Main.estimatePi(new Circle(new Point(0.5,0.5),0.5),()->new RandomPoint(0,1,0,1),10000));

 CS2030STest.finish();
 }
}
