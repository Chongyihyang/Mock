# Past-Year PE1: ArrayStack

Adapted from PE1, AY2021/22 Semester 2, using the supplied `py1-stack-sample.pdf`.

**Suggested practice time: 75 minutes** (10 minutes planning, 25 minutes Part 1, 25 minutes Part 2, 15 minutes testing). This is a practice estimate; the supplied sample does not state an exam duration.

This edition removes all supplied solution listings and retains all sample interactions and expected outputs. Printed line numbers and line wrapping have been cleaned up. Course-server setup instructions are replaced with the local instructions below. These are newly written tests, not the original course test files. No solution or implementation skeleton is included.

## Rules

- Implement the stack using an array.
- Do not use `java.util.Stack` or `java.util.ArrayList`.
- Use only public/private access, following your course restriction. Interface methods are public.
- Use generic types without raw types. Where an unchecked warning is unavoidable and you can justify type safety, the paper permits a narrowly scoped `@SuppressWarnings("unchecked")`.
- Use the default package: put your Java files beside the test files, without package declarations.
- You may introduce additional classes/interfaces if needed. Internal fields and algorithms are yours to design.

## Part 1: A generic stack

A stack is first-in-last-out (FILO), also called last-in-first-out (LIFO). Items are pushed onto the top and popped from the top.

Create the generic interface `Stack<T>` with these operations:

| Method | Parameters | Return type |
|---|---|---|
| `pop` | None | `T` |
| `push` | One item of type `T` | `void` |
| `getStackSize` | None | `int` |

Create `ArrayStack<T>`, which implements `Stack<T>` using an array. Its public constructor takes one `int`: the maximum depth (capacity).

- `push` adds an item to the top. When full, disregard the new item; existing items are unchanged.
- `pop` removes and returns the top item. When empty, return `null`.
- `getStackSize` returns the current number of items, not the capacity.
- `toString` displays `Stack:` followed by items from bottom to top, each preceded by one space, with no trailing space.

### Sample interactions

```text
jshell> Stack<Integer> st = new ArrayStack<>(3);
st ==> Stack:
jshell> st.push(1);
jshell> st;
st ==> Stack: 1
jshell> st.push(1);
jshell> st;
st ==> Stack: 1 1
jshell> st.push(2);
jshell> st;
st ==> Stack: 1 1 2
jshell> st.getStackSize();
$.. ==> 3
jshell> st.push(3);
jshell> st;
st ==> Stack: 1 1 2
jshell> st.pop();
$.. ==> 2
jshell> st;
st ==> Stack: 1 1
jshell> st.getStackSize();
$.. ==> 2
jshell> st.pop();
$.. ==> 1
jshell> st
st ==> Stack: 1
jshell> st.getStackSize();
$.. ==> 1
jshell> st.pop();
$.. ==> 1
jshell> st
st ==> Stack:
jshell> st.pop();
$.. ==> null
jshell> st
st ==> Stack:
jshell> st.pop();
$.. ==> null
jshell> st
st ==> Stack:
jshell> st.push(2);
jshell> st;
st ==> Stack: 2
jshell> Stack<String> st2 = new ArrayStack<>(10);
st2 ==> Stack:
jshell> st2.push("Hello");
jshell> st2;
st2 ==> Stack: Hello
jshell> st2.push("World");
jshell> st2;
st2 ==> Stack: Hello World
jshell> st2.pop();
$.. ==> "World"
jshell> st2.pop();
$.. ==> "Hello"
```

## Part 2: Factory and bulk transfers

Add a static generic factory method named `of`. It takes an array of items followed by an `int` capacity, and returns an `ArrayStack` with the items pushed in array order. If the array is longer than the capacity, retain only its first capacity-many items. Keep the original public constructor available.

Add these two instance operations:

- `destination.pushAll(source)`: repeatedly pop an item from `source` and push it onto `destination`, until `source` is empty.
- `source.popAll(destination)`: repeatedly pop an item from `source` and push it onto `destination`, until `source` is empty.

Both operations return nothing. If the destination is full, the popped items are lost. **The source must still be emptied.** Design the generic signatures so the mixed-type calls shown below compile safely.

### Sample interactions

This is one continuous sequence; later statements reuse the earlier stacks.

```text
jshell> ArrayStack.of(new Integer[] {1, 2, 3}, 10);
$.. ==> Stack: 1 2 3
jshell> ArrayStack.of(new Object[] {1, "foo", "bar"}, 10);
$.. ==> Stack: 1 foo bar
jshell> ArrayStack<Integer> as0 = ArrayStack.of(new Integer[] {1, 2, 3, 4}, 2);
as0 ==> Stack: 1 2
jshell> ArrayStack<Integer> as1 = ArrayStack.of(new Integer[] {4, 5, 6}, 10);
as1 ==> Stack: 4 5 6
jshell> ArrayStack<Integer> as2 = ArrayStack.of(new Integer[] {1, 2, 3}, 10);
as2 ==> Stack: 1 2 3
jshell> as2.pushAll(as1);
jshell> as2;
as2 ==> Stack: 1 2 3 6 5 4
jshell> as1;
as1 ==> Stack:
jshell> as1 = ArrayStack.of(new Integer[] {4, 5, 6}, 10);
as1 ==> Stack: 4 5 6
jshell> ArrayStack<Integer> as3 = ArrayStack.of(new Integer[] {1, 2, 3}, 5);
as3 ==> Stack: 1 2 3
jshell> as3.pushAll(as1);
jshell> as3;
as3 ==> Stack: 1 2 3 6 5
jshell> ArrayStack<Number> asn = new ArrayStack<>(10);
asn ==> Stack:
jshell> asn.pushAll(as2);
jshell> asn
asn ==> Stack: 4 5 6 3 2 1
jshell> ArrayStack<String> as4 = ArrayStack.of(new String[] {"d", "e", "f"}, 10);
as4 ==> Stack: d e f
jshell> ArrayStack<String> as5 = ArrayStack.of(new String[] {"a", "b", "c"}, 10);
as5 ==> Stack: a b c
jshell> as4.popAll(as5);
jshell> as5;
as5 ==> Stack: a b c f e d
jshell> as4 = ArrayStack.of(new String[] {"d", "e", "f"}, 10);
as4 ==> Stack: d e f
jshell> ArrayStack<String> as6 = ArrayStack.of(new String[] {"a", "b", "c"}, 5);
as6 ==> Stack: a b c
jshell> as4.popAll(as6);
jshell> as6;
as6 ==> Stack: a b c f e
jshell> ArrayStack<Integer> as7 = ArrayStack.of(new Integer[] {7, 8, 9}, 5);
as7 ==> Stack: 7 8 9
jshell> as7.popAll(asn);
jshell> asn;
asn ==> Stack: 4 5 6 3 2 1 9 8 7
```

## Testing

Create `Stack.java` and `ArrayStack.java` yourself. The supplied test files contain client calls and expected results only.

| File | Coverage |
|---|---|
| `TestSupport.java` | Shared assertion/reporting helper |
| `Test1.java` | Every Part 1 sample result |
| `Test2.java` | Every Part 2 sample result, in the same sequence |
| `Test3.java` | Additional checks derived from the written requirements |
| `test.sh` | Compiles and runs a selected stage or all stages |

**Part 1 can be compiled and tested before any Part 2 methods exist.** Do not compile `*.java` at that point, since that would include the later tests.

From inside this directory, run:

```sh
sh test.sh 1
```

After implementing Part 2:

```sh
sh test.sh 2
sh test.sh 3
sh test.sh all
```

Without a shell script (including Windows terminals), run the following commands for Part 1:

```sh
javac -Xlint:rawtypes -Xlint:unchecked -Werror -d out Stack.java ArrayStack.java TestSupport.java Test1.java
java -cp out Test1
```

For another group, replace `Test1.java` and `Test1` with `Test2.java`/`Test2` or `Test3.java`/`Test3`. Use a JDK with `javac` and `java` available; Java 17 is suitable. No JUnit or `-ea` is required.

A complete successful run reports `24 passed; 0 failed.` for Test1, `20 passed; 0 failed.` for Test2, and `40 passed; 0 failed.` for Test3 (84 checks total).

Tests report each check and end with a pass/fail total. Any failed assertion or unexpected exception causes a nonzero exit status. The script stops on a compilation failure or failed group. The compiler's `-Werror` option makes raw/unchecked warnings fail the build; the narrowly scoped, justified suppression permitted above is still allowed.

### Additional-test scope

`Test3` checks empty and zero-capacity stacks, reuse after popping, factory capacity/order, transfers into full or partially full destinations, emptied sources, and safe transfers from narrower element types into wider ones. Tests use nonnegative capacities, non-null arrays/elements, and distinct source/destination stacks. Negative capacities, null inputs/elements, and transferring a stack into itself are not specified by this practice edition and are not assessed.

The written transfer rules are authoritative. The tests follow those rules, including discarding excess transferred items and emptying the source. No algorithm from the PDF's removed answer listings is used as an answer key.

Passing these tests does not establish the quality of your design or compliance with the array/public-private restrictions; those need code review. Keep generated `.class` files and the `out` directory out of your submission.
