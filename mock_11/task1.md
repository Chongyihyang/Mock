# Mock PE 11 (Rover Mission) — Task 1
CS2030S Programming Methodology II  
Independent practice paper — not an official NUS assessment

October 2026  
**Time Allowed: 90 minutes**  
**Task 2 is released after 50 minutes, with 40 minutes remaining.**

---

## INSTRUCTIONS TO CANDIDATES

1. Task 1 and Task 2 carry 10 marks each. Marks for each question are indicated below. Style violations and design issues may reduce the score. Passing every test is not a condition for receiving any marks; partial credit is available.
2. Simulate a CLOSED-BOOK assessment. You may refer to one double-sided A4-size paper.
3. Only Java source files directly under your `task1` and `task2` submission directories will be assessed. Work in the mock's main directory, then use `submit.sh` to save each version.
4. Some skeletons are provided. You may add new files as needed. The public driver API is specified, but supporting classes, fields and relationships are yours to design.
5. Put your student number in an `@author` tag in every Java file you create or edit. Do not use your name.
6. Do not add `import` statements. Use only `public` and `private` access in your declarations; fields must be private. Use separate files for top-level classes and interfaces.
7. Do not use raw types, explicit casts or `@SuppressWarnings` in code you add. The supplied array construction inside `Seq` is exempt. Use `Seq` for collections; do not introduce other arrays, collection classes or streams. The supplied `main(String[] args)` is allowed. `String`, `String.format` and `StringBuilder` are allowed.
8. Do not select a job's behaviour by inspecting its class, using `instanceof`, downcasting, reflection, or branching on a stored job-kind flag. Conditions on an object's state are allowed. Registration methods may construct appropriate job objects.
9. Compile with:
   ```shell
   javac -Xlint:unchecked -Xlint:rawtypes *.java
   ```

**IMPORTANT:** Code that does not compile may receive no marks for the questions that depend on it. Keep your program compiling throughout the assessment. Tests check behaviour; restrictions and design are also reviewed. Within this mock's 20 marks, restriction/style deductions are capped at 2 marks and design deductions at 2 marks; the final score cannot be negative. These are practice marking rules.

---

## Preliminary

Extract the whole package and enter `mock_11`. Use Java/JDK 17 or later and Bash. Ensure these files and directories are present:

| Item | Purpose |
|---|---|
| `RoverMission.java` | Driver skeleton to complete |
| `Seq.java` | Sequence to modify in Question 1 |
| `Playground.java` | Optional client for your own experiments |
| `pristine/` | Original copies of the three starter files |
| `task1.md` | This question paper |
| `task2.secret` | Encrypted later question and test package |
| `test.jar`, `tests/` | Runnable tests and their readable Java source |
| `start.sh`, `unlock.sh`, `status.sh` | Session timer and Task 2 release |
| `test.sh`, `submit.sh` | Test and submission commands |
| `support/` | Supplied session utility; do not modify |

**Start the timer before reading the Background and questions:**

```shell
bash start.sh
```

This records a start time. Running it again displays the existing timer; it does not restart it. At minute 50, save your current Task 1 work and reveal Task 2:

```shell
bash submit.sh task1
bash unlock.sh
```

`unlock.sh` refuses release before 50 minutes. Once released, `task2.md` and the later Java test sources appear, and `test.jar` gains the later tests while retaining the earlier ones. Continue editing the main-directory source files. The unlock script does not alter your implementation or submitted Task 1 snapshot. You may check elapsed time with `bash status.sh`. This is an offline practice timer; leave its files and your system clock unchanged.

At minute 90, stop coding and run `bash submit.sh task2`. The timer reports elapsed time but does not terminate your editor. There is no automatic background notification; use your own 90-minute timer with a reminder at minute 50 if needed.

`submit.sh` runs the task's tests and saves your main-directory Java files even if tests fail. Existing snapshots are preserved in uniquely named backup directories. The command saves locally; it does not push to GitHub. Submission time does not control Task 2 release.

**You may add new implementation files as needed.** Do not change tests, scripts or session utilities. Test infrastructure is exempt from the implementation restrictions.

---

## Provided Classes

### `RoverMission`

Complete this driver class. You may add fields and methods and create supporting types. We will be able to compile your implementation with:

```shell
rm -f *.class
javac -Xlint:unchecked -Xlint:rawtypes RoverMission.java
```

For ordinary testing, the `javac *.java` command in the instructions also works. The initial driver compiles but its TODO methods throw exceptions until implemented.

### `Seq<T>`

The supplied sequence has a fixed capacity established by `Seq(int capacity)`. Initially it provides `set(int index, T item)` and `T get(int index)`. Change it only as required by Question 1. Preserve its supplied array allocation and type-safety annotation.

**IMPORTANT:** Use this sequence to store jobs. Replacing it with your own array is a restriction violation, even if all behaviour tests pass.

---

## Background

A rover visits a research site with a limited energy supply. Mission control keeps jobs in registration order and tells the rover which job to execute. Engineers may recharge the rover between executions.

Each job has a non-empty name. Names need not be unique: each registration is a distinct job. Registration indices start at 0 and never change. A mission's capacity bounds its total registrations, including jobs that have finished.

For Task 1, every job is an ordinary scan. A scan requires a positive, fixed number of energy units and needs one successful execution. It is initially pending and becomes done after success. No call advances time or recharges the rover automatically.

The status of a job is:

```text
<name> {next <N> energy} [<state>]
```

`N` is the cost of its next execution. A pending ordinary scan uses its fixed cost; a done scan uses 0. In this task, `state` is `pending` or `done`. Always use `energy`, including for 1. Preserve the name exactly. Angle-bracketed terms are placeholders, not literal text.

All specified methods return their results without printing. Constructors are also silent. The client decides what to print. String messages have no trailing newline unless they are listing strings described in Question 3.

Assume capacities are positive; initial energy is non-negative; scan costs and recharge amounts are positive; names are non-null, non-empty single-line strings; job indices are valid; capacity is never exceeded; and all arithmetic fits in `int`. Invalid arguments and overflow need not be handled.

In sample transcripts, `$..` stands for JShell's generated result variable and `########` stands for an unspecified object identity hash. These are not literal output requirements. Blank output lines are intentional. The runnable Java tests require no JShell setup.

---

## 1. (1 mark) Modify Seq

Change the sequence to support insertion only at its end. Replace the indexed `set` operation with the following operation; keep `get` unchanged. Add `size`.

| Method | Parameters | Behaviour | Return |
|---|---|---|---|
| `set` | One item of type `T` | Append at the next unused position | `void` |
| `size` | None | Report the number of items appended so far | `int` |

A new sequence has size 0. Reading an element does not change its size. You may assume all `get` indices refer to appended items and every insertion fits within capacity. You may add private fields to `Seq`.

### Sample Run

Each sample begins with a fresh JShell state.

```text
jshell> Seq<String> seq = new Seq<String>(3)
seq ==> Seq@########
jshell> seq.set("North")
jshell> seq.size()
$.. ==> 1
jshell> seq.set("South")
jshell> seq.get(0)
$.. ==> "North"
jshell> seq.get(1)
$.. ==> "South"
jshell> seq.size()
$.. ==> 2
```

### Testing

```shell
java -cp test.jar:. Test1
```

---

## 2. (2 marks) Register Scans

Implement the following public constructor and method.

| Member | Parameters | Behaviour and result |
|---|---|---|
| `RoverMission` | `int capacity, int energy` | Create an empty mission with the stated initial energy |
| `String addScan` | `String name, int cost` | Append a pending scan and return `Added: <status>` |

Registration does not spend energy. A mission can register an unaffordable job. Each mission has independent jobs and energy accounting.

### Sample Run

Each sample begins with a fresh JShell state.

```text
jshell> RoverMission mission = new RoverMission(4, 7)
mission ==> RoverMission@########
jshell> mission.addScan("Crater", 4)
$.. ==> "Added: Crater {next 4 energy} [pending]"
jshell> mission.addScan("Ridge", 6)
$.. ==> "Added: Ridge {next 6 energy} [pending]"
```

### Testing

```shell
java -cp test.jar:. Test2
```

---

## 3. (3 marks) Inspect the Mission

Implement these public methods.

| Method | Parameters | Return |
|---|---|---|
| `String status` | None | All registered jobs in registration order |
| `String pending` | None | Only pending jobs, in registration order |

Each included entry is `<index>: <status>` followed by exactly one newline character (`"\n"`), including the final entry. There is no heading. An empty listing is `""`. Filtering preserves original indices.

A job is pending because it is not done, regardless of available energy. Queries do not execute jobs or change any state. Done jobs remain visible in `status()` and are excluded from `pending()`.

You do not need to optimise string concatenation. You may add supporting methods as needed. The sample uses `println` on strings that already end in a newline, so it includes a blank line after each non-empty listing.

### Sample Run

Each sample begins with a fresh JShell state.

```text
jshell> RoverMission mission = new RoverMission(4, 7)
mission ==> RoverMission@########
jshell> mission.addScan("Crater", 4)
$.. ==> "Added: Crater {next 4 energy} [pending]"
jshell> mission.addScan("Ridge", 6)
$.. ==> "Added: Ridge {next 6 energy} [pending]"
jshell> System.out.println(mission.status())
0: Crater {next 4 energy} [pending]
1: Ridge {next 6 energy} [pending]

jshell> System.out.println(mission.pending())
0: Crater {next 4 energy} [pending]
1: Ridge {next 6 energy} [pending]

```

### Testing

```shell
java -cp test.jar:. Test3
```

---

## 4. (4 marks) Execute and Recharge

Implement the following public methods.

| Method | Parameters | Behaviour and result |
|---|---|---|
| `String execute` | `int index` | Attempt the specified job, following the rules below |
| `int recharge` | `int units` | Add units to available energy and return the new available total |
| `int energyLeft` | None | Return available energy |
| `int energyUsed` | None | Return total energy consumed by successful executions at this mission |

Process an execution request in this order:

1. If the job is done, return `Cannot execute: <status>`.
2. Otherwise, if available energy is less than the job's next cost, return `Insufficient energy: <status>`.
3. Otherwise, consume the cost, mark the scan done, and return `Executed: <status>` using the status **after** execution.

A refusal changes nothing: neither energy total nor any job state. Exactly enough energy is sufficient. Re-executing a done scan is refused even though its displayed next cost is 0. Do not throw exceptions for these refusals.

`energyUsed()` starts at 0, regardless of initial energy. Recharging increases available energy but does not change energy already used or the status of any job. Successful executions add the actual cost consumed to energy used. Queries do not change either total.

### Sample Run

Each sample begins with a fresh JShell state.

```text
jshell> RoverMission mission = new RoverMission(4, 7)
mission ==> RoverMission@########
jshell> mission.addScan("Crater", 4)
$.. ==> "Added: Crater {next 4 energy} [pending]"
jshell> mission.addScan("Ridge", 6)
$.. ==> "Added: Ridge {next 6 energy} [pending]"
jshell> mission.execute(0)
$.. ==> "Executed: Crater {next 0 energy} [done]"
jshell> mission.energyLeft()
$.. ==> 3
jshell> mission.energyUsed()
$.. ==> 4
jshell> mission.execute(1)
$.. ==> "Insufficient energy: Ridge {next 6 energy} [pending]"
jshell> mission.execute(0)
$.. ==> "Cannot execute: Crater {next 0 energy} [done]"
jshell> mission.recharge(3)
$.. ==> 6
jshell> mission.execute(1)
$.. ==> "Executed: Ridge {next 0 energy} [done]"
jshell> mission.energyLeft()
$.. ==> 0
jshell> mission.energyUsed()
$.. ==> 10
jshell> System.out.println(mission.pending())

jshell> System.out.println(mission.status())
0: Crater {next 0 energy} [done]
1: Ridge {next 0 energy} [done]

```

### Testing

```shell
java -cp test.jar:. Test4
```

To run all Task 1 groups with a fresh compilation:

```shell
bash test.sh task1
```

The script stops at the first failing group; individual test commands remain available. A group may report multiple failed assertions. Assertion counts are not mark allocations.

Save your current Task 1 attempt before opening Task 2:

```shell
bash submit.sh task1
```

### Potential Changes

- Some jobs may need multiple successful executions or may remain pending indefinitely.
- The energy required for the next execution may change as a job progresses.
- A job may become unavailable without completing normally.
- The rover may select a job using a rule rather than an explicit index.
- Additional information may appear in a job's status.

There may be other changes. Choose common operations that allow the mission to work with different jobs. Decide for yourself whether abstract classes or interfaces help and what those contracts should promise. You are not required to create any particular supporting class or field.
