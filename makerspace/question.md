# Mock 8 — The Makerspace Order Desk

**Original CS2030S-style practice • 90 minutes • 20 marks**

This is an original practice question, not an official NUS paper. It builds on TaskList and DayCalendar but asks you to design a program from a specification. There is no procedural implementation to refactor.

The focus is abstraction, inheritance/polymorphism, encapsulation, checked exceptions, immutability, tell-don't-ask and Liskov substitution. You do not need functional interfaces, streams, generics or wildcard syntax in your implementation. Functional interfaces in the tests are supplied test machinery.

## Instructions

- Use Java 17. Only **public and private** access is allowed; use private fields. No package declarations.
- Do not use `instanceof`, `getClass()`, reflection, casts, or strings/numeric tags to dispatch on the kind of work. Do not use raw types or unchecked casts.
- Use ordinary arrays if you need a collection. No Java collection classes, streams or external libraries are needed or allowed in the submitted implementation.
- Construction, queries and submission must be silent. Only client code prints output, including error messages.
- Preserve the public API specified below. Choose any additional types, helpers and relationships yourself. The paper deliberately does not specify your fields, subclass arrangement, or whether `WorkItem` is an interface or abstract class.
- A work description is a reusable, unchanging specification. It is not a record of whether an order has been submitted. Receipt details must also remain unchanged once issued. The desk's remaining capacity and revenue do change.
- Assume all arguments satisfy the stated input conditions, references and array entries are non-null, and arithmetic fits in `int`. No argument-validation task is required.
- No `equals` or `hashCode` implementation is required.

Read all parts before coding. Allow **10 minutes to plan**, approximately **15, 20, 15 and 20 minutes** for Parts 1–4, then **10 minutes for final checks**. Sketch relationships and trace one submission before implementing. You are not required to submit the sketch.

## The story

A campus makerspace produces cut signs and printed posters. Staff describe a piece of work before deciding which desk will accept it. Each desk has a fixed remaining allowance of material sheets and machine minutes for the day.

Different kinds of work calculate their requirements differently. The desk's role is to accept a complete order if both allowances permit it, charge the correct price, and issue a receipt. Staff expect to add new kinds of work without teaching every desk how they are produced.

All prices are whole dollars. Machine minutes and sheet counts are integers.

## Part 1 — Describe the work

Clients refer to every kind of work using a common type named `WorkItem`. Any `WorkItem` provides these public operations:

| Operation | Result |
|---|---|
| `String label()` | The work's label, unchanged |
| `int sheets()` | Required material sheets |
| `int minutes()` | Required machine minutes |
| `int price()` | Price in whole dollars |
| `String toString()` | `LABEL [sheets=S, minutes=M, price=P]` |

These queries report the work specification; they do not perform work, consume capacity, or print anything. The desk must be able to use any valid `WorkItem` through this contract.

Implement the following concrete work descriptions:

| Construction | Input meaning | Required sheets | Required minutes | Price |
|---|---|---:|---:|---:|
| `new CutJob(label, sheets, cuts)` | A cut sign; sheets ≥ 1, cuts ≥ 0 | sheets | 2 × sheets + cuts | 5 × sheets + cuts |
| `new PosterJob(label, sheets, colours)` | Printed posters; sheets ≥ 1, colours from 1 to 4 | sheets | 3 × sheets + colours | 2 × sheets + 2 × colours |

`label` is a nonempty `String`. Other constructor arguments are `int`. Spaces in labels are preserved. The sheet count is already the total for the job: do not multiply it again by cuts or colours.

Sample client:

```java
WorkItem sign = new CutJob("Sign", 2, 3);
WorkItem map = new PosterJob("Map", 3, 2);
System.out.println(sign);
System.out.println(map);
```

Output:

```text
Sign [sheets=2, minutes=7, price=13]
Map [sheets=3, minutes=11, price=10]
```

Run `sh test.sh 1`. This group needs only Part 1 implementation files; it does not require the desk, receipt or exception.

## Part 2 — Accept orders and issue receipts

A desk is constructed using `new JobDesk(sheets, minutes)`, with nonnegative `int` capacities. Different desks are independent.

Implement these public operations:

| Operation | Behaviour |
|---|---|
| `Receipt submit(WorkItem work)` | Accept one whole order and return its receipt |
| `int remainingSheets()` | Current unused sheets |
| `int remainingMinutes()` | Current unused machine minutes |
| `int totalCharged()` | Total revenue from accepted orders |

In this part, assume submissions fit both remaining capacities. Part 3 defines what happens otherwise.

For a successful submission, consume exactly the work's required sheets and minutes and add its price to the desk's revenue. Issue consecutive receipt numbers starting at **1 for each desk**. Requirements equal to remaining capacity are acceptable.

Each `Receipt` offers:

| Operation | Result |
|---|---|
| `int number()` | Its receipt number |
| `String label()` | The submitted work's label |
| `int price()` | The amount charged for this order |
| `String toString()` | `#NUMBER LABEL: $PRICE` |

No public receipt constructor is prescribed. A receipt must not change when its desk accepts later orders. The supplied work must not change either: submitting the same object again places a **new order**, consumes its requirements again, and produces a new receipt.

Sample client, placed inside a `main` method that declares `throws Exception` so that it remains valid after Part 3:

```java
JobDesk desk = new JobDesk(10, 30);
Receipt first = desk.submit(new CutJob("Sign", 2, 3));
Receipt second = desk.submit(new PosterJob("Map", 3, 2));
System.out.println(first);
System.out.println(second);
System.out.println(desk.remainingSheets() + " sheets, "
    + desk.remainingMinutes() + " minutes, $" + desk.totalCharged());
```

Output:

```text
#1 Sign: $13
#2 Map: $10
5 sheets, 12 minutes, $23
```

Run `sh test.sh 2`. Both this test and the playground allow checked exceptions in their `main` methods; adding Part 3 does not require changing those clients.

## Part 3 — Refuse an order without side effects

Create a **checked exception** named `CapacityException` and update `submit` to declare it. The caller must be able to catch this specific exception. It represents an order that the desk cannot accept.

Rules:

1. If sheets are insufficient, throw it with the exact message:
   `Insufficient sheets: need N, available A`
2. Otherwise, if machine minutes are insufficient, throw it with:
   `Insufficient minutes: need N, available A`
3. If both are insufficient, report **sheets first**.
4. A refused submission must leave **all desk state unchanged**: no materials consumed, no minutes consumed, no revenue collected, and no receipt number used.
5. The desk throws the exception; it does not catch it merely to print a message or return `null`.

The exception constructor signature is your choice. The message must be available through `getMessage()`.

Sample client, again in a `main` method declaring `throws Exception`:

```java
JobDesk desk = new JobDesk(2, 6);
try {
  desk.submit(new CutJob("Sign", 2, 3));
} catch (CapacityException e) {
  System.out.println(e.getMessage());
}
System.out.println(desk.remainingSheets() + " sheets, "
    + desk.remainingMinutes() + " minutes, $" + desk.totalCharged());
System.out.println(desk.submit(new CutJob("Small", 1, 1)));
```

Output:

```text
Insufficient minutes: need 7, available 6
2 sheets, 6 minutes, $0
#1 Small: $6
```

Run `sh test.sh 3`, then rerun Parts 1–2.

**Before Part 4, save a copy of your current `JobDesk.java` outside the Java source set**, for example as `JobDesk.part3.txt`. This is for your later design review, not an automated test requirement.

## Part 4 — The specification changes: event bundles

Festival organisers want several jobs to appear as one order. They also want to combine existing bundles into larger bundles.

Implement construction with:

`new JobBundle(String label, WorkItem[] items)`

A bundle is itself usable wherever a `WorkItem` is accepted. The array records its immediate contents. Apply these rules:

- Sheets and minutes are the sums of the corresponding requirements of the immediate contents.
- The price is the sum of their prices, minus a **$2 bundle discount**, with a minimum price of zero.
- Each bundle applies its own discount. An inner bundle's already-discounted price contributes to the outer bundle's price.
- An empty bundle is valid and requires zero sheets, zero minutes and zero dollars.
- Each array position counts separately. Repeating the same work object twice means two copies of that work, not one.
- Replacing elements in the caller's array after construction must not change the bundle. You may rely on every supplied `WorkItem` obeying the unchanging-specification contract. Cyclic bundle structures are outside scope.
- The bundle uses the same display format as any other work item, with its own label and aggregate figures. Do not list its contents in `toString()`.
- A bundle is accepted or refused as **one whole order**. Acceptance issues one receipt; refusal leaves the desk entirely unchanged. An empty bundle can be accepted even by a desk with zero capacities and still receives a receipt number.

**Design constraint: add this feature without changing `JobDesk`, `Receipt`, `CutJob`, or `PosterJob`.** You may add types and adjust shared abstractions if necessary, while preserving the earlier contracts. If you need to change a frozen class to get working behaviour, do so and record why; you can still earn correctness credit, but will lose some extension-design credit.

Sample client:

```java
WorkItem fair = new JobBundle("Fair", new WorkItem[] {
  new CutJob("Sign", 2, 3),
  new PosterJob("Map", 3, 2)
});
WorkItem whole = new JobBundle("Whole fair", new WorkItem[] {
  fair, new PosterJob("Extra", 1, 1)
});
System.out.println(fair);
System.out.println(whole);
System.out.println(new JobDesk(6, 22).submit(whole));
```

Output:

```text
Fair [sheets=5, minutes=18, price=21]
Whole fair [sheets=6, minutes=22, price=23]
#1 Whole fair: $23
```

Run `sh test.sh 4`, then `sh test.sh all`.

## Files and testing

The package supplies `Test1.java`–`Test4.java`, `TestSupport.java`, `test.sh`, and an empty `Playground.java` client. **No implementation classes or solutions are supplied.** You create the implementation files yourself. The public contracts above provide the method signatures needed for testing without prescribing your internal design.

Before implementation, the tests will report missing types. This is expected. Implement Part 1 first and run its group. Each group compiles only its selected test and your current top-level implementation files. If you create an unfinished Java file early, its syntax errors can still prevent compilation.

```sh
sh test.sh 1
sh test.sh 2
sh test.sh 3
sh test.sh 4
sh test.sh all
```

There are **91 checks**: 16 in Test1, 24 in Test2, 18 in Test3, and 33 in Test4. All sample outputs are exercised by the Java tests.

The script uses JDK 17, rejects raw/unchecked warnings, starts each group with fresh compiled classes, and stops on the first failed group. The tests need no JUnit or `-ea`. Do not modify the supplied tests to make your implementation pass. Their lambdas and generic helpers are test infrastructure, not extra exam topics.

After completing the full implementation, you can also run:

```sh
javac -Xlint:rawtypes -Xlint:unchecked -Werror -d out *.java
java -cp out Test4
```

For your own experiments, add a few client calls inside `Playground.main`, compile only the implementation files you have finished plus `Playground.java`, and run `java Playground`. **Do not use `javac *.java` early on**, because it would also compile tests for unimplemented later parts.

For example, after Part 1, if those are your only implementation files:

```sh
javac WorkItem.java CutJob.java PosterJob.java Playground.java
java Playground
```

Include any additional helper files you created. This avoids JShell dependency-order issues.

## Practice marking and review gates

This is a practice rubric, not an official marking scheme. Partial credit is available; a single failing test does not erase all other marks. Each behaviour being graded must compile so it can be assessed. No course Checkstyle configuration is bundled.

| Area | Marks | What is assessed |
|---|---:|---|
| Common abstraction and meaningful code reuse | 4 | Shared behaviour belongs in appropriate abstractions; subtypes honour the common contract |
| Responsibilities and tell-don't-ask | 3 | Desk handles admission/accounting without subtype branching; work supplies its requirements through its contract |
| Immutability and state integrity | 3 | Work and receipts stay stable; refusal has no partial effects; constructor-array changes cannot leak into bundles |
| Extension design | 2 | Bundles fit existing clients, including nested bundles; frozen classes remain unchanged |
| Style | 3 | Clear names, private fields, appropriate `final`, readable methods and consistent formatting |
| Observable correctness | 5 | Part 1: 1 mark; Part 2: 1 mark; Part 3: 1.5 marks; Part 4: 1.5 marks |

Resource queries such as `sheets()` express the public work contract. Using them to enforce desk capacity is legitimate collaboration, not automatically a tell-don't-ask violation. Assess where decisions belong rather than banning every query.

Review gates:

- After Part 1: Test1 passes and both jobs can be used through `WorkItem`.
- After Part 2: Tests 1–2 pass; earlier receipts and work descriptions remain stable.
- After Part 3: Tests 1–3 pass; trace both a refusal and the next success to check the receipt number.
- After Part 4: All tests pass; compare your saved Part 3 desk with the final one and explain any changes.

A training pass target is **at least 12/20 overall, including at least 7/12 for design**, with Parts 1–3 working within 90 minutes. Full completion includes Part 4. Passing public tests is evidence of behaviour, not an automatic design score. No additional hidden test files are supplied; later review may probe other inputs within this specification.

When finished, upload your implementation and your saved Part 3 desk. Tell me your time taken, the last part reached when the timer ended, and one design decision you reconsidered. Do not include generated `.class` files or `out/`.
