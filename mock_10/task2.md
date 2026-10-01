# Task 2 — Different policies, one counter

**10 marks · continues Task 1 within the same 90-minute mock**

Keep all Task 1 public methods. Extend your design for the requirements below. Task 1 behaviour remains unchanged **until a parcel is collected**; Q5 explicitly changes which parcels appear in listings and outstanding fees.

The counter begins accepting chilled deliveries and parcels stored in lockers. Staff still use the same operations to list parcels, request more time and compute fees. They should not need separate versions of these operations for every parcel kind.

## Q4 — Chilled and locker parcels (3 marks)

Add these public registration operations to `ParcelDesk`:

| Method | Result |
|---|---|
| `String addChilled(String name, int days)` | Register a chilled parcel |
| `String addLocker(String name, int days, String code)` | Register a locker parcel |

Both return `Registered: STATUS`, without a newline, and allocate indices from the same sequence as `addParcel`.

The policy table below applies to all three kinds. An extension is also refused whenever the parcel is overdue, regardless of kind.

| Kind | Maximum successful extensions | Days added per success | Fee when overdue | Status suffix |
|---|---:|---:|---|---|
| Ordinary (`addParcel`) | 2 | 3 | $2 × number of overdue days | None |
| Chilled (`addChilled`) | 1 | 1 | $4 × number of overdue days | ` [chilled]` |
| Locker (`addLocker`) | 0 | Not applicable | $7 total, regardless of overdue duration | ` [locker CODE]` |

All three kinds have a fee of $0 when their days value is zero or positive. A locker parcel always refuses extension, including when it is due today or has time remaining.

Form a status using the Task 1 format, then append the applicable suffix. Thus any extension marker comes **before** the kind suffix. `CODE` is the supplied locker code, preserved exactly. Do not add an ordinary-parcel suffix.

The inherited expectations for return messages, newlines, original indices and unchanged state on refusal still apply. Your existing `allParcels`, `overdueParcels`, `extend` and `totalFees` must now work with any mixture of parcels. The restriction on testing an object's kind applies here: the desk should issue an operation through a common contract and allow the parcel's policy to determine the result.

**Sample S4 — start with a fresh desk.**

```java
ParcelDesk desk = new ParcelDesk(5);
System.out.println(desk.addParcel("Camera", -2));
System.out.println(desk.addChilled("Herbs", 0));
System.out.println(desk.addChilled("Milk", -2));
System.out.println(desk.addLocker("Cable", -3, "B7"));
System.out.println(desk.extend(1));
System.out.println(desk.extend(1));
System.out.println(desk.extend(3));
System.out.println("Fees: $" + desk.totalFees());
```

Output:

```text
Registered: Camera (-2 days)
Registered: Herbs (0 days) [chilled]
Registered: Milk (-2 days) [chilled]
Registered: Cable (-3 days) [locker B7]
Extended: Herbs (1 days) {1 extensions} [chilled]
Cannot extend: Herbs (1 days) {1 extensions} [chilled]
Cannot extend: Cable (-3 days) [locker B7]
Fees: $19
```

Run `Test4`. The fee total in this sample is $4 + $0 + $8 + $7 = $19.

## Q5 — Collection and payment (4 marks)

Add `String collect(int index)` and `int feesCollected()` to `ParcelDesk`.

Customers may collect any parcel, including one that is overdue. A first collection pays the parcel's current storage fee and marks that registration as collected. The operation does not change its name, days, locker code, or successful-extension count. A collected parcel remains addressable by its original index.

`collect` returns exactly one of these strings, without a newline:

| Situation | Return value | State change |
|---|---|---|
| First collection | `Collected: STATUS; paid $N` | Mark collected; add N to the desk's collected-fee total |
| Already collected | `Already collected: STATUS` | None; do not charge again |

`N` is the fee immediately before the first collection, printed as an integer without decimal places. Free collection must still include `; paid $0`. There is no collection marker added to `STATUS` itself.

`feesCollected()` returns the total fees paid through first collections at this desk. It starts at 0 and querying it has no side effects.

Once a registration is collected, apply all of these changes to the existing operations:

| Operation | Treatment of collected registrations |
|---|---|
| `allParcels()` | Exclude them |
| `overdueParcels()` | Exclude them, even if their days are negative |
| `totalFees()` | Exclude their fees; this is now the **outstanding** fee total |
| `extend(index)` | Refuse with `Cannot extend: STATUS`, without any change |

For an uncollected registration, use the policies from Q4. Do not reuse indices after collection. A new arrival receives the next registration index, not a vacated index. Recall that capacity bounds the total registrations ever made; no test attempts to register more than capacity.

**Sample S5 — start with a fresh desk.**

```java
ParcelDesk desk = new ParcelDesk(4);
desk.addParcel("Camera", -2);
desk.addChilled("Herbs", 0);
desk.addLocker("Cable", -3, "B7");
System.out.println(desk.collect(0));
System.out.println(desk.collect(0));
System.out.println(desk.extend(0));
System.out.print(desk.allParcels());
System.out.println("Outstanding: $" + desk.totalFees());
System.out.println("Collected fees: $" + desk.feesCollected());
System.out.println(desk.collect(1));
System.out.println(desk.collect(2));
System.out.print(desk.allParcels());
System.out.println("Outstanding: $" + desk.totalFees());
System.out.println("Collected fees: $" + desk.feesCollected());
```

Output:

```text
Collected: Camera (-2 days); paid $4
Already collected: Camera (-2 days)
Cannot extend: Camera (-2 days)
1: Herbs (0 days) [chilled]
2: Cable (-3 days) [locker B7]
Outstanding: $7
Collected fees: $4
Collected: Herbs (0 days) [chilled]; paid $0
Collected: Cable (-3 days) [locker B7]; paid $7
Outstanding: $0
Collected fees: $11
```

Run `Test5`. The final call to `allParcels()` returns `""`, so it adds no output line in this sample.

## Q6 — Preserve the contract across mixed operations (3 marks)

There is no new public method in this question. Check that the combined design obeys the following rules under interleaved operations:

- An extension followed by collection retains its extended days and extension marker in both the collection message and any later refusal message.
- A collected parcel with positive days still refuses extension, even if it had unused extensions.
- Collecting one registration does not collect another with the same name. Registration indices identify parcels, not names.
- Free and paid collections can be interleaved. Repeated collections never increase `feesCollected()` or alter `totalFees()`.
- Separate desks have independent registrations, indices, extension counts and collected-fee totals.
- All operations remain silent; client code decides what to print.

Q6 awards marks for these interactions rather than counting the basic Q4/Q5 checks twice. `Test6` exercises these sequences. No new policy beyond the earlier specifications is introduced.

Run `bash test.sh task2`, which also reruns Task 1 tests. Save your final attempt using `bash submit.sh task2`.

## State rules at a glance

| State when an operation starts | Extend | First/repeated collection | Listed? | Included in `totalFees()`? |
|---|---|---|---|---|
| Uncollected, days >= 0 | Apply kind's extension limit | First collection; pay $0 | Full list only | Yes, contributes $0 |
| Uncollected, days < 0 | Refuse; no change | First collection; pay kind's fee | Full and overdue lists | Yes |
| Collected, any days | Refuse; no change | Already collected; no change | Neither list | No |

After a successful extension, only days and the count of successful extensions change. After a first collection, only collection state and the desk's accumulated paid fees change. Queries, refused extensions and repeated collections leave all state unchanged.
