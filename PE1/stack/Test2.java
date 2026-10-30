/** All Part 2 sample results. Depends on Part 1 and the new Part 2 methods. */
public final class Test2 {
  private Test2() {
  }

  public static void main(String[] args) {
    TestSupport t = new TestSupport();
    t.check("integer factory", "Stack: 1 2 3",
        ArrayStack.of(new Integer[] {1, 2, 3}, 10).toString());
    t.check("mixed object factory", "Stack: 1 foo bar",
        ArrayStack.of(new Object[] {1, "foo", "bar"}, 10).toString());
    ArrayStack<Integer> as0 = ArrayStack.of(new Integer[] {1, 2, 3, 4}, 2);
    t.check("factory truncation", "Stack: 1 2", as0.toString());
    ArrayStack<Integer> as1 = ArrayStack.of(new Integer[] {4, 5, 6}, 10);
    t.check("as1", "Stack: 4 5 6", as1.toString());
    ArrayStack<Integer> as2 = ArrayStack.of(new Integer[] {1, 2, 3}, 10);
    t.check("as2", "Stack: 1 2 3", as2.toString());
    as2.pushAll(as1);
    t.check("pushAll destination", "Stack: 1 2 3 6 5 4", as2.toString());
    t.check("pushAll source", "Stack:", as1.toString());
    as1 = ArrayStack.of(new Integer[] {4, 5, 6}, 10);
    t.check("reset as1", "Stack: 4 5 6", as1.toString());
    ArrayStack<Integer> as3 = ArrayStack.of(new Integer[] {1, 2, 3}, 5);
    t.check("as3", "Stack: 1 2 3", as3.toString());
    as3.pushAll(as1);
    t.check("pushAll with limited room", "Stack: 1 2 3 6 5", as3.toString());
    ArrayStack<Number> asn = new ArrayStack<>(10);
    t.check("number stack", "Stack:", asn.toString());
    asn.pushAll(as2);
    t.check("pushAll Integer into Number", "Stack: 4 5 6 3 2 1", asn.toString());
    ArrayStack<String> as4 = ArrayStack.of(new String[] {"d", "e", "f"}, 10);
    t.check("as4", "Stack: d e f", as4.toString());
    ArrayStack<String> as5 = ArrayStack.of(new String[] {"a", "b", "c"}, 10);
    t.check("as5", "Stack: a b c", as5.toString());
    as4.popAll(as5);
    t.check("popAll destination", "Stack: a b c f e d", as5.toString());
    as4 = ArrayStack.of(new String[] {"d", "e", "f"}, 10);
    t.check("reset as4", "Stack: d e f", as4.toString());
    ArrayStack<String> as6 = ArrayStack.of(new String[] {"a", "b", "c"}, 5);
    t.check("as6", "Stack: a b c", as6.toString());
    as4.popAll(as6);
    t.check("popAll with limited room", "Stack: a b c f e", as6.toString());
    ArrayStack<Integer> as7 = ArrayStack.of(new Integer[] {7, 8, 9}, 5);
    t.check("as7", "Stack: 7 8 9", as7.toString());
    as7.popAll(asn);
    t.check("popAll Integer into Number", "Stack: 4 5 6 3 2 1 9 8 7", asn.toString());
    t.finish();
  }
}
