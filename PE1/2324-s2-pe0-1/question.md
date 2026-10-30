# CS2030S AY2023/24 Semester 2 — PE0, Session I

**Vehicles and Road Trips — 60 minutes — 30 marks**

This practice edition covers **Session I (8 AM), pages 1–4 of `2324-s2-pe0.pdf`**, including Questions 1–6. Later sessions are excluded.

The question is adapted into Markdown, retaining the original requirements and sample behaviour. The Java tests are newly written practice tests, not the unavailable official tests. No implementation solutions are supplied. This paper explicitly prescribes some classes and fields; those requirements are retained rather than replaced by hints.

## Instructions

- The original time limit is **60 minutes**. Up to **3 marks may be deducted for style**.
- The original assessment was closed book except for **one double-sided A4 sheet**.
- Code that cannot compile receives zero marks for the corresponding question, according to the paper. Keep each completed stage compilable.
- Keep implementation files directly in this folder, without package declarations. Use Java 17 and only public/private access, following your course restriction. Keep internal instance fields private; the explicitly public fuel-efficiency constant is an exception to that field-visibility rule.
- Choose any implementation details the paper does not prescribe. The sample client calls are required API examples, not solutions.
- All construction, refuelling, folding, unfolding and direct movement calls are silent. `toString()` returns text. Only a failed `RoadTrip.complete()` prints the specified error.
- The course's original Checkstyle tool and `pe0_style.xml` are not bundled. No official style check is claimed here.

API reminder from the paper:

| Constructor | Behaviour |
|---|---|
| `Exception()` | Creates an exception whose detail message is null |
| `Exception(String msg)` | Creates an exception with the given detail message |

## Question 1 — CannotMoveException (2 marks)

Implement a **checked exception** named `CannotMoveException`. Provide a public constructor that takes no parameters and has an empty body.

## Question 2 — Vehicle (3 marks)

Implement an **abstract class** named `Vehicle`.

It must declare a public abstract method named `move` that takes the distance as a `double`, returns nothing, and may throw `CannotMoveException`.

## Question 3 — Car (9 marks)

Implement `Car`, which inherits from `Vehicle`.

All cars share a **public `double` field named `FUEL_EFFICIENCY`**, with value **5.0**, which cannot be modified.

A car has an internal field named `fuelLevel`. The constructor `Car(double)` initialises its fuel level using the supplied value.

Implement:

- `toString()`, returning `Car fuelLevel: N`, where `N` is formatted to two decimal places.
- `refuel(double)`, which adds the supplied amount to the fuel level and returns nothing.
- `move(double)`, which consumes `distance / FUEL_EFFICIENCY` units of fuel. If insufficient fuel is available, throw `CannotMoveException`.

Sample interaction (load the sources in the displayed order if using JShell):

```text
jshell> /open CannotMoveException.java
jshell> /open Vehicle.java
jshell> /open Car.java

jshell> Car.FUEL_EFFICIENCY
$4 ==> 5.0

jshell> Car.FUEL_EFFICIENCY = 10;
| Error:
| cannot assign a value to final variable FUEL_EFFICIENCY

jshell> Vehicle v = new Car(10)
v ==> Car fuelLevel: 10.00

jshell> Car c = (Car) v
c ==> Car fuelLevel: 10.00

jshell> c.refuel(5.0)
jshell> c
c ==> Car fuelLevel: 15.00

jshell> v.move(10)
jshell> v
v ==> Car fuelLevel: 13.00

jshell> v.move(200)
| Exception CannotMoveException
```

The failed assignment is an example of code that **must not compile**; do not put that assignment in your implementation. The tests inspect the constant's modifiers instead. JShell-generated exception prefixes, snippet numbers, carets and stack-frame locations have been omitted because they vary between sessions.

Run **`sh test.sh 1`** after Questions 1–3. This group does not require Bike, Foldable or RoadTrip.

## Question 4 — Foldable (2 marks)

Write an interface named `Foldable` with public methods `fold` and `unfold`. Both take no arguments and return nothing.

## Question 5 — Bike (9 marks)

Implement `Bike`, which inherits from `Vehicle` and implements `Foldable`.

A bike has two internal fields named `totalDistance` and `folded`. Implement the no-argument constructor, `fold`, `unfold` and `toString()` to match the samples.

A new bike is unfolded and has travelled zero distance. Its string representation is:

`Bike distance: N folded: B`

Here `N` is the total distance formatted to two decimal places, and `B` is `true` or `false`.

Calling `move(double)` increases the total distance by the supplied amount. If the bike is folded, throw `CannotMoveException` instead.

Sample interaction:

```text
jshell> /open Foldable.java
jshell> /open CannotMoveException.java
jshell> /open Vehicle.java
jshell> /open Bike.java

jshell> Foldable f = new Bike()
f ==> Bike distance: 0.00 folded: false

jshell> f.fold()
jshell> f
f ==> Bike distance: 0.00 folded: true

jshell> f.unfold()
jshell> f
f ==> Bike distance: 0.00 folded: false

jshell> Bike b = new Bike()
b ==> Bike distance: 0.00 folded: false

jshell> b.move(10)
jshell> b
b ==> Bike distance: 10.00 folded: false

jshell> b.fold()
jshell> b.move(10)
| Exception CannotMoveException

jshell> b.unfold()
jshell> b.move(10)
jshell> b
b ==> Bike distance: 20.00 folded: false
```

Run **`sh test.sh 2`** after Questions 1, 2, 4 and 5. It does not require Car or RoadTrip.

## Question 6 — RoadTrip (5 marks)

Create a class named `RoadTrip` containing a vehicle and a distance. Support construction with `RoadTrip(Vehicle, double)`.

Provide a public method `complete()` that takes no arguments and returns nothing. It moves the supplied vehicle by the supplied distance. It must catch `CannotMoveException` and handle it by printing:

```text
This trip cannot be completed.
```

If movement succeeds, print nothing. The caller should not have to catch `CannotMoveException` from `complete()`.

**Editorial clarification:** the prose in the PDF omits the final full stop, but both sample outputs include it. This practice edition follows the samples, including the full stop.

Sample interaction, after loading all required classes:

```text
jshell> Bike b = new Bike()
b ==> Bike distance: 0.00 folded: false

jshell> new RoadTrip(b, 10).complete()
jshell> b.fold()
jshell> new RoadTrip(b, 10).complete()
This trip cannot be completed.

jshell> Car c = new Car(10)
c ==> Car fuelLevel: 10.00

jshell> new RoadTrip(c, 10).complete()
jshell> c.move(40)
jshell> new RoadTrip(c, 10).complete()
This trip cannot be completed.
```

Run **`sh test.sh 3`**, then **`sh test.sh all`**.

## Scope of the additional practice tests

The original examples are represented in Java tests: successful values are checked through `toString()` or direct queries; exceptions are checked by type and message rather than by JShell stack traces. Additional checks cover:

- Fractional fuel and distances, sufficient fuel exactly at the boundary, and independent vehicles.
- Failed movement leaving the vehicle's state unchanged. For cars this edition makes that interpretation explicit: insufficient fuel means no movement and no fuel deduction.
- Folding an already folded bike leaving it folded; unfolding an already unfolded bike leaving it unfolded.
- Vehicle operations and constructors not printing debugging output.
- RoadTrip moving the supplied vehicle exactly once, with the correct distance, and working through the general `Vehicle` contract with another test-only subtype.

Only finite, nonnegative initial fuel and refuelling amounts, and positive finite movement distances, are tested. Negative values, NaN, infinities and overflow are outside scope. No extra validation or exception types are required. Tests use a locale with `.` as the decimal separator. The original requirement to use two decimal places remains.

The tests check some declared structure, such as the abstract Vehicle method, the interface, and the fuel constant's modifiers. They do not replace source review of the empty exception constructor, specified fields, access control or style. Reflection and nested helper classes in the tests are supplied machinery you do not need to imitate.

## Files and commands

Supplied files:

- `Test1.java`: Questions 1–3.
- `Test2.java`: Questions 4–5, using Vehicle and CannotMoveException.
- `Test3.java`: Question 6 and integration.
- `TestSupport.java`: test infrastructure.
- `Playground.java`: an empty client for your own experiments.
- `test.sh`: staged compilation and testing.

**You create the implementation files.** No solution classes or field-filled skeletons are supplied. Before implementation, missing-type compilation errors are expected.

Run from the extracted folder:

```sh
sh test.sh 1
sh test.sh 2
sh test.sh 3
sh test.sh all
```

There are **54 checks**: 19 in Test1, 17 in Test2, and 18 in Test3.

The script compiles the selected test, its helper, and your current top-level implementation files. Future test groups are excluded, but an unfinished implementation file can still stop compilation. Each group uses a clean output directory. `all` stops at the first failure. No JUnit or `-ea` is needed; failures exit nonzero. Raw/unchecked warnings fail the practice build.

After completing all questions, you may use:

```sh
javac *.java
java Test1
java Test2
java Test3
```

For your own experiments after Question 3, edit `Playground.main` and run:

```sh
javac CannotMoveException.java Vehicle.java Car.java Playground.java
java Playground
```

Add later implementation filenames as needed. This avoids compiling later tests before their required classes exist and avoids JShell loading-order problems.

The paper's suggested individual commands (`javac Test1.java`, then `java Test1`, and similarly for Test2 and Test3) also work from this folder when that group's dependencies have been implemented.

Use the full **60-minute timer**, including reading and testing. If you finish early, check the exact output formatting, failure-state preservation and polymorphic calls. Keep generated `.class` files and `out/` out of your submission.
