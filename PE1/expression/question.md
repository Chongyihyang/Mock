# Past-Year PE1: Expression

Adapted from PE1, AY2020/21 Semester 2, using the supplied `py1-expression-sample.pdf`.

**Suggested practice time: 75 minutes**: 10 minutes planning, 20 minutes Part 1, 30 minutes Part 2, 15 minutes testing. This is a practice estimate; the supplied sample does not state a time limit.

All supplied solution listings are removed. All sample interactions and expected results are preserved below, with printed line numbers and page wrapping removed. The original course-server instructions are replaced by local testing commands. No solutions or starter implementations are included. These tests are newly written, not the original course tests.

## Background

An expression is an entity that can be evaluated into a value. There are two kinds here:

- An operand, which itself is a value.
- A binary operation, which takes two expressions and produces a value.

As general mathematical examples, `3`, `3 + 2`, and `(3 + 2) + 3` are expressions. **The Java operations required below are specifically integer multiplication, string concatenation, and Boolean XOR; integer addition is not required.**

An operand need not be an integer: it can hold a value of any reference type. Evaluating an expression may produce values of different reference types.

Use only public/private access, following your course restriction, and the default package. You may introduce additional parent classes, interfaces, or other classes as appropriate. The tests do not prescribe the name or kind of any shared expression abstraction or the names of concrete operation subclasses. This paper does not impose a ban on type tests or casts; do not import restrictions from a different mock.

## Part 1: Operand and an exception

### Operand

Create `Operand`, which encapsulates a reference to a value of any reference type. Its `eval()` method returns that value.

```text
jshell> new Operand(5).eval()
$.. ==> 5
jshell> new Operand("string").eval()
$.. ==> "string"
jshell> new Operand(true).eval()
$.. ==> true
```

### InvalidOperandException

Create an **unchecked** exception named `InvalidOperandException`. Its constructor takes a `char` identifying the operator. The message must match the sample exactly:

```text
jshell> InvalidOperandException e = new InvalidOperandException('!')
jshell> e.getMessage();
$.. ==> "ERROR: Invalid operand for operator !"
```

Unchecked exceptions extend `java.lang.RuntimeException` (directly or indirectly). `RuntimeException(String message)` accepts a detail message, and `getMessage()` retrieves it.

## Part 2: Operation

Create an **abstract** class `Operation` with two private fields corresponding to its two input expressions. Add a static factory method `of`, taking an operator character followed by two expressions, that returns an appropriate concrete operation subclass.

| Character | Required behaviour |
|---|---|
| `*` | Multiply two integer values (`Integer`) |
| `+` | Concatenate two strings (`String`), left followed by right |
| `^` | XOR two Boolean values (`Boolean`) |
| Any other character | Return `null` from the factory |

Java's Boolean XOR operator is `^`.

Each operation provides `eval()`. Inputs may themselves be operations, as the samples show. Supported operations are constructed before evaluation; wrong operand types must be reported when `eval()` is called, not during the factory call.

### Successful and unsupported-operation samples

```text
jshell> Operation o = Operation.of('*', new Operand(2), new Operand(3));
jshell> o.eval()
$.. ==> 6
jshell> Operation o = Operation.of('+', new Operand("hello"), new Operand("world"));
jshell> o.eval()
$.. ==> "helloworld"
jshell> Operation o = Operation.of('^', new Operand(true), new Operand(false));
jshell> o.eval()
$.. ==> true
jshell> Operation.of('!', new Operand(2), new Operand(3));
$.. ==> null
jshell> Operation o1 = Operation.of('*', new Operand(2), new Operand(3));
jshell> Operation o = Operation.of('*', o1, new Operand(4));
jshell> o.eval()
$.. ==> 24
jshell> Operation o2 = Operation.of('*', new Operand(2), new Operand(4));
jshell> Operation o = Operation.of('*', o1, o2);
jshell> o.eval()
$.. ==> 48
```

### Invalid operands

If evaluated operands have incorrect types for an operator, `eval()` must throw `InvalidOperandException` for that operator. Do not coerce values between types.

A failure evaluating a nested input must propagate with its original operator message, as demonstrated by the final sample. The problem does not specify precedence when both input expressions fail; the tests do not assess that case.

```text
jshell> Operation o = Operation.of('*', new Operand("1"), new Operand(3));
jshell> try {
...>   o.eval();
...> } catch (InvalidOperandException e) {
...>   System.out.println(e.getMessage());
...> }
ERROR: Invalid operand for operator *
jshell> Operation o = Operation.of('+', new Operand(1), new Operand(4));
jshell> try {
...>   o.eval();
...> } catch (InvalidOperandException e) {
...>   System.out.println(e.getMessage());
...> }
ERROR: Invalid operand for operator +
jshell> Operation o = Operation.of('^', new Operand(false), new Operand(3));
jshell> try {
...>   o.eval();
...> } catch (InvalidOperandException e) {
...>   System.out.println(e.getMessage());
...> }
ERROR: Invalid operand for operator ^
jshell> Operation o1 = Operation.of('*', new Operand(1), new Operand(3));
jshell> Operation o2 = Operation.of('^', new Operand(false), new Operand(false));
jshell> Operation o = Operation.of('+', o1, o2);
jshell> try {
...>   o.eval();
...> } catch (InvalidOperandException e) {
...>   System.out.println(e.getMessage());
...> }
ERROR: Invalid operand for operator +
jshell> Operation o1 = Operation.of('*', new Operand(1), new Operand("3"));
jshell> Operation o2 = Operation.of('^', new Operand(false), new Operand(false));
jshell> Operation o = Operation.of('+', o1, o2);
jshell> try {
...>   o.eval();
...> } catch (InvalidOperandException e) {
...>   System.out.println(e.getMessage());
...> }
ERROR: Invalid operand for operator *
```

## Testing

Place your Java source files beside the supplied tests, without package declarations. Do not name an implementation file `Test1.java`, `Test2.java`, `Test3.java`, or `TestSupport.java`.

| File | Purpose |
|---|---|
| `Test1.java` | All 4 Part 1 sample results and a compile-time check that the exception is unchecked |
| `Test2.java` | All 11 Part 2 sample results, including exact failure messages |
| `Test3.java` | Additional contract checks: types, nested operations, both operand positions, repeat evaluation, XOR cases, and abstract Operation |
| `TestSupport.java` | Small assertion/reporting helper |
| `test.sh` | Compiles only the selected test group and your implementation sources |

Part 1 tests run **without Operation or its subclasses**. You need Operand, InvalidOperandException, and any helpers they depend on.

```sh
sh test.sh 1
```

After completing Part 2:

```sh
sh test.sh 2
sh test.sh 3
sh test.sh all
```

The script runs from any working directory. Implementation files belong directly beside the tests. It excludes the unselected supplied test groups when compiling, so later tests cannot block Part 1. An unfinished implementation file that does not compile can still prevent compilation; create files incrementally and keep the ones you have compilable.

Manual commands for Part 1 (substitute your actual helper filenames if needed):

```sh
javac -Xlint:rawtypes -Xlint:unchecked -Werror -d out Operand.java InvalidOperandException.java TestSupport.java Test1.java
java -cp out Test1
```

The compiler can discover referenced helper sources in the current directory. Once all parts exist, you may compile everything and run the three groups directly:

```sh
javac -Xlint:rawtypes -Xlint:unchecked -Werror -d out *.java
java -cp out Test1
java -cp out Test2
java -cp out Test3
```

A successful complete run reports `4 passed; 0 failed.`, `11 passed; 0 failed.`, and `21 passed; 0 failed.` (36 checks total).

Use a JDK with `javac` and `java` available (verified using Java 17). No JUnit or `-ea` is needed. Raw/unchecked warnings fail the build. Each test prints pass/fail information and a summary; an assertion failure, unexpected exception, or compilation error produces a nonzero exit status. The script stops at the first failing group.

Additional tests use non-null operands and expressions. Null values, null expression references, integer overflow, side-effecting custom expressions, and the order of two simultaneous nested failures are not assessed. Tests are based on the written requirements and examples, not the removed implementations. Public/private access, private representation, and the use of appropriate subclasses still require code review.

Keep generated `.class` files and the `out` directory out of your submission.
