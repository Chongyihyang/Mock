# 05-2324-session1-fruit
Read task1.md. 

These are reconstructed practice files and newly written tests, not official course starter files or official grading tests. No exercise solutions are included. Provided support/helper classes are implemented so you can focus on the assigned work.

## Run
Use JDK 17 or newer from this folder. Start with Test1 and keep earlier tests passing.
```sh
sh test.sh 1
sh test.sh 2
```
Run `sh test.sh` for every test, once all required methods exist. (Point has only Test1.) A failure prints the expected and actual values and returns a nonzero exit status.
You can also use familiar commands:
```sh
javac -Xlint:unchecked -Xlint:rawtypes Test1.java
java Test1
```
After implementing all APIs, `javac -Xlint:unchecked -Xlint:rawtypes *.java` compiles all tests. For packaged exercises, javac finds `cs2030s/fp` sources automatically.
`java -cp test.jar:. Test1` works after compiling your exercise classes; the jar contains ONLY this pack's tests, not solutions. It does not compile your changed code. Prefer test.sh to avoid stale bytecode.

## JShell
For every folder containing cs2030s/fp: compile your `cs2030s/fp/*.java` first, then `jshell --class-path . load.jsh`.
For default-package exercises (after compiling support files, if present): `jshell --class-path . load.jsh` opens sources in dependency order. Reload/restart after edits. Compiled default-package classes cannot simply be imported into JShell.

## Notes
Reconstructed Fruits helpers include Mango, which is used but omitted from the prelude list. Nonempty stall formatting is a leading newline, one "- item\n" per fruit; empty is "". This matches the nested list examples. Empty stalls have expiry rating Integer.MAX_VALUE. Single-return restriction applies to methods from Q3 onward, not constructors. Q7 implements java.util.Comparator (the paper mistakenly calls it Comparable).

`pristine/` preserves editable starters only; do not compile it with your working files. No automatic reset is supplied, to avoid overwriting your work.
Official Checkstyle files were not supplied. `checkstyle.sh` reports this honestly; add your own course jar/config to use it. Runtime tests do not grade style, single-return restrictions, casts, or every invalid generic program. Inspect these manually.

After Test2 compiles, run `sh test-types.sh` for the paper’s expected compilation failures. These tests do not belong in `javac *.java`.
