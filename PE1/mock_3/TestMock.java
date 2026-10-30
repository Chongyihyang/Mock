import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * Behavioral tests only; contains no Maker Hub implementation.
 * Reflection lets you compile this file before future-stage types exist.
 * Do not use reflection in your own solution.
 */
public final class TestMock {
  private static int passed;
  private static int failed;

  private TestMock() {}

  private interface Task {
    public void run() throws Exception;
  }

  private static Class<?> type(String name) throws ClassNotFoundException {
    if (name.equals("int")) {
      return int.class;
    }
    if (name.equals("String")) {
      return String.class;
    }
    if (name.equals("Pass[]")) {
      return Array.newInstance(type("Pass"), 0).getClass();
    }
    return Class.forName(name);
  }

  private static Class<?>[] signature(String names) throws ClassNotFoundException {
    if (names.isEmpty()) {
      return new Class<?>[0];
    }
    String[] parts = names.split(",");
    Class<?>[] result = new Class<?>[parts.length];
    for (int i = 0; i < parts.length; i++) {
      result[i] = type(parts[i]);
    }
    return result;
  }

  private static Exception unwrap(InvocationTargetException wrapper) {
    Throwable cause = wrapper.getCause();
    if (cause instanceof Error) {
      throw (Error) cause;
    }
    if (cause instanceof Exception) {
      return (Exception) cause;
    }
    return new Exception(cause);
  }

  private static Object make(String name, String parameters, Object... arguments)
      throws Exception {
    try {
      return type(name).getConstructor(signature(parameters)).newInstance(arguments);
    } catch (InvocationTargetException e) {
      throw unwrap(e);
    }
  }

  private static Object call(Object target, String method, String parameters,
      Object... arguments) throws Exception {
    try {
      return target.getClass().getMethod(method, signature(parameters))
          .invoke(target, arguments);
    } catch (InvocationTargetException e) {
      throw unwrap(e);
    }
  }

  private static Object get(Object target, String method) throws Exception {
    return call(target, method, "");
  }

  private static Object visit(String id, int visits) throws Exception {
    return make("VisitPass", "String,int", id, visits);
  }

  private static Object workshop(String code, int capacity, int price) throws Exception {
    return make("Workshop", "String,int,int", code, capacity, price);
  }

  private static Object wallet(int amount) throws Exception {
    return make("Wallet", "int", amount);
  }

  private static Object walletPass(String id, Object account) throws Exception {
    return make("WalletPass", "String,Wallet", id, account);
  }

  private static Object sleeve(Object original) throws Exception {
    return make("DiscountPass", "Pass", original);
  }

  private static void use(Object pass, int price) throws Exception {
    // Invoke through the common role, not a concrete type.
    try {
      type("Pass").getMethod("use", int.class).invoke(pass, price);
    } catch (InvocationTargetException e) {
      throw unwrap(e);
    }
  }

  private static void admit(Object workshop, Object pass) throws Exception {
    call(workshop, "admit", "Pass", pass);
  }

  private static String[] batch(Object workshop, Object... passes) throws Exception {
    Object array = Array.newInstance(type("Pass"), passes.length);
    for (int i = 0; i < passes.length; i++) {
      Array.set(array, i, passes[i]);
    }
    try {
      Method method = type("AdmissionDesk").getMethod("admitAll",
          type("Workshop"), type("Pass[]"));
      return (String[]) method.invoke(null, workshop, array);
    } catch (InvocationTargetException e) {
      throw unwrap(e);
    }
  }

  private static void equal(Object expected, Object actual) {
    if (!Objects.equals(expected, actual)) {
      throw new AssertionError("expected <" + expected + "> but got <" + actual + ">");
    }
  }

  private static void lines(String[] expected, String[] actual) {
    if (!Arrays.equals(expected, actual)) {
      throw new AssertionError("expected " + Arrays.toString(expected)
          + " but got " + Arrays.toString(actual));
    }
  }

  private static Exception expect(String name, Task action) throws Exception {
    Class<?> expected = type(name);
    try {
      action.run();
    } catch (Exception e) {
      if (expected.isInstance(e)) {
        return e;
      }
      throw new AssertionError("expected " + name + " but got " + e, e);
    }
    throw new AssertionError("expected " + name + " but nothing was thrown");
  }

  private static void test(String name, Task action) {
    try {
      action.run();
      passed++;
      System.out.println("PASS " + name);
    } catch (Exception | AssertionError e) {
      failed++;
      System.out.println("FAIL " + name + " -> " + e);
    }
  }

  private static void stage1() {
    test("1.01 successful visit spends one place and one visit", () -> {
      Object pass = visit("A", 2);
      Object room = workshop("W", 3, 99);
      admit(room, pass);
      equal(1, get(pass, "remaining"));
      equal(2, get(room, "seatsLeft"));
      equal("A", get(pass, "id"));
      equal("W", get(room, "code"));
    });
    test("1.02 direct redemption does not depend on price magnitude", () -> {
      Object pass = visit("A", 2);
      use(pass, 1);
      use(pass, 1000);
      equal(0, get(pass, "remaining"));
    });
    test("1.03 empty pass refuses without spending a seat", () -> {
      Object pass = visit("A", 0);
      Object room = workshop("W", 2, 5);
      equal("Pass A cannot be used",
          expect("PassRejectedException", () -> admit(room, pass)).getMessage());
      equal(2, get(room, "seatsLeft"));
      equal(0, get(pass, "remaining"));
    });
    test("1.04 full workshop leaves pass intact", () -> {
      Object pass = visit("A", 2);
      Object room = workshop("W", 0, 5);
      equal("Workshop W is full",
          expect("FullWorkshopException", () -> admit(room, pass)).getMessage());
      equal(2, get(pass, "remaining"));
      equal(0, get(room, "seatsLeft"));
    });
    test("1.05 full takes precedence over exhausted pass", () -> {
      Object pass = visit("A", 0);
      Object room = workshop("W", 0, 5);
      expect("FullWorkshopException", () -> admit(room, pass));
    });
    test("1.06 desk continues after refusal", () -> {
      Object room = workshop("W", 1, 5);
      Object empty = visit("Empty", 0);
      Object ready = visit("Ready", 1);
      lines(new String[] {"REJECTED: Pass Empty cannot be used", "ADMITTED Ready to W"},
          batch(room, empty, ready));
      equal(0, get(room, "seatsLeft"));
      equal(0, get(ready, "remaining"));
    });
    test("1.07 duplicate reference is processed twice", () -> {
      Object room = workshop("W", 2, 5);
      Object pass = visit("A", 1);
      lines(new String[] {"ADMITTED A to W", "REJECTED: Pass A cannot be used"},
          batch(room, pass, pass));
      equal(1, get(room, "seatsLeft"));
    });
    test("1.08 empty batch", () -> {
      Object room = workshop("W", 2, 5);
      lines(new String[0], batch(room));
      equal(2, get(room, "seatsLeft"));
    });
    test("1.09 one pass works across workshops", () -> {
      Object pass = visit("A", 2);
      Object first = workshop("W1", 1, 4);
      Object second = workshop("W2", 1, 50);
      admit(first, pass);
      admit(second, pass);
      equal(0, get(pass, "remaining"));
      equal(0, get(first, "seatsLeft"));
      equal(0, get(second, "seatsLeft"));
    });
    test("1.10 equal identifiers do not merge credentials", () -> {
      Object a = visit("Same", 1);
      Object b = visit("Same", 2);
      use(a, 10);
      equal(0, get(a, "remaining"));
      equal(2, get(b, "remaining"));
    });
    test("1.11 invalid identifiers", () -> {
      for (String id : new String[] {null, "", " \t\n", "\u2003"}) {
        expect("java.lang.IllegalArgumentException", () -> visit(id, 1));
        expect("java.lang.IllegalArgumentException", () -> workshop(id, 1, 1));
      }
    });
    test("1.12 valid labels retain spaces", () -> {
      equal(" A ", get(visit(" A ", 1), "id"));
      equal(" W ", get(workshop(" W ", 1, 1), "code"));
    });
    test("1.13 invalid numeric constructor arguments", () -> {
      expect("java.lang.IllegalArgumentException", () -> visit("A", -1));
      expect("java.lang.IllegalArgumentException", () -> workshop("W", -1, 1));
      expect("java.lang.IllegalArgumentException", () -> workshop("W", 1, 0));
      expect("java.lang.IllegalArgumentException", () -> workshop("W", 1, -1));
    });
    test("1.14 invalid redemption does not consume visits", () -> {
      Object pass = visit("A", 2);
      expect("java.lang.IllegalArgumentException", () -> use(pass, 0));
      expect("java.lang.IllegalArgumentException", () -> use(pass, -2));
      equal(2, get(pass, "remaining"));
      use(pass, 1);
      equal(1, get(pass, "remaining"));
    });
    test("1.15 invalid price takes precedence on an empty pass", () -> {
      Object pass = visit("A", 0);
      expect("java.lang.IllegalArgumentException", () -> use(pass, 0));
    });
    test("1.16 checked exception family and messages", () -> {
      Class<?> family = type("AdmissionException");
      if (!Exception.class.isAssignableFrom(family)
          || RuntimeException.class.isAssignableFrom(family)
          || !family.isAssignableFrom(type("PassRejectedException"))
          || !family.isAssignableFrom(type("FullWorkshopException"))) {
        throw new AssertionError("business refusals must belong to the checked AdmissionException family");
      }
      equal("custom", get(make("AdmissionException", "String", "custom"), "getMessage"));
      equal("Pass A cannot be used",
          get(make("PassRejectedException", "String", "A"), "getMessage"));
      equal("Workshop W is full",
          get(make("FullWorkshopException", "String", "W"), "getMessage"));
    });
  }

  private static void stage2() {
    test("2.01 wallet charge and exact balance", () -> {
      Object funds = wallet(7);
      Object pass = walletPass("A", funds);
      use(pass, 7);
      equal(0, get(funds, "balance"));
      expect("PassRejectedException", () -> use(pass, 1));
      equal(0, get(funds, "balance"));
    });
    test("2.02 costly refusal followed by cheaper success", () -> {
      Object funds = wallet(5);
      Object pass = walletPass("A", funds);
      expect("PassRejectedException", () -> use(pass, 6));
      equal(5, get(funds, "balance"));
      use(pass, 3);
      equal(2, get(funds, "balance"));
    });
    test("2.03 shared wallet is not copied at pass construction", () -> {
      Object funds = wallet(10);
      Object a = walletPass("A", funds);
      Object b = walletPass("B", funds);
      equal(10, get(funds, "balance"));
      use(a, 6);
      equal(4, get(funds, "balance"));
      expect("PassRejectedException", () -> use(b, 5));
      use(b, 4);
      equal(0, get(funds, "balance"));
    });
    test("2.04 top-up affects an existing pass after refusal", () -> {
      Object funds = wallet(1);
      Object pass = walletPass("A", funds);
      Object room = workshop("W", 1, 4);
      expect("PassRejectedException", () -> admit(room, pass));
      equal(1, get(room, "seatsLeft"));
      call(funds, "topUp", "int", 3);
      admit(room, pass);
      equal(0, get(funds, "balance"));
      equal(0, get(room, "seatsLeft"));
    });
    test("2.05 independent wallets despite matching pass identifiers", () -> {
      Object first = wallet(8);
      Object second = wallet(8);
      Object a = walletPass("Same", first);
      Object b = walletPass("Same", second);
      use(a, 5);
      equal(3, get(first, "balance"));
      equal(8, get(second, "balance"));
      use(b, 8);
      equal(0, get(second, "balance"));
    });
    test("2.06 full workshop cannot charge a wallet", () -> {
      Object funds = wallet(10);
      Object pass = walletPass("A", funds);
      Object room = workshop("W", 0, 5);
      expect("FullWorkshopException", () -> admit(room, pass));
      equal(10, get(funds, "balance"));
    });
    test("2.07 invalid wallet inputs preserve balance", () -> {
      expect("java.lang.IllegalArgumentException", () -> wallet(-1));
      Object funds = wallet(0);
      expect("java.lang.IllegalArgumentException", () -> call(funds, "topUp", "int", 0));
      expect("java.lang.IllegalArgumentException", () -> call(funds, "topUp", "int", -2));
      equal(0, get(funds, "balance"));
      call(funds, "topUp", "int", 2);
      equal(2, get(funds, "balance"));
    });
    test("2.08 invalid wallet-pass construction", () -> {
      Object funds = wallet(3);
      expect("java.lang.IllegalArgumentException", () -> walletPass("A", null));
      for (String id : new String[] {null, "", " \t", "\u2003"}) {
        expect("java.lang.IllegalArgumentException", () -> walletPass(id, funds));
      }
      equal(3, get(funds, "balance"));
    });
    test("2.09 mixed schemes through the same desk", () -> {
      Object funds = wallet(4);
      Object paid = walletPass("Paid", funds);
      Object visits = visit("Visit", 1);
      Object room = workshop("W", 3, 4);
      lines(new String[] {"ADMITTED Paid to W", "ADMITTED Visit to W",
          "REJECTED: Pass Paid cannot be used"}, batch(room, paid, visits, paid));
      equal(1, get(room, "seatsLeft"));
      equal(0, get(visits, "remaining"));
      equal(0, get(funds, "balance"));
    });
    test("2.10 invalid redemption leaves an empty wallet unchanged", () -> {
      Object funds = wallet(0);
      Object pass = walletPass("A", funds);
      expect("java.lang.IllegalArgumentException", () -> use(pass, 0));
      expect("java.lang.IllegalArgumentException", () -> use(pass, -5));
      equal(0, get(funds, "balance"));
    });
  }

  private static void stage3() {
    test("3.01 sponsor repeated use and admission", () -> {
      Object pass = make("SponsorPass", "String", "S");
      use(pass, 1);
      use(pass, 10000);
      Object room = workshop("W", 2, 99);
      lines(new String[] {"ADMITTED S to W", "ADMITTED S to W"}, batch(room, pass, pass));
      equal(0, get(room, "seatsLeft"));
    });
    test("3.02 sponsor validates identifiers and requested prices", () -> {
      for (String id : new String[] {null, "", " \t", "\u2003"}) {
        expect("java.lang.IllegalArgumentException", () -> make("SponsorPass", "String", id));
      }
      Object pass = make("SponsorPass", "String", " S ");
      equal(" S ", get(pass, "id"));
      expect("java.lang.IllegalArgumentException", () -> use(pass, 0));
      expect("java.lang.IllegalArgumentException", () -> use(pass, -1));
      use(pass, 1);
    });
    test("3.03 sleeve delegates reduced charge and identity", () -> {
      Object funds = wallet(10);
      Object original = walletPass("A", funds);
      Object promo = sleeve(original);
      equal("A", get(promo, "id"));
      use(promo, 7);
      equal(6, get(funds, "balance"));
      use(original, 6);
      equal(0, get(funds, "balance"));
    });
    test("3.04 reduction never reaches zero", () -> {
      Object funds = wallet(5);
      Object promo = sleeve(walletPass("A", funds));
      use(promo, 1); use(promo, 2); use(promo, 3); use(promo, 4);
      equal(1, get(funds, "balance"));
    });
    test("3.05 invalid original request is not repaired by a sleeve", () -> {
      Object funds = wallet(5);
      Object promo = sleeve(walletPass("A", funds));
      expect("java.lang.IllegalArgumentException", () -> use(promo, 0));
      expect("java.lang.IllegalArgumentException", () -> use(promo, -7));
      equal(5, get(funds, "balance"));
    });
    test("3.06 sleeve around visit pass cannot mint visits", () -> {
      Object original = visit("V", 1);
      Object promo = sleeve(original);
      use(promo, 8);
      equal(0, get(original, "remaining"));
      expect("PassRejectedException", () -> use(original, 1));
    });
    test("3.07 two sleeves share one original entitlement", () -> {
      Object original = visit("V", 1);
      Object a = sleeve(original);
      Object b = sleeve(original);
      use(a, 10);
      expect("PassRejectedException", () -> use(b, 10));
      equal(0, get(original, "remaining"));
    });
    test("3.08 nested sleeves apply reductions separately", () -> {
      Object funds = wallet(3);
      Object nested = sleeve(sleeve(walletPass("A", funds)));
      use(nested, 7);
      equal(2, get(funds, "balance"));
      use(nested, 1);
      equal(1, get(funds, "balance"));
    });
    test("3.09 sleeve around sponsor remains reusable", () -> {
      Object promo = sleeve(make("SponsorPass", "String", "S"));
      Object room = workshop("W", 2, 1);
      lines(new String[] {"ADMITTED S to W", "ADMITTED S to W"},
          batch(room, promo, promo));
    });
    test("3.10 declined discounted admission preserves both resources", () -> {
      Object funds = wallet(2);
      Object promo = sleeve(walletPass("A", funds));
      Object room = workshop("W", 1, 6);
      equal("Pass A cannot be used",
          expect("PassRejectedException", () -> admit(room, promo)).getMessage());
      equal(2, get(funds, "balance"));
      equal(1, get(room, "seatsLeft"));
      call(funds, "topUp", "int", 1);
      admit(room, promo);
      equal(0, get(funds, "balance"));
      equal(0, get(room, "seatsLeft"));
    });
    test("3.11 full workshop preserves shared discounted wallet", () -> {
      Object funds = wallet(10);
      Object original = walletPass("A", funds);
      Object promo = sleeve(original);
      Object room = workshop("W", 0, 7);
      expect("FullWorkshopException", () -> admit(room, promo));
      equal(10, get(funds, "balance"));
      use(original, 10);
      equal(0, get(funds, "balance"));
    });
    test("3.12 null sleeve input", () ->
        expect("java.lang.IllegalArgumentException", () -> sleeve(null)));
  }

  private static void stage4() {
    test("4.01 all arrangements honor the common positive-price contract", () -> {
      Object[] passes = {visit("V", 2), walletPass("W", wallet(20)),
          make("SponsorPass", "String", "S"),
          sleeve(walletPass("D", wallet(20))), sleeve(visit("DV", 2))};
      Object room = workshop("Mixed", 5, 4);
      lines(new String[] {"ADMITTED V to Mixed", "ADMITTED W to Mixed",
          "ADMITTED S to Mixed", "ADMITTED D to Mixed", "ADMITTED DV to Mixed"},
          batch(room, passes));
      equal(0, get(room, "seatsLeft"));
    });
    test("4.02 full-workshop refusal is scheme-independent", () -> {
      Object[] passes = {visit("V", 1), walletPass("W", wallet(2)),
          make("SponsorPass", "String", "S"), sleeve(visit("D", 1))};
      Object full = workshop("Full", 0, 1);
      for (Object pass : passes) {
        expect("FullWorkshopException", () -> admit(full, pass));
        use(pass, 1);
      }
    });
    test("4.03 minimum positive price remains supported by every arrangement", () -> {
      Object[] passes = {visit("V", 1), walletPass("W", wallet(1)),
          make("SponsorPass", "String", "S"),
          sleeve(walletPass("D", wallet(1))), sleeve(sleeve(visit("DV", 1)))};
      for (Object pass : passes) {
        use(pass, 1);
      }
    });
    test("4.04 invalid prices have consistent unchecked behavior", () -> {
      Object[] passes = {visit("V", 0), walletPass("W", wallet(0)),
          make("SponsorPass", "String", "S"), sleeve(visit("D", 0))};
      for (Object pass : passes) {
        expect("java.lang.IllegalArgumentException", () -> use(pass, 0));
      }
    });
    test("4.05 shared-wallet batch makes decisions at each attempt", () -> {
      Object funds = wallet(8);
      Object original = walletPass("Paid", funds);
      Object promo = sleeve(original);
      Object room = workshop("W", 3, 6);
      lines(new String[] {"ADMITTED Paid to W", "REJECTED: Pass Paid cannot be used",
          "ADMITTED Paid to W"}, batch(room, promo, original, promo));
      equal(2, get(funds, "balance"));
      equal(1, get(room, "seatsLeft"));
    });
  }

  public static void main(String[] args) {
    String group = args.length == 0 ? "all" : args[0];
    if (args.length > 1 || !Arrays.asList("all", "1", "2", "3", "4").contains(group)) {
      System.err.println("Usage: java -cp out TestMock [all|1|2|3|4]");
      System.exit(2);
    }
    if (group.equals("all") || group.equals("1")) { stage1(); }
    if (group.equals("all") || group.equals("2")) { stage2(); }
    if (group.equals("all") || group.equals("3")) { stage3(); }
    if (group.equals("all") || group.equals("4")) { stage4(); }
    System.out.println("RESULT: " + passed + " passed; " + failed + " failed.");
    if (failed > 0) {
      System.exit(1);
    }
  }
}

