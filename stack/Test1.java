/** All Part 1 sample results, in their original order. */
public final class Test1 {
  private Test1() {
  }

  public static void main(String[] args) {
    TestSupport t = new TestSupport();
    Stack<Integer> st = new ArrayStack<>(3);
    t.check("new integer stack", "Stack:", st.toString());
    st.push(1);
    t.check("first push", "Stack: 1", st.toString());
    st.push(1);
    t.check("duplicate push", "Stack: 1 1", st.toString());
    st.push(2);
    t.check("third push", "Stack: 1 1 2", st.toString());
    t.check("size at capacity", 3, st.getStackSize());
    st.push(3);
    t.check("full push leaves contents unchanged", "Stack: 1 1 2", st.toString());
    t.check("first pop", 2, st.pop());
    t.check("contents after first pop", "Stack: 1 1", st.toString());
    t.check("size after first pop", 2, st.getStackSize());
    t.check("second pop", 1, st.pop());
    t.check("contents after second pop", "Stack: 1", st.toString());
    t.check("size after second pop", 1, st.getStackSize());
    t.check("third pop", 1, st.pop());
    t.check("empty after third pop", "Stack:", st.toString());
    t.check("empty pop", null, st.pop());
    t.check("still empty", "Stack:", st.toString());
    t.check("repeated empty pop", null, st.pop());
    t.check("still empty after repeated pop", "Stack:", st.toString());
    st.push(2);
    t.check("reuse after emptying", "Stack: 2", st.toString());
    Stack<String> st2 = new ArrayStack<>(10);
    t.check("new string stack", "Stack:", st2.toString());
    st2.push("Hello");
    t.check("first string", "Stack: Hello", st2.toString());
    st2.push("World");
    t.check("second string", "Stack: Hello World", st2.toString());
    t.check("pop World", "World", st2.pop());
    t.check("pop Hello", "Hello", st2.pop());
    t.finish();
  }
}
