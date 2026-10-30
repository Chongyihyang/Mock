# CS2030S AY2022/23 Semester 2 — PE1, Session II

**DayCalendar — 90 minutes — 20 marks**

This practice edition covers Session II (2 PM), PDF pages 5–8 only. The original allocation is **12 marks for design, 3 for style, and 5 for correctness**. Style and correctness marks require a reasonable attempt at the tasks. The paper explicitly states that an uncompilable submission receives zero marks; it does not state that failing any single test means zero overall.

## About this edition

The PDF refers to starting Java files and course tests that are not included in the PDF. This package supplies **independently reconstructed procedural starting code**, replacement `Array<T>` support, and new practice tests. These are not the original exam source or official tests. `pristine/` preserves the reconstructed baseline.

This is a refactoring exercise: the starter already performs the required operations, but its design needs improvement. **No completed OO hierarchy or exception-handling solution is included.** The design decisions remain yours.

The paper contains a busy-period typo: after cancelling the meeting with Ahmad, its code comment says 5, while the following sentence says 6. The durations add up to **5**, which this edition uses. Sample file paths have been changed from `Sample.txt` to `inputs/Sample.txt`; all sample output is retained.

Use only **public/private** access, following your course restriction. Keep your Java files directly in this folder without package declarations. You may create classes or interfaces as needed. Do not compile `pristine/` together with your implementation.

## The scenario

`DayCalendar` represents a calendar for one day. It reads events and lets its user list them, receive reminders, cancel meetings, and find the day's busy period.

The supplied implementation uses minimal object-oriented design. Rewrite it to follow the OO principles you have learned, while preserving its observable behaviour.

### Constructing a calendar

```java
DayCalendar cal1 = new DayCalendar(filename); // Read from a named file.
DayCalendar cal2 = new DayCalendar();         // Read from standard input.
```

### Events and input

There are three event types:

| Type | Event | Information |
|---|---|---|
| 0 | Birthday | An all-day event associated with the birthday person's name; no starting or ending time |
| 1 | Lesson | An event with a starting and ending time |
| 2 | Meeting | An event with a starting and ending time, and a person to meet |

The first input line is a positive integer `n`. The next `n` lines describe the events in comma-separated fields:

1. The integer event type: 0, 1, or 2.
2. The event description; for a birthday, the birthday person's name.
3. For timed events, the starting hour, a positive integer.
4. For timed events, the ending hour, a positive integer at least equal to the starting hour.
5. For meetings, the name of the person to meet.

Input follows this format correctly. The provided `inputs/Sample.txt` contains:

```text
5
1,CS2030S,12,14
0,Ah Huat
2,Discuss Project,11,12,Ahmad
1,MA2101,8,10
2,Breakfast,10,11,Devi
```

### Listing descriptions

`printEventDescriptions()` takes no arguments and returns nothing. Print the description of every non-cancelled event, in input order. A birthday is described as `Birthday (NAME)`. Event indices are their original zero-based input positions.

```java
new DayCalendar("inputs/Sample.txt").printEventDescriptions();
```

```text
0 CS2030S
1 Birthday (Ah Huat)
2 Discuss Project
3 MA2101
4 Breakfast
```

### Listing details

`printEventDetails()` takes no arguments and returns nothing. Print every non-cancelled event in input order, including times and the meeting partner where applicable.

```java
new DayCalendar("inputs/Sample.txt").printEventDetails();
```

```text
0 CS2030S | 12 - 14
1 Birthday (Ah Huat)
2 Discuss Project | 11 - 12 | Meet with Ahmad
3 MA2101 | 8 - 10
4 Breakfast | 10 - 11 | Meet with Devi
```

### Cancelling events

`cancelEvent(int index)` attempts to cancel the indexed event and returns nothing. **Only meetings can be cancelled.** Successful cancellation prints nothing. Cancelling an already cancelled meeting has no effect and prints nothing.

```java
DayCalendar cal = new DayCalendar("inputs/Sample.txt");
cal.cancelEvent(2);
cal.printEventDetails();
```

```text
0 CS2030S | 12 - 14
1 Birthday (Ah Huat)
3 MA2101 | 8 - 10
4 Breakfast | 10 - 11 | Meet with Devi
```

Attempting to cancel a birthday or lesson prints an error and leaves the event unchanged:

```java
DayCalendar cal = new DayCalendar("inputs/Sample.txt");
cal.cancelEvent(0);
cal.cancelEvent(1);
cal.printEventDetails();
```

```text
Unable to cancel event: CS2030S
Unable to cancel event: Birthday (Ah Huat)
0 CS2030S | 12 - 14
1 Birthday (Ah Huat)
2 Discuss Project | 11 - 12 | Meet with Ahmad
3 MA2101 | 8 - 10
4 Breakfast | 10 - 11 | Meet with Devi
```

### Reminders

`remind(int time)` takes the current hour and returns nothing. Print the details of non-cancelled timed events whose **starting hour is greater than or equal to `time`**. Preserve input order and original indices. Birthdays have no starting hour and are not listed by this operation.

```java
DayCalendar cal = new DayCalendar("inputs/Sample.txt");
cal.remind(10);
```

```text
0 CS2030S | 12 - 14
2 Discuss Project | 11 - 12 | Meet with Ahmad
4 Breakfast | 10 - 11 | Meet with Devi
```

With a cancellation:

```java
DayCalendar cal = new DayCalendar("inputs/Sample.txt");
cal.cancelEvent(2);
cal.remind(10);
```

```text
0 CS2030S | 12 - 14
4 Breakfast | 10 - 11 | Meet with Devi
```

### Busy period

`getBusyPeriod()` takes no arguments and returns an `int` giving the total hours contributed by non-cancelled lessons and meetings. Birthdays contribute no hours. Each timed event's duration is its ending hour minus its starting hour.

```java
DayCalendar cal = new DayCalendar("inputs/Sample.txt");
cal.cancelEvent(2);
cal.getBusyPeriod(); // Returns 5.
```

The two lessons contribute 4 hours in total and the remaining meeting with Devi contributes 1 hour. The cancelled meeting contributes none.

## Task 1: Refactor using OO principles

Read `DayCalendar.java` to understand its behaviour. Create a class called **`Event`** to encapsulate relevant attributes and behaviour. Create subclasses as necessary.

Use polymorphism to simplify `DayCalendar` and make the program extensible to new event types. Follow Liskov substitution, tell-don't-ask, and information hiding. Choose the responsibilities, relationships, helper methods and internal representation yourself. Retain the public calendar operations described above.

## Task 2: Implement exception handling

Create a **checked** exception named **`IllegalCancellationException`** for attempts to cancel a birthday or a lesson.

Throw this exception from `Event` or an appropriate subclass. Catch and handle it in `DayCalendar.cancelEvent`, preserving the exact error output above. Clients calling `cancelEvent` should not have to catch this exception.

The paper does not prescribe the exception's constructor signature or the event cancellation method's name. A checked exception can extend `java.lang.Exception`; `Exception(String message)` stores a message retrievable with `getMessage()`.

## Practice assumptions

The missing original source could contain additional details. For this reconstruction:

- Input files exist, input is valid, and client indices are valid. Invalid event types, malformed input, missing files and invalid indices are outside the assessed scope.
- Descriptions and names contain no commas. Spaces within fields are preserved.
- Listings and reminders retain original indices even after cancellation; they are not sorted by time.
- Busy hours are summed from individual durations in the reconstructed starter. The paper does not clarify overlapping-event semantics; **the supplied tests do not assess overlapping timed intervals**.
- A timed event may have equal start and end hours, contributing zero hours. It still appears in listings and in reminders when its starting hour qualifies.
- Reminder queries do not advance time or change the calendar. No passage-of-time operation is required.
- The replacement `Array<T>` provides `get`, `set`, and `length`. You need not retain the starter's parallel-array representation.

## Running the tests

Use a **90-minute timer**, including reading, planning and testing. The original assessment was open book for written/printed notes, with digital resources restricted to those available on the PE machines.

Run from this folder with JDK 17 or later:

```sh
sh test.sh 1       # Seven original sample behaviours.
sh test.sh 2       # Twenty-eight additional behaviour checks.
sh test.sh 3       # Two partial structural checks after refactoring.
sh test.sh all
```

**Tests 1 and 2 pass on the starter.** They protect existing behaviour while you refactor. **Test3 initially fails**, because you must create `Event` and the checked exception.

Test3 does not prove that exceptions are actually thrown/caught in the required places, or that the hierarchy follows OO principles. Those aspects require code review. Passing all tests does not automatically award design/style marks or guarantee success on unavailable official tests.

The script compiles only the selected test, its support code, and your top-level implementation files. It does not compile `pristine/`. No JUnit or `-ea` flag is needed. Each failed group exits nonzero; `all` stops at the first failed group. Output comparisons preserve whitespace, normalising only Windows/Unix line endings.

Without the script:

```sh
javac -Xlint:rawtypes -Xlint:unchecked -Werror -d out *.java
java -cp out Test1
java -cp out Test2
java -cp out Test3
```

The practice build rejects raw/unchecked warnings. The original checkstyle tool and configuration are unavailable and are not bundled or claimed to have been run. Follow your course style rules and keep all submitted code compilable.

### Trying your own calls

An optional `Playground.java` already contains a `main` method. Edit the statements inside it and run:

```sh
javac *.java && java Playground
```

It initially prints the sample details and then `Busy hours: 6` (nothing has been cancelled). This avoids JShell loading-order issues. Your own tests and the provided tests contain client calls only; they do not provide the refactoring solution.

Do not commit generated `.class` files or `out/`. Keep `pristine/` as a reference, and submit your implementation files for review when finished.
