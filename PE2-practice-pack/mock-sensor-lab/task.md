# Mock Practice PE 2 — Sensor Lab
CS2030S Programming Methodology II

Time Allowed: **90 minutes**  
Total: **15 marks**

---

## INSTRUCTIONS TO CANDIDATES

1. Marks for each question are indicated below. Style and design form part of the assessment.
2. This is a closed-book assessment. You may refer to one double-sided A4-size paper and the supplied API references.
3. Edit `Series.java`, `Monitor.java`, and `Analysis.java`. Keep your answers directly in the home directory.
4. Write your student number in the `@author` tag of each file you edit.
5. Do not add imports. You may remove unused imports.
6. Use JDK 17 or later. Compile with:

   ```shell
   javac -Xlint:unchecked -Xlint:rawtypes *.java
   ```

7. Run the tests with:

   ```shell
   java -cp test.jar:. Test1
   java -cp test.jar:. Test2
   java -cp test.jar:. Test3
   ```

   Recompile after changing your Java files.

---

## Preliminary

Your home directory contains:

- `Series.java`
- `Monitor.java`
- `Analysis.java`
- `cs2030s/fp/` — the supplied package source files
- `pristine/` — clean copies of the three question files
- `task.md` — this paper
- `test.jar` — compiled tests for Questions 1–3
- `Maybe.md` and `Stream.md` — abridged API references

## Provided Classes

The `cs2030s.fp` package contains `BooleanCondition`, `Combiner`, `Consumer`, `InfiniteList`, `Lazy`, `Maybe`, `Producer`, and `Transformer`. Keep the package source files unchanged.

This paper uses sequential Java streams for sequence processing. `List` represents stored data and returned results. Optional results have the supplied `Maybe` type, and declared functional parameters use the supplied `cs2030s.fp` interfaces.

## Constraints

Your completed answers must satisfy all of the following:

- **Every method body must contain exactly one `return` statement and nothing else.**
- Do not declare local variables. Lambda parameters are allowed; declarations inside lambda bodies are not.
- Do not use loops, `if`, `switch`, conditional expressions (`?:`), or recursion.
- Do not use block-bodied lambdas such as `x -> { ... }`.
- Do not use arrays, `InfiniteList`, `Optional`, or declare types from `java.util.function`.
- Do not use parallel streams.
- Do not assign to fields or mutate existing objects or collections in methods. Assignment, compound assignment, increment, and decrement expressions inside a return expression or lambda are also prohibited.
- Do not use casts, raw types, `@SuppressWarnings`, explicit null values, or exception-based control flow.
- Do not change any given method's name, parameter types, or return type.
- Do not add fields, constructors, methods, classes, anonymous classes, or initialiser blocks. This also prohibits private helper methods.
- Do not add imports or use fully qualified names to access prohibited APIs.

The single-return rule applies to methods, not constructors. Constructors may initialise fields. The constructor and declaration modifiers in `Series` may be revised as part of Question 1. Keep the supplied constructor, field, accessors, and `toString` in `Monitor` unchanged.

The imperative starter methods are code to rewrite. The restrictions apply to your completed versions. The supplied package implementations are not subject to the answer restrictions.

---

## Question 1 [5 marks]

Rewrite `Series` as an **immutable** class.

A series has a name and an ordered list of integer readings. The two fields are `name`, of type `String`, and `values`, of type `List<Integer>`. Its constructor is:

```java
public Series(String name, List<Integer> values)
```

Names, lists, and list entries are non-null. Readings may be negative, zero, or positive. Lists may be empty and may contain repeated values. All arithmetic required in this paper fits in an `int`.

Implement the following behaviour while retaining the supplied public interfaces:

| Member | Required behaviour |
| --- | --- |
| Constructor | Create a series with the given name and the readings in the supplied order. Subsequent changes to the caller's list must not affect the series. |
| `String getName()` | Return the series name. |
| `List<Integer> getValues()` | Return the readings in order. The returned list must not provide a way to modify the series. |
| `Series append(int value)` | Return a new series with the same name and `value` appended after all existing readings. |
| `Series shift(int offset)` | Return a new series with the same name and `offset` added to every reading. Preserve the order. |
| `String toString()` | Return exactly the name, followed by `": "`, followed by the readings in ordinary list notation. |

`append` and `shift` leave the original series unchanged. Each returns a new series object, including `shift(0)` and operations on an empty series. The constructor must accept both mutable and unmodifiable lists.

### Sample Run

```text
jshell> List<Integer> input = new ArrayList<>(List.of(4, 7, 3, 6))
input ==> [4, 7, 3, 6]

jshell> Series a = new Series("A", input)
a ==> A: [4, 7, 3, 6]

jshell> input.set(0, 99)
$.. ==> 4

jshell> a
a ==> A: [4, 7, 3, 6]

jshell> a.append(8)
$.. ==> A: [4, 7, 3, 6, 8]

jshell> a.shift(-2)
$.. ==> A: [2, 5, 1, 4]

jshell> a
a ==> A: [4, 7, 3, 6]

jshell> a.shift(0) == a
$.. ==> false

jshell> new Series("empty", List.of()).append(5)
$.. ==> empty: [5]
```

Run Question 1's tests with:

```shell
java -cp test.jar:. Test1
```

---

## Question 2 [5 marks]

Rewrite `exceeding`, `truncate`, and `flatten` in `Monitor.java` in functional style. Each method body must consist of a single return statement.

A monitor stores an ordered list of non-null series. Series names need not be unique, and the same series object may occur repeatedly. The supplied constructor preserves the input order and isolates the monitor from later changes to the caller's list.

### `exceeding` [2 marks]

```java
public List<String> exceeding(int threshold)
```

Return the names of the series having **at least one reading strictly greater than** `threshold`.

Produce one name per qualifying series occurrence, regardless of how many readings exceed the threshold. Preserve monitor order and repeated names. An empty series does not qualify. An empty monitor produces an empty list.

### `truncate` [2 marks]

```java
public Monitor truncate(int limit)
```

For a non-negative `limit`, return a new monitor in the same series order. Every series in the result must be a new series object, with the original name and at most its first `limit` readings.

Keep every series occurrence, including those with no retained readings. A limit larger than a series's length retains all of its readings. A zero limit produces empty series. The original monitor and its series remain unchanged.

### `flatten` [1 mark]

```java
public List<Integer> flatten()
```

Return all readings in monitor order and, within each series, reading order. Preserve duplicate readings and repeated series occurrences. Empty series contribute no readings. An empty monitor produces an empty list.

All three methods leave the original monitor and its series unchanged. Repeated calls on the same monitor must continue to work and produce the specified results.

The supplied imperative methods already produce the required results once Question 1 is complete. This question assesses their functional rewrite.

### Sample Run

```text
jshell> Series a = new Series("A", List.of(4, 7, 3, 6))
a ==> A: [4, 7, 3, 6]
jshell> Series b = new Series("B", List.of(-2, 8))
b ==> B: [-2, 8]
jshell> Series empty = new Series("empty", List.of())
empty ==> empty: []

jshell> Monitor m = new Monitor(List.of(a, empty, b, a))
m ==> [A: [4, 7, 3, 6]][empty: []][B: [-2, 8]][A: [4, 7, 3, 6]]

jshell> m.exceeding(6)
$.. ==> [A, B, A]

jshell> m.exceeding(7)
$.. ==> [B]

jshell> m.exceeding(8)
$.. ==> []

jshell> m.truncate(2)
$.. ==> [A: [4, 7]][empty: []][B: [-2, 8]][A: [4, 7]]

jshell> m.truncate(0)
$.. ==> [A: []][empty: []][B: []][A: []]

jshell> m.flatten()
$.. ==> [4, 7, 3, 6, -2, 8, 4, 7, 3, 6]

jshell> m
m ==> [A: [4, 7, 3, 6]][empty: []][B: [-2, 8]][A: [4, 7, 3, 6]]
```

Run Question 2's tests with:

```shell
java -cp test.jar:. Test2
```

---

## Question 3 [5 marks]

Implement the three static methods in `Analysis.java`. Each method body must consist of a single return statement.

### `firstDrop` [1 mark]

| Item | Description |
| --- | --- |
| Parameter | `Series series` |
| Return type | `Maybe<Integer>` |
| Result | The smallest index `i` at which a reading is strictly less than the immediately preceding reading, wrapped in `Maybe`. Return `Maybe.none()` if there is no such index. |

Indices are zero-based. The returned index identifies the later reading in the pair. Equal consecutive readings do not count as a drop. Empty and one-element series have no drop.

### `movingTotals` [2 marks]

| Item | Description |
| --- | --- |
| Parameters | `Series series`, `int width` |
| Return type | `List<Integer>` |
| Result | The total of each consecutive group of exactly `width` readings, with groups starting at indices 0, 1, 2, and so on. |

`width` is positive. Include only complete groups. Preserve the order of their starting indices. If there are fewer than `width` readings, return an empty list. The groups may overlap.

For example, with readings `[4, 7, 3, 6]` and width 2, the groups are `[4, 7]`, `[7, 3]`, and `[3, 6]`, giving `[11, 10, 9]`.

### `combine` [2 marks]

```java
public static Maybe<Series> combine(Series first, Series second,
    Combiner<? super Integer, ? super Integer, ? extends Integer> operation)
```

If the two series have different lengths, return `Maybe.none()` and do not invoke `operation`.

Otherwise, return a new series wrapped in `Maybe`. Its name is the first name, followed by `"+"`, followed by the second name. Its reading at index `i` is the result of applying `operation` to the first series's reading at `i` and the second series's reading at `i`, in that argument order.

Invoke the operation exactly once per corresponding pair, in index order. It returns a non-null integer. Two empty series successfully produce a new empty series, without invoking the operation.

All three methods leave their inputs unchanged and must support repeated calls.

### Sample Run

```text
jshell> Series a = new Series("A", List.of(4, 7, 3, 6))
a ==> A: [4, 7, 3, 6]
jshell> Series b = new Series("B", List.of(1, 2, 5, 4))
b ==> B: [1, 2, 5, 4]

jshell> Analysis.firstDrop(a)
$.. ==> [2]

jshell> Analysis.firstDrop(new Series("C", List.of(1, 1, 3)))
$.. ==> []

jshell> Analysis.movingTotals(a, 2)
$.. ==> [11, 10, 9]

jshell> Analysis.movingTotals(a, 3)
$.. ==> [14, 16]

jshell> Analysis.movingTotals(a, 1)
$.. ==> [4, 7, 3, 6]

jshell> Analysis.movingTotals(a, 5)
$.. ==> []

jshell> Analysis.combine(a, b, (x, y) -> x - y)
$.. ==> [A+B: [3, 5, -2, 2]]

jshell> Analysis.combine(a, new Series("short", List.of(1)), (x, y) -> x + y)
$.. ==> []

jshell> Analysis.combine(new Series("left", List.of()),
   ...>     new Series("right", List.of()), (x, y) -> x + y)
$.. ==> [left+right: []]

jshell> a
a ==> A: [4, 7, 3, 6]
```

Run Question 3's tests with:

```shell
java -cp test.jar:. Test3
```
