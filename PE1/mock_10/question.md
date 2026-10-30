# Mock 10 — Campus Collection Counter

**90 minutes total · two tasks · 20 marks · original practice paper**

This is an independent CS2030S-style mock, not an official examination paper.

A campus counter records parcels awaiting collection. Staff register arrivals, check deadlines and approve extensions. Later, the counter introduces new parcel policies and collection transactions.

Your challenge is to keep the counter usable as its requirements change. The public client calls are specified; the supporting classes, relationships and fields are yours to decide. No solution is included.

## Start here

1. Work in this folder using Java 17 or later and Bash.
2. Read the shared rules below, then [Task 1](task1.md).
3. Save Task 1 with `bash submit.sh task1` before starting [Task 2](task2.md). Continue editing the root Java files for Task 2.
4. At 90 minutes, save your current attempt with `bash submit.sh task2`, even if incomplete.

Suggested allocation: **8 minutes reading/planning, 32 minutes Task 1, 40 minutes Task 2, 10 minutes final checks**. These are checkpoints within one timer, not separate time limits. You may read both tasks at the start.

## Shared rules

- Use only `public` and `private` access in your own declarations. Keep fields private. Use a separate file for each top-level class or interface.
- Choose the supporting types yourself. Keep parcel decisions with the objects responsible for them. The desk should be able to perform an operation on different parcel kinds through a common contract.
- Do not select parcel behaviour with `instanceof`, class-name checks, downcasts, reflection, or a stored kind flag followed by conditionals. Ordinary conditions on state, such as whether a parcel is overdue, are allowed. Registration methods may construct the appropriate kind of parcel.
- Use the supplied `Seq<T>` wherever you need to store a collection of objects. Do not modify it. Do not use other arrays, collection classes, streams, or import statements in your implementation. The supplied `main(String[] args)` is allowed. `String` operations and `StringBuilder` are allowed.
- Do not use raw types, explicit casts or `@SuppressWarnings` in your own implementation. The supplied `Seq.java` is exempt from this restriction.
- Use the default package. Do not change supplied test files, scripts or `test.jar`. You may edit `ParcelDesk.java`, `Playground.java`, and add implementation files beside them. The restrictions on implementation do not apply to supplied test infrastructure.
- All specified desk methods **return their result and print nothing**. Constructors print nothing. Only client code, including `Playground`, prints results.
- You may assume capacity is positive, there is space for every registration, indices refer to existing registrations, names and locker codes are non-null and non-empty, and arithmetic fits in `int`. There are no tests for invalid arguments or exceeding capacity.
- A capacity limits the **total number of registrations over the desk's lifetime**. It is not a limit on currently waiting parcels. Every desk manages its own independent records.
- Calls do not advance time. The initial number of days changes only after a successful extension. Rejected operations and queries do not change any state.

`Seq<T>` provides `new Seq<T>(capacity)`, `set(int index, T value)`, and `get(int index)`. Initially each position contains `null`. It has no size-query method. How you use this support class is part of the exercise.

## Marks and design review

| Task | Requirement | Marks | Test group |
|---|---|---:|---|
| 1 | Q1: registration | 3 | Test1 |
| 1 | Q2: listings | 4 | Test2 |
| 1 | Q3: extensions and fees | 3 | Test3 |
| 2 | Q4: additional parcel policies | 3 | Test4 |
| 2 | Q5: collection and accounting | 4 | Test5 |
| 2 | Q6: combined behaviour | 3 | Test6 |
| | **Total** | **20** | |

These are practice marking weights, not a claim about your course's marking scheme. Partial credit is available within each row; failing one assertion does not erase all marks. Printed assertion counts are not marks. The tests cover behaviour, not every design rule.

A later review may deduct up to **2 marks total for restriction/style violations** and up to **2 marks total for design weaknesses** from the behaviour score, with a minimum final score of zero. Design review considers encapsulation, meaningful polymorphism, substitutability, and whether new policies caused repeated edits to the desk's algorithms. Keep the Task 1 snapshot so the adaptation can be reviewed.

## Testing and saving

Compile and run individual groups from the mock folder:

```sh
javac -Xlint:unchecked -Xlint:rawtypes *.java
java -cp test.jar:. Test1
java -cp test.jar:. Test2
java -cp test.jar:. Test3
```

After implementing Task 2, also run `Test4`, `Test5` and `Test6` in the same way. Recompile after each edit before running a test directly.

Or run the task scripts, which compile into a fresh temporary directory automatically:

```sh
bash test.sh task1
bash submit.sh task1
# Continue editing the root Java files for Task 2.
bash test.sh task2
bash submit.sh task2
```

`test.sh task1` runs Tests 1–3. `test.sh task2` runs Tests 1–6, including Task 1 regression checks. It stops at the first failing group and exits nonzero on compilation or test failure. Each group reports its checks; unexpected exceptions also count as a failed run. No JShell setup is needed.

`submit.sh` first reports whether the task checks passed, then saves all root Java source files in `task1/` or `task2/`. It still saves an incomplete attempt if checks fail. A previous snapshot is moved to a uniquely named backup folder. A successful save is not a claim that the tests passed. This command does **not** upload to GitHub; commit and push your work separately.

The Java test sources are in `tests/`. Their compiled versions are in the Java 17-compatible `test.jar`. Keep this folder layout: `javac *.java` should compile your root source files without pulling in the later-task tests. The starter API compiles, but its TODO methods deliberately fail when called until you implement them.
