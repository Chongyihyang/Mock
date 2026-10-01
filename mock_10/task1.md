# Task 1 — The collection register

**10 marks · part of the shared 90-minute mock**

Read [the shared rules and commands](question.md) first. Complete the public `ParcelDesk` API in the starter file and create any supporting types you need. No particular internal class names or fields are prescribed.

The counter labels each arriving parcel with its name and the number of days remaining for collection. A positive number means time remains, zero means collection is due today, and a negative number means it is overdue. Two parcels may have the same name; they are still separate registrations.

## Q1 — Register arrivals (3 marks)

`new ParcelDesk(int capacity)` creates an empty register.

`String addParcel(String name, int days)` registers an ordinary parcel and returns its registration message. Each registration receives the next index, starting at zero. The first call registers index 0, the second index 1, and so on. The returned registration message does not include the index.

Use the following status format everywhere in this task:

| Situation | Status |
|---|---|
| No successful extensions | `NAME (D days)` |
| E successful extensions, where E > 0 | `NAME (D days) {E extensions}` |

`NAME`, `D` and `E` are placeholders, not literal text. Use the current number of days and preserve names exactly. Always use `days` and `extensions`, even for 1. There is one space between adjacent parts of the status.

The registration message is exactly `Registered: STATUS`. It contains no newline.

**Sample S1 — start with a fresh desk.**

```java
ParcelDesk desk = new ParcelDesk(5);
System.out.println(desk.addParcel("Camera", 2));
System.out.println(desk.addParcel("Tin", -2));
System.out.println(desk.addParcel("Badge", 0));
```

Output:

```text
Registered: Camera (2 days)
Registered: Tin (-2 days)
Registered: Badge (0 days)
```

Run `Test1` after Q1.

## Q2 — List the register (4 marks)

`String allParcels()` returns every registered parcel, in registration order.

`String overdueParcels()` returns only parcels whose days value is **strictly below zero**, in registration order. A parcel due today is not overdue.

Each included entry has the form `INDEX: STATUS` followed by one newline character (`\n`), including the final entry. Use the original registration index; filtering does not renumber entries. There are no headings or blank lines in the returned string. An empty result is the empty string `""`.

These methods only report the current register. They do not extend parcels, change days or alter extension counts.

**Sample S2 — start with a fresh desk.**

```java
ParcelDesk desk = new ParcelDesk(5);
desk.addParcel("Camera", 2);
desk.addParcel("Tin", -2);
desk.addParcel("Badge", 0);
System.out.print(desk.allParcels());
System.out.println("Overdue:");
System.out.print(desk.overdueParcels());
```

Output:

```text
0: Camera (2 days)
1: Tin (-2 days)
2: Badge (0 days)
Overdue:
1: Tin (-2 days)
```

Run `Test2` after Q2. The sample client uses `print` for listing strings because they already contain their line endings.

## Q3 — Extensions and storage fees (3 marks)

A customer may request more time for an ordinary parcel. The counter permits at most **two successful extensions per parcel**. Each successful extension adds **three days**. Overdue parcels cannot be extended; a parcel due today may be extended.

`String extend(int index)` processes the request and returns one of these messages:

| Outcome | Exact return format | Effect |
|---|---|---|
| Granted | `Extended: STATUS` | Add 3 days and count one successful extension |
| Refused | `Cannot extend: STATUS` | No change of any kind |

For a grant, `STATUS` uses the state **after** the change. For a refusal, it uses the unchanged current state. A refused request does not use up an extension. Do not throw an exception for a refusal.

`int totalFees()` returns the sum of storage fees across the register. An ordinary parcel costs **$2 for each overdue day**. Parcels with zero or positive days cost $0. For example, a parcel at -3 days contributes $6. Querying fees neither collects payment nor changes the register.

**Sample S3 — start with a fresh desk.**

```java
ParcelDesk desk = new ParcelDesk(5);
desk.addParcel("Camera", 2);
desk.addParcel("Tin", -2);
desk.addParcel("Badge", 0);
System.out.println(desk.extend(1));
System.out.println(desk.extend(2));
System.out.println(desk.extend(2));
System.out.println(desk.extend(2));
System.out.print(desk.allParcels());
System.out.println("Fees: $" + desk.totalFees());
```

Output:

```text
Cannot extend: Tin (-2 days)
Extended: Badge (3 days) {1 extensions}
Extended: Badge (6 days) {2 extensions}
Cannot extend: Badge (6 days) {2 extensions}
0: Camera (2 days)
1: Tin (-2 days)
2: Badge (6 days) {2 extensions}
Fees: $4
```

Run `Test3`, then `bash test.sh task1`. Save this version with `bash submit.sh task1` before continuing to Task 2.

## Task 1 boundary checklist

- Zero days: included in the full list, absent from the overdue list, no fee, extension allowed.
- Third extension: refused without changing the days or successful-extension count.
- Negative days: overdue, incurs a fee, cannot extend.
- Empty register: both lists are `""` and total fees are 0.
- Duplicate names and different desks: each registration has independent state.
