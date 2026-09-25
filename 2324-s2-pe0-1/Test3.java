import java.util.Locale;

public final class Test3 {
  private Test3() { }

  /** Test-only vehicle: verifies that RoadTrip uses the Vehicle contract. */
  private static class ProbeVehicle extends Vehicle {
    private final boolean refuse;
    private int calls;
    private double distance;

    public ProbeVehicle(boolean refuse) { this.refuse = refuse; }

    @Override
    public void move(double distance) throws CannotMoveException {
      this.calls++;
      this.distance = distance;
      if (this.refuse) { throw new CannotMoveException(); }
    }
  }

  public static void main(String[] args) throws Exception {
    Locale.setDefault(Locale.US);
    TestSupport t = new TestSupport();
    Bike b = new Bike();
    t.check("sample initial bike", "Bike distance: 0.00 folded: false", b.toString());
    t.check("sample bike trip success silent", "", t.capture(() -> new RoadTrip(b, 10).complete()));
    t.check("bike was moved", "Bike distance: 10.00 folded: false", b.toString());
    b.fold();
    t.check("sample folded bike trip", "This trip cannot be completed.\n",
        t.capture(() -> new RoadTrip(b, 10).complete()));
    t.check("failed trip preserves bike", "Bike distance: 10.00 folded: true", b.toString());
    Car c = new Car(10);
    t.check("sample initial car", "Car fuelLevel: 10.00", c.toString());
    t.check("sample car trip success silent", "", t.capture(() -> new RoadTrip(c, 10).complete()));
    c.move(40);
    t.check("sample car trip refusal", "This trip cannot be completed.\n",
        t.capture(() -> new RoadTrip(c, 10).complete()));
    t.check("failed trip preserves car", "Car fuelLevel: 0.00", c.toString());
    t.check("complete returns void", void.class, RoadTrip.class.getMethod("complete").getReturnType());
    // This helper does not declare throws: complete must handle CannotMoveException itself.
    t.check("complete has no checked exception requirement", "This trip cannot be completed.\n",
        t.capture(() -> completeWithoutThrows(new RoadTrip(new Car(0), 1))));
    ProbeVehicle probe = new ProbeVehicle(false);
    final RoadTrip[] holder = new RoadTrip[1];
    t.check("construction is silent", "", t.capture(() -> holder[0] = new RoadTrip(probe, 3.25)));
    t.check("construction does not move vehicle", 0, probe.calls);
    t.check("arbitrary Vehicle subclass works", "", t.capture(holder[0]::complete));
    t.check("complete invokes move once", 1, probe.calls);
    t.check("distance forwarded exactly", 3.25, probe.distance);
    ProbeVehicle refusal = new ProbeVehicle(true);
    t.check("arbitrary vehicle refusal handled", "This trip cannot be completed.\n",
        t.capture(() -> new RoadTrip(refusal, 4.5).complete()));
    t.check("failed complete does not retry", 1, refusal.calls);
    t.finish();
  }

  private static void completeWithoutThrows(RoadTrip trip) {
    trip.complete();
  }
}
