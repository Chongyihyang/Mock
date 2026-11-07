# Mock Practice PE 2 — Calibration Station
CS2030S Programming Methodology II

**Original unofficial practice · Time allowed: 90 minutes · Total: 15 marks**

## INSTRUCTIONS TO CANDIDATES

1. Style, type safety, and compliance with restrictions form part of the assessment. Passing the visible tests alone does not guarantee full marks.
2. Work closed-book with one double-sided A4 sheet and the supplied `API.md` reference.
3. Edit only `Batch.java`, `Calibration.java`, and `Inspection.java`. Write your student number in each `@author` tag.
4. Do not add imports. You may remove unused imports.
5. Compile using JDK 17 or later:
   ```shell
   javac -Xlint:unchecked -Xlint:rawtypes *.java
   ```
6. Run the tests after recompiling:
   ```shell
   java -cp test.jar:. Test1
   java -cp test.jar:. Test2
   java -cp test.jar:. Test3
   ```
   Java test sources are supplied too. To run your compiled test sources directly, use `java Test1`, `java Test2`, or `java Test3`.
7. This paper has three questions, all available from the start. Suggested checkpoints: Q1 by minute 20, Q2 by minute 50, Q3 by minute 85, then a final compile and restriction check.

## Preliminary

The directory contains the three answer files, their clean copies in `pristine/`, `task1.md`, `API.md`, `Checks.java`, `Test1.java`–`Test3.java`, `test.jar`, and the supplied `cs2030s/fp/` package. Keep test and support files unchanged. There are no solutions in this pack.

The starter files compile but contain unimplemented methods. Failing tests before implementation are expected. Test cases run independently within each test program; a failure is reported without suppressing later cases.

## Restrictions

Every completed method body in the three answer files must contain exactly one `return` statement and nothing else. Constructors are exempt; only the `Batch` constructor may be changed.

- No local variable declarations, loops, `if`, `switch`, `?:`, recursion, or block-bodied lambdas.
- No mutation, assignments, increment/decrement, or side effects in your method expressions.
- No arrays, `Optional`, `InfiniteList`, parallel streams, casts, raw types, `@SuppressWarnings`, explicit nulls, or exception-based control flow.
- Use sequential Java streams and the supplied `Maybe`, `Lazy`, and functional interfaces. `List` may store and return data. Do not declare types from `java.util.function`.
- Do not add fields, methods, constructors, classes, or initialiser blocks. Preserve all supplied method signatures.
- Change the `Batch` class/field modifiers as required by Q1. Leave `Calibration`'s field and constructor unchanged.

The restrictions apply to your answers, not the tests or supplied package. Replace the starter `throw` statements with your answers.

## Background

A laboratory records batches of integer measurements. A calibration can turn a raw reading into a usable non-negative reading, or reject it. The laboratory then examines adjacent readings and overlapping groups, sometimes postponing the work until a report is requested.

Names, lists, list elements, and functional arguments are non-null. Names may be empty. Readings may be negative, zero, or positive; duplicates are allowed. All lists are finite. All arithmetic in this paper, including differences and index bounds, fits in `int`. Corrections return non-null integers and do not throw.

## Question 1 — Preserve the measurements [5 marks]

Make `Batch` immutable, with a final class and private final fields. Preserve the existing names and types of its fields.

| Member | Required behaviour |
|---|---|
| Constructor and `getReadings()` [2] | Store the name and readings in order. Later changes to the caller's list must not affect the batch. A returned list must not allow modification of the batch. Accept mutable and unmodifiable input lists. |
| `append(int reading)` [1] | Return a new batch with the same name and one reading added at the end. |
| `shift(int offset)` [2] | Return a new batch with the same name and the offset added to every reading. |

Both operations preserve the original batch and return a new batch object, including a zero shift or an empty batch. Keep `getName()` and `toString()` unchanged.

### Sample run

```text
jshell> List<Integer> source = new ArrayList<>(List.of(2, 3, 7, 4))
source ==> [2, 3, 7, 4]
jshell> Batch a = new Batch("A", source)
a ==> A: [2, 3, 7, 4]
jshell> source.set(0, 99)
$.. ==> 2
jshell> a
a ==> A: [2, 3, 7, 4]
jshell> a.append(0)
$.. ==> A: [2, 3, 7, 4, 0]
jshell> a.shift(-2)
$.. ==> A: [0, 1, 5, 2]
jshell> a.shift(0) == a
$.. ==> false
```

Test: `java -cp test.jar:. Test1`

## Question 2 — Accept the whole batch [5 marks]

A `Calibration` stores a correction supplied through its constructor. Corrections may consume a supertype of `Integer`, as allowed by the given signature.

### (a) `apply(int reading)` [2 marks]

Apply the correction exactly once. If the **corrected value** is non-negative, return that value in `Maybe`. Otherwise return `Maybe.none()`. Zero is valid. The sign of the original reading does not determine success.

### (b) `applyAll(Batch batch)` [3 marks]

Return a new batch wrapped in `Maybe` if every reading can be calibrated. Keep its name, reading order, and duplicates. If any reading fails, return `Maybe.none()` for the entire batch; do not drop failed readings or substitute values.

For a successful batch, invoke the correction exactly once per reading, in order. For a failing batch, you may stop after the first failure or continue processing; any readings you do process must be processed at most once, in original order.

An empty batch succeeds with a new empty batch and invokes no correction. Even an identity correction must produce a new batch. Do not modify the original batch. Separate calls perform separate calibrations.

### Sample run

```text
jshell> Calibration c = new Calibration(x -> x + 1)
c ==> Calibration@########
jshell> c.apply(-1)
$.. ==> [0]
jshell> c.apply(-2)
$.. ==> []
jshell> c.applyAll(new Batch("A", List.of(2, 3, 7, 4)))
$.. ==> [A: [3, 4, 8, 5]]
jshell> c.applyAll(new Batch("B", List.of(2, -2, 4)))
$.. ==> []
jshell> c.applyAll(new Batch("E", List.of()))
$.. ==> [E: []]
```

Test: `java -cp test.jar:. Test2`

## Question 3 — Inspect and defer [5 marks]

### (a) `firstRise(Batch batch, int gap)` [1 mark]

`gap` is positive. Return the smallest index `i` whose reading is **at least `gap` larger than the immediately preceding reading**, wrapped in `Maybe<Integer>`. Indices start at zero; return the index of the later reading, not its value. Return `none` when no such pair exists. Empty and singleton batches have no such pair.

### (b) `windowTotals(Batch batch, int width)` [2 marks]

`width` is positive. Return the sum of every complete consecutive group of exactly `width` readings. Groups start at indices 0, 1, 2, and so on and may overlap. Preserve starting-index order. If the batch is shorter than `width`, return an empty list. This method uses the given readings directly; it performs no calibration.

### (c) `deferredReport(Calibration calibration, Batch batch, int width, Producer<? extends String> fallback)` [2 marks]

Return a `Lazy<String>`. On its first `get()`:

1. Calibrate the whole batch according to Q2(b).
2. If successful, calculate the window totals of the calibrated readings. Return the original batch name, followed by `": "`, followed by the totals in ordinary list notation.
3. If calibration fails, invoke `fallback` and return its string instead. The fallback returns a non-null string, possibly empty.

Construction and calling `toString()` on the unevaluated lazy result must invoke neither the correction nor the fallback. Repeated `get()` calls reuse the cached report and invoke neither again. Separate lazy results cache independently.

An empty batch or a valid batch with no complete window is a **successful report**, not a fallback case. Calibration must still check the readings when `width` is larger than the batch. Leave the input unchanged.

### Sample run

```text
jshell> Batch a = new Batch("A", List.of(2, 3, 7, 4))
a ==> A: [2, 3, 7, 4]
jshell> Inspection.firstRise(a, 4)
$.. ==> [2]
jshell> Inspection.firstRise(a, 5)
$.. ==> []
jshell> Inspection.windowTotals(a, 2)
$.. ==> [5, 10, 11]
jshell> Inspection.windowTotals(a, 4)
$.. ==> [16]
jshell> Inspection.windowTotals(a, 5)
$.. ==> []
jshell> Lazy<String> report = Inspection.deferredReport(new Calibration(x -> x + 1), a, 2, () -> "REJECTED")
report ==> ?
jshell> report.get()
$.. ==> "A: [7, 12, 13]"
jshell> report.get()
$.. ==> "A: [7, 12, 13]"
jshell> Inspection.deferredReport(new Calibration(x -> x), new Batch("B", List.of(-1)), 5, () -> "REJECTED").get()
$.. ==> "REJECTED"
jshell> Inspection.deferredReport(new Calibration(x -> x), new Batch("E", List.of()), 2, () -> "REJECTED").get()
$.. ==> "E: []"
```

Test: `java -cp test.jar:. Test3`

The examples assume the Java and `cs2030s.fp` types are already available in JShell. Use the supplied Java test programs for repeatable checks; the transcripts are behavioural references, not a JShell loading script.
