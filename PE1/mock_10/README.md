# Mock 10 — Campus Collection Counter

**Two tasks, 90 minutes total. Java 17 or later; Bash. No solutions included.**

Start with [question.md](question.md), then [task1.md](task1.md) and [task2.md](task2.md). The cover sheet contains shared restrictions, timing, the marking rubric and command instructions.

The package includes:

- `ParcelDesk.java`: Task 1 public API stubs; choose your own supporting classes and fields.
- `Seq.java`: supplied fixed-capacity sequence; do not modify it.
- `Playground.java`: optional client for your own experiments.
- `tests/Test1.java` through `tests/Test6.java`: readable test cases, including every paper sample.
- `tests/TestSupport.java`: test infrastructure.
- `test.jar`: compiled test classes for Java 17 or later; contains no implementation.
- `test.sh`: compiles your root source files and runs the appropriate test groups.
- `submit.sh`: saves local Java source snapshots for review, preserving any previous snapshot.

From this folder:

```sh
javac -Xlint:unchecked -Xlint:rawtypes *.java
java -cp test.jar:. Test1
bash test.sh task1
bash submit.sh task1
# Continue Task 2 in the root source files.
bash test.sh task2
bash submit.sh task2
```

The untouched starter compiles but fails tests with a TODO exception. Implement the requested behaviour before expecting tests to pass. Task 1 tests do not require Task 2 methods. Test source files stay under `tests/` so future-task APIs do not interfere with `javac *.java`.

To try a short client, put calls inside `Playground.main`, compile as above and run `java Playground`. There is no need to reopen dependency files in JShell.

When finished, commit and push your root implementation and the `task1/` and `task2/` snapshots to GitHub for review. `submit.sh` does not push anything.

Package verification: all 84 checks passed against a separate validation implementation, including every printed sample. Tests 1–3 also passed with the Task 2 desk methods absent. The command and snapshot scripts were exercised, and six deliberate behavioural defects were rejected. Only test classes are included in `test.jar`; no validation implementation is supplied.
