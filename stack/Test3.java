/** Extra checks of the written contract, separate from the PDF's samples. */
public final class Test3 {
  private Test3() {
  }

  private static void basics(TestSupport t) {
    Stack<Integer> zero = new ArrayStack<>(0);
    zero.push(7);
    t.check("zero capacity contents", "Stack:", zero.toString());
    t.check("zero capacity size", 0, zero.getStackSize());
    t.check("zero capacity pop", null, zero.pop());
    Stack<Integer> one = new ArrayStack<>(1);
    one.push(7);
    one.push(8);
    t.check("overflow does not inflate size", 1, one.getStackSize());
    t.check("overflow does not replace top", 7, one.pop());
    t.check("size after pop", 0, one.getStackSize());
    t.check("empty pop again", null, one.pop());
    t.check("empty pop preserves size", 0, one.getStackSize());
    one.push(9);
    t.check("reuse restores capacity", 9, one.pop());
    ArrayStack<String> empty = ArrayStack.of(new String[] {}, 3);
    t.check("empty factory", "Stack:", empty.toString());
    empty.push("x");
    t.check("factory retains requested capacity", "x", empty.pop());
    ArrayStack<Integer> noRoom = ArrayStack.of(new Integer[] {1, 2}, 0);
    t.check("zero capacity factory", 0, noRoom.getStackSize());
    ArrayStack<Integer> limited = ArrayStack.of(new Integer[] {1, 2, 3}, 2);
    t.check("factory size", 2, limited.getStackSize());
    Integer top = limited.pop(); // Must return Integer without a client cast.
    t.check("factory keeps first items in order", 2, top);
    t.check("factory bottom", 1, limited.pop());
  }

  private static void pushTransfers(TestSupport t) {
    ArrayStack<Integer> source = ArrayStack.of(new Integer[] {4, 5, 6}, 3);
    ArrayStack<Number> dest = ArrayStack.of(new Number[] {1, 2, 3}, 5);
    dest.pushAll(source);
    t.check("pushAll partial destination", "Stack: 1 2 3 6 5", dest.toString());
    t.check("pushAll drains overflowing source", 0, source.getStackSize());
    t.check("pushAll drained source contents", "Stack:", source.toString());
    t.check("pushAll drained source pop", null, source.pop());
    t.check("pushAll destination size", 5, dest.getStackSize());
    source.push(9);
    dest.pushAll(source);
    t.check("pushAll already-full destination", "Stack: 1 2 3 6 5", dest.toString());
    t.check("pushAll full target still drains source", 0, source.getStackSize());
    ArrayStack<Object> zero = new ArrayStack<>(0);
    source.push(10);
    zero.pushAll(source);
    t.check("pushAll zero target stays empty", 0, zero.getStackSize());
    t.check("pushAll zero target drains source", 0, source.getStackSize());
    dest.pushAll(source);
    t.check("pushAll empty source preserves target", "Stack: 1 2 3 6 5", dest.toString());
    ArrayStack<Number> roomy = new ArrayStack<>(4);
    source.push(11);
    source.push(12);
    roomy.pushAll(source);
    Number top = roomy.pop();
    t.check("pushAll top is last transferred", 11, top);
    t.check("pushAll remaining top", 12, roomy.pop());
  }

  private static void popTransfers(TestSupport t) {
    ArrayStack<Integer> source = ArrayStack.of(new Integer[] {4, 5, 6}, 3);
    ArrayStack<Number> dest = ArrayStack.of(new Number[] {1, 2, 3}, 5);
    source.popAll(dest);
    t.check("popAll partial destination", "Stack: 1 2 3 6 5", dest.toString());
    t.check("popAll drains overflowing source", 0, source.getStackSize());
    t.check("popAll drained source contents", "Stack:", source.toString());
    t.check("popAll destination size", 5, dest.getStackSize());
    source.push(9);
    source.popAll(dest);
    t.check("popAll already-full destination", "Stack: 1 2 3 6 5", dest.toString());
    t.check("popAll full target still drains source", 0, source.getStackSize());
    ArrayStack<Object> zero = new ArrayStack<>(0);
    source.push(10);
    source.popAll(zero);
    t.check("popAll zero target stays empty", 0, zero.getStackSize());
    t.check("popAll zero target drains source", 0, source.getStackSize());
    source.popAll(dest);
    t.check("popAll empty source preserves target", "Stack: 1 2 3 6 5", dest.toString());
    ArrayStack<Object> roomy = new ArrayStack<>(4);
    source.push(11);
    source.push(12);
    source.popAll(roomy);
    t.check("popAll into initially empty target", "Stack: 12 11", roomy.toString());
    t.check("popAll into empty target drains source", 0, source.getStackSize());
    t.check("popAll top is last transferred", 11, roomy.pop());
    t.check("popAll remaining top", 12, roomy.pop());
  }

  public static void main(String[] args) {
    TestSupport t = new TestSupport();
    basics(t);
    pushTransfers(t);
    popTransfers(t);
    t.finish();
  }
}
