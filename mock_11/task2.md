# Mock PE 11 (Rover Mission) — Task 2
CS2030S Programming Methodology II  
Independent practice paper

**40 minutes remain in the shared 90-minute session.**  
**Task 2: 10 marks**

The restrictions, assumptions, compile commands and output conventions in Task 1 still apply. Keep the existing public API working. Continue editing source files in the main mock directory; preserve the saved `task1/` version.

## Background

The research team now needs jobs that progress differently. The mission must support a mixture of ordinary scans and the new jobs below. The existing execution, listing and energy-accounting operations should continue to work through common job operations, without inspecting a job's kind.

---

## 5. (4 marks) Additional Jobs

Add these public registration methods to `RoverMission`. Each appends one new job and returns `Added: <status>`. All job kinds share the same registration-index sequence.

| Method | Parameters | Description |
|---|---|---|
| `String addSweep` | `String name, int baseCost, int passes` | Register a pending sweep requiring the stated positive number of passes |
| `String addBeacon` | `String name, int cost` | Register a pending beacon transmission job |

### Sweep

A sweep starts with 0 completed passes. Each successful execution completes exactly one pass. If `k` passes have been completed, its next pass costs `baseCost * (k + 1)` energy units. Once all required passes have succeeded, the sweep becomes done and its next cost becomes 0.

For example, a sweep with base cost 2 and 3 passes consumes 2, then 4, then 6 units. It consumes 12 in total. A failed execution does not advance its pass count or increase its next cost.

Append ` [sweep <completed>/<required>]` to the Task 1 status format. A sweep requiring 1 pass is valid and completes after that single pass.

### Beacon

Each successful beacon execution consumes its fixed cost and increases its count of successful transmissions by 1. A beacon remains pending after every success; it never becomes done through execution. It starts with 0 successful transmissions.

Append ` [beacon <count>]` to the Task 1 status format. Refused attempts do not increase the count.

### Shared requirements

- Base costs, beacon costs and required passes are positive. All products and totals fit in `int`.
- Apply the Task 1 execution checks and messages to all kinds. A success reports the **updated** status, but spends the cost applicable **before** that success.
- Each new suffix follows the closing state bracket, with exactly one space before it. Ordinary scans have no suffix.
- Registration, queries and refusals consume no energy. `energyUsed()` includes every successful pass and transmission.

### Sample Run

Each sample begins with a fresh JShell state.

```text
jshell> RoverMission mission = new RoverMission(4, 12)
mission ==> RoverMission@########
jshell> mission.addSweep("Basin", 2, 3)
$.. ==> "Added: Basin {next 2 energy} [pending] [sweep 0/3]"
jshell> mission.addBeacon("Relay", 3)
$.. ==> "Added: Relay {next 3 energy} [pending] [beacon 0]"
jshell> mission.execute(0)
$.. ==> "Executed: Basin {next 4 energy} [pending] [sweep 1/3]"
jshell> mission.execute(1)
$.. ==> "Executed: Relay {next 3 energy} [pending] [beacon 1]"
jshell> mission.execute(0)
$.. ==> "Executed: Basin {next 6 energy} [pending] [sweep 2/3]"
jshell> mission.execute(0)
$.. ==> "Insufficient energy: Basin {next 6 energy} [pending] [sweep 2/3]"
jshell> mission.recharge(3)
$.. ==> 6
jshell> mission.execute(0)
$.. ==> "Executed: Basin {next 0 energy} [done] [sweep 3/3]"
jshell> mission.energyLeft()
$.. ==> 0
jshell> mission.energyUsed()
$.. ==> 15
jshell> System.out.println(mission.pending())
1: Relay {next 3 energy} [pending] [beacon 1]

```

### Testing

```shell
java -cp test.jar:. Test5
```

---

## 6. (3 marks) Cancel Pending Jobs

Operators may withdraw jobs that should no longer run. Add the following public method:

| Method | Parameters | Behaviour |
|---|---|---|
| `String cancel` | `int index` | Attempt cancellation of the specified job |

For a pending job of **any kind**, cancellation succeeds: it enters the `cancelled` state and its next cost becomes 0. Return `Cancelled: <status>` using the updated status. Cancellation consumes no energy and refunds none. Preserve the name and all completed-pass or transmission counts.

For a job already done or cancelled, return `Cannot cancel: <status>` and change nothing. A done job stays done; it is not converted to cancelled.

After cancellation, apply these rules:

| Operation | Required behaviour |
|---|---|
| `status()` | Include the registration with state `cancelled` |
| `pending()` | Exclude it |
| `execute(index)` | Return `Cannot execute: <status>` without any change |
| `energyLeft()` / `energyUsed()` | Cancellation changes neither total |

A cancelled sweep retains its `[sweep k/n]` suffix; a cancelled beacon retains its `[beacon k]` suffix. Refusal takes precedence over the insufficient-energy check for all unavailable jobs. Recharging does not reactivate a done or cancelled job.

Cancellation does not remove a registration, free a capacity slot or renumber later jobs. A new registration receives the next unused index. As in Task 1, tests never exceed lifetime registration capacity.

### Sample Run

Each sample begins with a fresh JShell state.

```text
jshell> RoverMission mission = new RoverMission(4, 10)
mission ==> RoverMission@########
jshell> mission.addSweep("Basin", 2, 3)
$.. ==> "Added: Basin {next 2 energy} [pending] [sweep 0/3]"
jshell> mission.addBeacon("Relay", 3)
$.. ==> "Added: Relay {next 3 energy} [pending] [beacon 0]"
jshell> mission.addScan("Crater", 4)
$.. ==> "Added: Crater {next 4 energy} [pending]"
jshell> mission.execute(0)
$.. ==> "Executed: Basin {next 4 energy} [pending] [sweep 1/3]"
jshell> mission.cancel(0)
$.. ==> "Cancelled: Basin {next 0 energy} [cancelled] [sweep 1/3]"
jshell> mission.execute(0)
$.. ==> "Cannot execute: Basin {next 0 energy} [cancelled] [sweep 1/3]"
jshell> mission.cancel(0)
$.. ==> "Cannot cancel: Basin {next 0 energy} [cancelled] [sweep 1/3]"
jshell> mission.execute(2)
$.. ==> "Executed: Crater {next 0 energy} [done]"
jshell> mission.cancel(2)
$.. ==> "Cannot cancel: Crater {next 0 energy} [done]"
jshell> mission.energyLeft()
$.. ==> 4
jshell> mission.energyUsed()
$.. ==> 6
jshell> System.out.println(mission.pending())
1: Relay {next 3 energy} [pending] [beacon 0]

```

### Testing

```shell
java -cp test.jar:. Test6
```

---

## 7. (3 marks) Execute the Next Pending Job

Add the following public method:

| Method | Parameters | Behaviour and result |
|---|---|---|
| `String runNext` | None | Find the pending job with the smallest registration index and attempt it once |

Return exactly the message that `execute(index)` would return for that job, with the same state and energy effects. If there is no pending job, return `No pending jobs` and change nothing.

Selection ignores whether a pending job is currently affordable. If the first pending job needs too much energy, refuse that attempt; do **not** try a later, cheaper job. A call attempts at most one execution. It does not loop until a job finishes.

A sweep that remains pending can be selected again by the next call. A pending beacon retains its place in the order and can therefore keep being selected until cancelled. Done and cancelled jobs are skipped. Selection does not reorder the register.

### Sample Run

Each sample begins with a fresh JShell state.

```text
jshell> RoverMission mission = new RoverMission(4, 3)
mission ==> RoverMission@########
jshell> mission.addSweep("Basin", 2, 2)
$.. ==> "Added: Basin {next 2 energy} [pending] [sweep 0/2]"
jshell> mission.addScan("Ridge", 1)
$.. ==> "Added: Ridge {next 1 energy} [pending]"
jshell> mission.runNext()
$.. ==> "Executed: Basin {next 4 energy} [pending] [sweep 1/2]"
jshell> mission.runNext()
$.. ==> "Insufficient energy: Basin {next 4 energy} [pending] [sweep 1/2]"
jshell> mission.cancel(0)
$.. ==> "Cancelled: Basin {next 0 energy} [cancelled] [sweep 1/2]"
jshell> mission.runNext()
$.. ==> "Executed: Ridge {next 0 energy} [done]"
jshell> mission.runNext()
$.. ==> "No pending jobs"
jshell> mission.energyUsed()
$.. ==> 3
```

### Testing

```shell
java -cp test.jar:. Test7
```

## Submission

All seven groups should now work. Task 1 tests remain regression checks:

```shell
javac -Xlint:unchecked -Xlint:rawtypes *.java
java -cp test.jar:. Test5
java -cp test.jar:. Test6
java -cp test.jar:. Test7
bash test.sh task2
bash submit.sh task2
```

Stop coding at minute 90 and save your current attempt even if unfinished. `submit.sh` records sources locally; commit and push your main-directory implementation and the `task1/` and `task2/` snapshots for review.
