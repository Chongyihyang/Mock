# Practice PE — Fabrication Workshop — Task 1
CS2030S Programming Methodology II  
Time Allowed: 90 minutes across both tasks

## Background

A fabrication workshop shares one workstation between cutting and etching jobs.
Jobs wait in a queue. Each receives one turn at the workstation before an
unfinished job rejoins the back of the queue. Finished jobs leave the line.

## 1. (3 marks) Cutting jobs

Complete `WorkshopLine.java`. Create a line with its queue capacity: a positive
whole number of waiting jobs. For zero or a negative capacity, throw
`IllegalArgumentException` with the message `Capacity must be positive`.
The line starts empty.

Use the supplied `Queue<T>` to store the jobs. Its constructor accepts the
capacity. `enqueue` adds an item at the back, `dequeue` removes and produces
the front item, and `size` reports the number waiting. Enqueuing into a full
queue throws `IllegalStateException`; dequeuing from an empty queue throws
`java.util.NoSuchElementException`. The supplied code is complete and must
remain unchanged. It provides no indexed access or iterator.

`addCut` accepts a job name followed by its required number of pieces. Names
are nonempty text without newlines; you may assume all supplied names satisfy
this. Equal names identify separate jobs. The number of pieces must be a
positive whole number. Zero and negative counts can be supplied: construct
and throw `IllegalArgumentException` with the message `Pieces must be positive`.

A cutting job makes exactly one piece on each turn. Its status is
`NAME [pieces=P]`, where P is the number still to make. Initially this is the
supplied count. At zero the job is finished. A specialised job may append its
own labelled details while preserving this status prefix and the cutting rules.

For a valid job, attempt to add it at the back and report `Added: STATUS`.
Handle the supplied queue's full-queue exception by reporting `Full: STATUS`
instead. Rejecting a job must leave the existing queue unchanged. Validate
the job even when the queue is full: invalid piece counts still throw their
specified exception. Creating or rejecting a job does not perform a turn.

Each Sample Run uses a fresh JShell session with your sources loaded using
`/open`. `$..` represents a result number and `########` an object hash.
No particular display of the workshop line itself is required. Operations
produce their results without printing; the sample's exception handlers print
the caught message to demonstrate the result.

### Sample Run

```text
jshell> WorkshopLine line = new WorkshopLine(2)
line ==> WorkshopLine@########

jshell> line.addCut("Tag", 2)
$.. ==> "Added: Tag [pieces=2]"

jshell> try { line.addCut("Bad", 0); } catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
Pieces must be positive

jshell> line.addCut("Stand", 1)
$.. ==> "Added: Stand [pieces=1]"

jshell> line.addCut("Sign", 3)
$.. ==> "Full: Sign [pieces=3]"

```

### Testing

```shell
javac -Xlint:unchecked -Xlint:rawtypes *.java
java -cp test.jar:. Test1
```

## 2. (3 marks) Etching jobs

`addEtch` accepts a job name and the number of plates to etch. Names follow the
same rules as before. The plate count must be a positive whole number. For
zero or a negative count, construct and throw `IllegalArgumentException` with
the message `Plates must be positive`, including when the queue is full.

Every plate needs two turns: first prepare it, then etch it. A new job starts
at `prepare`. A preparation turn changes its stage to `etch` without reducing
the plate count. An etching turn reduces the count by one. If plates remain,
the next stage is `prepare`; otherwise the stage is `done` and the job is finished.

Its status is `NAME [plates=P, stage=S]`, where P counts plates still to etch
and S is `prepare`, `etch`, or `done`. Specialisations may append labelled
details while preserving this prefix and these processing rules.

Use the same queuing and full-queue behaviour as `addCut`. Mixed jobs share
one queue, and adding an etching job does not prepare it.

### Sample Run

```text
jshell> WorkshopLine line = new WorkshopLine(3)
line ==> WorkshopLine@########

jshell> line.addEtch("Plaque", 2)
$.. ==> "Added: Plaque [plates=2, stage=prepare]"

jshell> line.addCut("Tag", 1)
$.. ==> "Added: Tag [pieces=1]"

jshell> try { line.addEtch("Bad", -1); } catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
Plates must be positive

jshell> line.addEtch("Plaque", 1)
$.. ==> "Added: Plaque [plates=1, stage=prepare]"

jshell> line.addEtch("Extra", 1)
$.. ==> "Full: Extra [plates=1, stage=prepare]"

```

### Testing

```shell
javac -Xlint:unchecked -Xlint:rawtypes *.java
java -cp test.jar:. Test2
```

## 3. (4 marks) Taking turns

`runNext` takes no input. Remove the job at the front and give it exactly one
turn. If it finishes, leave it out of the queue and report `Finished: STATUS`.
Otherwise, put it at the back and report `Waiting: STATUS`. STATUS describes
the job after its turn. No other job makes progress.

Handle the supplied queue's empty-queue exception by reporting `Idle`.
An unfinished job continues from its existing state when its next turn arrives.

`pending` takes no input and lists the jobs in their current front-to-back
order. Each line is `POSITION: STATUS` followed by a newline. Positions run
from zero for the front of the current queue; they are not permanent job IDs.
An empty queue produces empty text. Listing must preserve every job's state
and the queue's order. Use the supplied queue operations to access the jobs.

### Sample Run

```text
jshell> WorkshopLine line = new WorkshopLine(3)
line ==> WorkshopLine@########

jshell> line.runNext()
$.. ==> "Idle"

jshell> line.pending()
$.. ==> ""

jshell> line.addCut("Tag", 2)
$.. ==> "Added: Tag [pieces=2]"

jshell> line.addEtch("Plaque", 1)
$.. ==> "Added: Plaque [plates=1, stage=prepare]"

jshell> line.addCut("Stand", 1)
$.. ==> "Added: Stand [pieces=1]"

jshell> line.runNext()
$.. ==> "Waiting: Tag [pieces=1]"

jshell> System.out.print(line.pending())
0: Plaque [plates=1, stage=prepare]
1: Stand [pieces=1]
2: Tag [pieces=1]

jshell> line.runNext()
$.. ==> "Waiting: Plaque [plates=1, stage=etch]"

jshell> line.runNext()
$.. ==> "Finished: Stand [pieces=0]"

jshell> line.runNext()
$.. ==> "Finished: Tag [pieces=0]"

jshell> line.runNext()
$.. ==> "Finished: Plaque [plates=0, stage=done]"

jshell> line.runNext()
$.. ==> "Idle"

jshell> line.pending()
$.. ==> ""

```

### Testing

```shell
javac -Xlint:unchecked -Xlint:rawtypes *.java
java -cp test.jar:. Test3
```

## 4. (6 marks) Running and cancelling

`run` accepts a whole number of turns. A valid count is nonnegative. For a
negative count, construct and throw `IllegalArgumentException` with the
message `Turns cannot be negative` before making any change.

Perform exactly that many calls to `runNext`. Report one line per call as
`TURN: RESULT`, followed by a newline, where TURN starts at one for each call
to `run` and RESULT is the corresponding `runNext` result. Include `Idle`
lines if the queue empties before all turns have been attempted. Zero turns
produce empty text and change nothing. The returned text is a record of that
call; later operations do not change it.

`cancel` accepts a name, following the earlier name rules. Remove only the
first job with that exact name in the current queue order, using a
case-sensitive comparison. Report `Cancelled: STATUS` for that job. If there
is no match, report `Not found: NAME`. Cancellation does not give any job a
turn. All other jobs retain their state and relative order. A freed queue
slot can be used by a later addition.

### Sample Run

```text
jshell> WorkshopLine line = new WorkshopLine(4)
line ==> WorkshopLine@########

jshell> line.addCut("Tag", 3)
$.. ==> "Added: Tag [pieces=3]"

jshell> line.addEtch("Plaque", 1)
$.. ==> "Added: Plaque [plates=1, stage=prepare]"

jshell> line.addCut("Tag", 1)
$.. ==> "Added: Tag [pieces=1]"

jshell> line.addEtch("Sign", 1)
$.. ==> "Added: Sign [plates=1, stage=prepare]"

jshell> System.out.print(line.run(2))
1: Waiting: Tag [pieces=2]
2: Waiting: Plaque [plates=1, stage=etch]

jshell> line.cancel("Tag")
$.. ==> "Cancelled: Tag [pieces=1]"

jshell> System.out.print(line.pending())
0: Sign [plates=1, stage=prepare]
1: Tag [pieces=2]
2: Plaque [plates=1, stage=etch]

jshell> line.cancel("tag")
$.. ==> "Not found: tag"

jshell> try { line.run(-1); } catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
Turns cannot be negative

jshell> line.run(0)
$.. ==> ""

jshell> System.out.print(line.run(7))
1: Waiting: Sign [plates=1, stage=etch]
2: Waiting: Tag [pieces=1]
3: Finished: Plaque [plates=0, stage=done]
4: Finished: Sign [plates=0, stage=done]
5: Finished: Tag [pieces=0]
6: Idle
7: Idle

jshell> line.cancel("Tag")
$.. ==> "Not found: Tag"

jshell> line.pending()
$.. ==> ""

```

### Testing

```shell
javac -Xlint:unchecked -Xlint:rawtypes *.java
java -cp test.jar:. Test4
```

## Submission

Use JDK 17 or later and run `bash submit.sh task1` from the exam folder. All
four groups must pass before `task2.md` is decrypted. Successful submissions
copy your top-level Java sources into `task1/`; earlier snapshots are retained
in uniquely named backup folders. Failed checks do not unlock Task 2 or
replace a saved submission. Original supplied files are in `pristine/` for
resetting, and `.gitignore` is included.

Use at most three additional types beyond `WorkshopLine` and the supplied
`Queue`. Use the standard exceptions specified above; no custom exception
type is required. Source review checks exception construction and handling,
preservation of parent contracts, subclass displays built by appending to
`super.toString()`, and decisions kept with the objects that own the state.
Avoid direct field getters where an operation can do the work instead.
