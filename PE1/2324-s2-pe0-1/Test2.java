import java.util.Locale;

public final class Test2 {
  private Test2() { }

  public static void main(String[] args) throws Exception {
    Locale.setDefault(Locale.US);
    TestSupport t = new TestSupport();
    t.check("Foldable is an interface", true, Foldable.class.isInterface());
    t.check("fold returns void", void.class, Foldable.class.getMethod("fold").getReturnType());
    t.check("unfold returns void", void.class, Foldable.class.getMethod("unfold").getReturnType());
    Foldable f = new Bike();
    t.check("sample Foldable construction", "Bike distance: 0.00 folded: false", f.toString());
    f.fold();
    t.check("sample fold", "Bike distance: 0.00 folded: true", f.toString());
    f.unfold();
    t.check("sample unfold", "Bike distance: 0.00 folded: false", f.toString());
    Bike b = new Bike();
    t.check("sample Bike construction", "Bike distance: 0.00 folded: false", b.toString());
    b.move(10);
    t.check("sample movement", "Bike distance: 10.00 folded: false", b.toString());
    b.fold();
    t.failure("sample folded movement refused", CannotMoveException.class, null, () -> b.move(10));
    t.check("refused movement changes neither distance nor folding", "Bike distance: 10.00 folded: true", b.toString());
    b.unfold();
    b.move(10);
    t.check("sample resumed movement", "Bike distance: 20.00 folded: false", b.toString());
    Vehicle v = b;
    v.move(2.5);
    t.check("fractional movement through Vehicle", "Bike distance: 22.50 folded: false", b.toString());
    b.fold(); b.fold();
    t.check("fold is not a toggle", "Bike distance: 22.50 folded: true", b.toString());
    b.unfold(); b.unfold();
    t.check("unfold is not a toggle", "Bike distance: 22.50 folded: false", b.toString());
    t.check("bikes have independent state", "Bike distance: 0.00 folded: false", f.toString());
    t.check("constructors and successful operations are silent", "", t.capture(() -> {
      Bike quiet = new Bike(); quiet.fold(); quiet.unfold(); quiet.move(1); quiet.toString();
    }));
    t.check("refused movement does not print", "", t.capture(() -> {
      Bike folded = new Bike(); folded.fold();
      try {
        folded.move(1);
        throw new AssertionError("Missing CannotMoveException");
      } catch (CannotMoveException expected) { }
    }));
    t.finish();
  }
}
