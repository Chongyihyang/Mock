import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Locale;

public final class Test1 {
  private Test1() { }

  public static void main(String[] args) throws Exception {
    Locale.setDefault(Locale.US);
    TestSupport t = new TestSupport();
    t.check("checked exception", true, Exception.class.isAssignableFrom(CannotMoveException.class)
        && !RuntimeException.class.isAssignableFrom(CannotMoveException.class));
    t.check("no-argument exception has null message", null, new CannotMoveException().getMessage());
    t.check("Vehicle is abstract", true, Modifier.isAbstract(Vehicle.class.getModifiers()));
    Method move = Vehicle.class.getDeclaredMethod("move", double.class);
    t.check("move is public abstract void", true, Modifier.isPublic(move.getModifiers())
        && Modifier.isAbstract(move.getModifiers()) && move.getReturnType() == void.class);
    t.check("Vehicle.move declares CannotMoveException", true,
        Arrays.asList(move.getExceptionTypes()).contains(CannotMoveException.class));
    Field efficiency = Car.class.getField("FUEL_EFFICIENCY");
    t.check("efficiency is public static final double", true,
        Modifier.isPublic(efficiency.getModifiers()) && Modifier.isStatic(efficiency.getModifiers())
        && Modifier.isFinal(efficiency.getModifiers()) && efficiency.getType() == double.class);
    t.check("sample fuel efficiency", 5.0, Car.FUEL_EFFICIENCY);
    Vehicle v = new Car(10);
    t.check("sample construction through Vehicle", "Car fuelLevel: 10.00", v.toString());
    Car c = (Car) v;
    t.check("sample downcast preserves object", "Car fuelLevel: 10.00", c.toString());
    c.refuel(5.0);
    t.check("sample refuel", "Car fuelLevel: 15.00", c.toString());
    v.move(10);
    t.check("sample movement via Vehicle", "Car fuelLevel: 13.00", v.toString());
    t.failure("sample insufficient fuel", CannotMoveException.class, null, () -> v.move(200));
    t.check("failed movement preserves fuel", "Car fuelLevel: 13.00", v.toString());
    Car exact = new Car(2.5);
    exact.move(12.5);
    t.check("exact fuel is enough", "Car fuelLevel: 0.00", exact.toString());
    t.failure("empty car cannot move", CannotMoveException.class, null, () -> exact.move(1));
    exact.refuel(0.5);
    exact.move(1.25);
    t.check("fractional fuel and distance", "Car fuelLevel: 0.25", exact.toString());
    t.check("cars have independent state", "Car fuelLevel: 13.00", c.toString());
    t.check("constructors, queries and successful calls are silent", "", t.capture(() -> {
      Car quiet = new Car(1); quiet.refuel(1); quiet.move(5); quiet.toString();
    }));
    t.check("failed move throws without printing", "", t.capture(() -> {
      try {
        new Car(0).move(1);
        throw new AssertionError("Missing CannotMoveException");
      } catch (CannotMoveException expected) { }
    }));
    t.finish();
  }
}
