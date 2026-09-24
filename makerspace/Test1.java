public final class Test1 {
  private Test1() { }

  public static void main(String[] args) throws Exception {
    TestSupport t = new TestSupport();
    WorkItem cut = new CutJob("Sign", 2, 3);
    WorkItem poster = new PosterJob("Map", 3, 2);
    t.check("cut label", "Sign", cut.label());
    t.check("cut sheets", 2, cut.sheets());
    t.check("cut minutes", 7, cut.minutes());
    t.check("cut price", 13, cut.price());
    t.check("cut display", "Sign [sheets=2, minutes=7, price=13]", cut.toString());
    t.check("poster label", "Map", poster.label());
    t.check("poster sheets", 3, poster.sheets());
    t.check("poster minutes", 11, poster.minutes());
    t.check("poster price", 10, poster.price());
    t.check("poster display", "Map [sheets=3, minutes=11, price=10]", poster.toString());
    WorkItem zeroCuts = new CutJob("Plain", 1, 0);
    t.check("zero cuts minutes", 2, zeroCuts.minutes());
    t.check("zero cuts price", 5, zeroCuts.price());
    WorkItem fourColours = new PosterJob("Large map", 1, 4);
    t.check("four colours minutes", 7, fourColours.minutes());
    t.check("four colours price", 10, fourColours.price());
    t.check("constructors and queries are silent", "", t.capture(() -> {
      WorkItem a = new CutJob("Quiet", 1, 1);
      WorkItem b = new PosterJob("Quiet", 1, 1);
      a.label(); a.sheets(); a.minutes(); a.price(); a.toString();
      b.label(); b.sheets(); b.minutes(); b.price(); b.toString();
    }));
    t.check("sample output", "Sign [sheets=2, minutes=7, price=13]\n"
        + "Map [sheets=3, minutes=11, price=10]\n", t.capture(() -> {
          System.out.println(cut); System.out.println(poster);
        }));
    t.finish();
  }
}
