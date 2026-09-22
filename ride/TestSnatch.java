import java.util.Objects;
import java.util.function.Supplier;

/** Automatic checks for every displayed result in the Snatch A Ride PDF. */
public final class TestSnatch {
  private static int passed = 0;
  private static int failed = 0;

  private TestSnatch() {
  }

  private static void check(String label, Object expected, Supplier<?> expression) {
    try {
      Object actual = expression.get();
      if (Objects.equals(expected, actual)) {
        passed++;
        System.out.println("PASS: " + label);
      } else {
        failed++;
        System.out.println("FAIL: " + label);
        System.out.println("  expected: " + expected);
        System.out.println("  actual:   " + actual);
      }
    } catch (RuntimeException | AssertionError e) {
      failed++;
      System.out.println("FAIL: " + label);
      System.out.println("  expected: " + expected);
      System.out.println("  threw:    " + e);
    }
  }

  private static void services() {
    check("JustRide: 20 km, 3 passengers, 1000", 440,
        () -> new JustRide().computeFare(new Request(20, 3, 1000)));
    check("JustRide: 10 km, 1 passenger, 0900", 720,
        () -> new JustRide().computeFare(new Request(10, 1, 900)));
    check("TakeACab: 20 km, 3 passengers, 1000", 860,
        () -> new TakeACab().computeFare(new Request(20, 3, 1000)));
    check("TakeACab: 10 km, 1 passenger, 0900", 530,
        () -> new TakeACab().computeFare(new Request(10, 1, 900)));
    check("ShareARide: 20 km, 3 passengers, 1000", 333,
        () -> new ShareARide().computeFare(new Request(20, 3, 1000)));
    check("ShareARide: 10 km, 1 passenger, 0900", 1000,
        () -> new ShareARide().computeFare(new Request(10, 1, 900)));
    check("JustRide.toString", "JustRide", () -> new JustRide().toString());
    check("TakeACab.toString", "TakeACab", () -> new TakeACab().toString());
    check("ShareARide.toString", "ShareARide", () -> new ShareARide().toString());
  }

  private static void cars() {
    check("Cab: 5 minutes", "Cab SHA1234 (5 mins away)",
        () -> new Cab("SHA1234", 5).toString());
    check("Cab: 1 minute", "Cab SHA1234 (1 min away)",
        () -> new Cab("SHA1234", 1).toString());
    check("PrivateCar: 4 minutes", "PrivateCar SU4032 (4 mins away)",
        () -> new PrivateCar("SU4032", 4).toString());
    check("PrivateCar: 1 minute", "PrivateCar SU4032 (1 min away)",
        () -> new PrivateCar("SU4032", 1).toString());
  }

  private static void bookings() {
    try {
      // The PDF includes this assignment: compilation checks the generic contract.
      Comparable<Booking> b = new Booking(new Cab("SHA1234", 5),
          new JustRide(), new Request(20, 3, 1000));
      Booking b1 = new Booking(new Cab("SHA1234", 3),
          new JustRide(), new Request(20, 3, 1000));
      Booking b2 = new Booking(new Cab("SBC8888", 5),
          new JustRide(), new Request(20, 3, 1000));
      Booking b3 = new Booking(new PrivateCar("SU4032", 5),
          new ShareARide(), new Request(20, 3, 1000));
      check("b3.compareTo(b2) < 0", true, () -> b3.compareTo(b2) < 0);
      check("b1.compareTo(b3) < 0", false, () -> b1.compareTo(b3) < 0);
      check("b1.compareTo(b2) < 0", true, () -> b1.compareTo(b2) < 0);
    } catch (RuntimeException | AssertionError e) {
      failed += 3;
      System.out.println("FAIL: booking sample setup; 3 comparisons could not run");
      System.out.println("  valid sample bookings should construct successfully");
      System.out.println("  threw: " + e);
    }
    check("Cab rejects ShareARide with the exact message",
        "Cab SHA1234 (5 mins away) does not provide the ShareARide service.",
        () -> {
          try {
            new Booking(new Cab("SHA1234", 5),
                new ShareARide(), new Request(20, 3, 1000));
          } catch (IllegalArgumentException e) {
            return e.getMessage();
          }
          return "<no IllegalArgumentException thrown>";
        });
  }

  public static void main(String[] args) {
    String group = args.length == 0 ? "all" : args[0];
    if (args.length > 1 || !(group.equals("all") || group.equals("1")
        || group.equals("2") || group.equals("3"))) {
      System.err.println("Usage: java TestSnatch [all|1|2|3]");
      System.exit(2);
    }
    if (group.equals("all") || group.equals("1")) {
      services();
    }
    if (group.equals("all") || group.equals("2")) {
      cars();
    }
    if (group.equals("all") || group.equals("3")) {
      bookings();
    }
    System.out.println(passed + " passed; " + failed + " failed.");
    if (failed > 0) {
      System.exit(1);
    }
  }
}
