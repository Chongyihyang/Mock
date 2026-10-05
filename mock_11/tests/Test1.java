public final class Test1 {
  private Test1() { }
  public static void main(String[] args) throws Exception {
    TestSupport t = new TestSupport();
    Seq<String> s = new Seq<String>(4);
    t.check("empty size", 0, s.size());
    s.set("A"); s.set("B"); s.set("A");
    t.check("append count", 3, s.size());
    t.check("first retained", "A", s.get(0));
    t.check("second retained", "B", s.get(1));
    t.check("duplicate appended", "A", s.get(2));
    s.set("last");
    t.check("full capacity count", 4, s.size());
    t.check("last slot", "last", s.get(3));
    Seq<Integer> numbers = new Seq<Integer>(2); numbers.set(17);
    t.check("another type", 17, numbers.get(0));
    t.check("sequences independent", 1, numbers.size());
    t.check("reads do not change size", 4, s.size());
    paperSample(t);
    t.finish("Test1");
  }
  private static void paperSample(TestSupport t) throws Exception {
    Seq<String> seq = new Seq<String>(3);
    seq.set("North");
    t.check("sample Q1 step 3", 1, seq.size());
    seq.set("South");
    t.check("sample Q1 step 5", "North", seq.get(0));
    t.check("sample Q1 step 6", "South", seq.get(1));
    t.check("sample Q1 step 7", 2, seq.size());
  }
}
