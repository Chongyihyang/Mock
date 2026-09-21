# Mock 3 — The Maker Hub
**Suggested time: 110 minutes. Total: 100 marks. Java 17 or newer.**

The university's Maker Hub offers workshops. A receptionist admits visitors using the credential they present. The Hub expects its membership schemes to evolve, and has asked you to keep the receptionist's work stable as new schemes appear.

Start with a class diagram in your own notes. At each stage, record which existing classes you changed and why. The names in the client contract below are for compatibility with the tests; the internal representation and inheritance relationships are yours to design. No starter implementations or solutions are supplied.

## Working rules
- Use only public and private access for your declarations. Do not use protected or package-private access. Use the default package.
- Keep instance fields private. Do not expose setters that let clients overwrite balances, remaining visits, or workshop capacity.
- Do not distinguish credential types using casts, instanceof, reflection, class names, or a type flag. These restrictions apply to your implementation, not the supplied test harness.
- Do not use streams or require external libraries. Ordinary arrays and java.util collections are allowed.
- Use checked exceptions for the business refusals described below. Do not catch Exception or Throwable to handle those refusals.
- Read-only queries are allowed for receipts, tests and displays. The receptionist must not inspect balances, visits or seats to decide whether admission is possible.
- Failed operations must preserve the affected resources. Earlier stages' valid behavior must continue to work in later stages.
- There is no concurrency, persistence or booking cancellation. Assume arithmetic results fit in an int.

The registration office observes that the credentials it issues have consistent identity rules. An external sponsor, however, should be able to join the admission system without inheriting the registration office's resource bookkeeping. Keep that possibility in mind when drawing your diagram.

## Stage 1 — Opening day (25 marks; about 30 minutes)
Every workshop has a code, a fixed advertised price in credits, and a limited number of places. Visitors initially use visit passes. One successful admission spends one visit, regardless of the advertised price. A visitor may hold a pass with no visits left.

The receptionist submits an admission request and reports its outcome. A pass can be reused across different workshops. Two passes with equal identifiers are still distinct credentials; identifiers are labels, not a key for merging their state.

A successful admission uses exactly one place and redeems the visitor's pass exactly once. A full workshop refuses the request before trying the pass. An exhausted pass does not take a place. A full workshop does not spend a visit. A request must never partially succeed.

The Hub also permits a credential to be redeemed directly at a service counter, without consuming a workshop place. The same credential rules apply there. Workshop admission uses that same redemption behavior; it must not maintain a second set of membership rules.

**Validity rules**
- Identifiers and workshop codes must be non-null and not blank according to String.isBlank(). Preserve valid strings exactly, including surrounding spaces.
- Initial visits and workshop capacity may be zero, but not negative.
- Workshop prices must be strictly positive.
- Invalid constructor arguments cause IllegalArgumentException.
- A redemption request with price zero or below causes IllegalArgumentException before anything else, even for an exhausted credential. Exception messages for invalid arguments are not assessed.

**Business refusals**
The names and messages are part of the client's contract:
- AdmissionException is the checked failure family and preserves a supplied message.
- PassRejectedException belongs to that family and reports: `Pass <id> cannot be used`.
- FullWorkshopException belongs to that family and reports: `Workshop <code> is full`.
- A full workshop takes precedence when both the workshop and the credential would refuse.
- All valid requests either succeed or report a failure in that checked family. Do not turn an ordinary refusal into an unchecked exception.

The desk processes a batch in array order. It returns one result string for every entry and continues after a business refusal:
- Success: `ADMITTED <id> to <code>`
- Refusal: `REJECTED: <exception-message>`

An empty batch returns an empty array. Duplicate references are processed separately. No operation prints to the console. Assume non-null method arguments and array elements unless a rule above says otherwise.

## Stage 2 — Household credit wallets (25 marks; about 25 minutes)
The finance office adds credit wallets. A wallet starts with a nonnegative balance and accepts strictly positive top-ups. Invalid amounts cause IllegalArgumentException without changing the balance.

A wallet pass is linked to an existing wallet. Redeeming that pass costs the requested price in credits. If the wallet cannot cover the whole price, the pass refuses and leaves the balance unchanged. Spending exactly the remaining balance is valid.

Several passes may use the same wallet. A top-up or successful purchase through any one of them affects what all the others can afford. Distinct wallets must stay independent, even when their passes have equal identifiers. Creating a pass must not copy the wallet's balance or spend any of it. A null wallet is an invalid constructor argument.

The existing visit-pass behavior remains unchanged. The finance office tops up wallets, not all kinds of credentials indiscriminately.

**Change checkpoint:** Did you have to teach the receptionist or workshop how a wallet works? Explain any changes you made to them.

## Stage 3 — Two new arrangements (25 marks; about 25 minutes)
### Sponsor credentials
A SponsorPass has a valid identifier and permits unlimited redemptions at any strictly positive requested price. It has no visit allowance or linked wallet. It must still reject invalid redemption prices consistently with the existing credentials.

### Promotional sleeves
A visitor may put ANY existing credential in a DiscountPass sleeve. Each sleeve reduces the requested price by 3 credits, with a minimum resulting price of 1 credit. The displayed identifier remains the original credential's identifier.

A sleeve does not create a new wallet, replenish visits, or create an independent entitlement. Using the original credential or another sleeve around it must observe the same underlying resources. A refusal by the enclosed credential must leave everything unchanged.

A sleeve may itself be placed inside another sleeve. Each applies its reduction once: a price of 7 becomes 4 and then 1 through two sleeves. A sleeve around a visit pass still spends one visit; a sleeve around a sponsor credential remains usable repeatedly.

An invalid original requested price must be rejected before any promotional adjustment. A null enclosed credential is an invalid constructor argument.

**Change checkpoint:** Were you able to add both arrangements while keeping the existing admission workflow intact? Record which behaviors are reused, which differ, and who owns each decision.

## Stage 4 — Substitute and audit (15 marks; about 15 minutes)
Run every group again. Check these situations deliberately:
- A cheap request follows a refusal by the same pass.
- A failed request is followed by a top-up and retry.
- A full workshop receives a discounted pass backed by a shared wallet.
- The same visit pass appears twice in a batch.
- Two sleeves share one original pass.
- The caller knows only the common Pass role, rather than a concrete scheme.

Write brief answers alongside your diagram:
1. State the promises a client may rely on when it knows only Pass. Include invalid inputs and failure-side effects.
2. Explain why the client can use every credential arrangement under those promises, even though their resource rules differ.
3. Identify one place where an object makes its own decision after receiving a request, and one harmless read-only query.
4. If the desk first checked a balance or remaining-visit count before submitting a request, what knowledge would it acquire that would make the next scheme harder to add?
5. A proposal would place a public topUp operation on every Pass, with visit passes throwing UnsupportedOperationException. Would that honor a contract promising that every positive top-up succeeds? Justify your answer; do not implement the proposal.

No particular diagram is the answer key. Your design will be reviewed for coherent responsibilities, useful shared behavior, substitutability and the size of changes between stages. Small, justified refactoring is acceptable. Avoid abstractions for features the story does not need.

## Client contract — names used by the tests
This fixes the external vocabulary, not your fields or internal hierarchy. All listed operations are public. Methods are instance methods except the desk's batch operation. Business operations declare AdmissionException (or compatible narrower checked types). No unrelated checked exceptions may escape.

| Public type | Construction / operations | Meaning |
|---|---|---|
| Pass | String id(); void use(int price) | The common credential role; no public construction is required. use may throw AdmissionException. |
| VisitPass | VisitPass(String id, int visits); int remaining() | Usable wherever Pass is expected; remaining reports unspent visits. |
| Workshop | Workshop(String code, int capacity, int price); String code(); int seatsLeft(); void admit(Pass pass) | admit may throw AdmissionException. The price and code remain fixed. |
| AdmissionDesk | static String[] admitAll(Workshop workshop, Pass[] passes) | Handles AdmissionException internally. |
| AdmissionException | AdmissionException(String message) | Checked base failure family; getMessage preserves message. |
| PassRejectedException | PassRejectedException(String id) | The pass-refusal message above. |
| FullWorkshopException | FullWorkshopException(String code) | The capacity-refusal message above. |
| Wallet | Wallet(int credits); int balance(); void topUp(int credits) | The shared finance resource. |
| WalletPass | WalletPass(String id, Wallet wallet) | Usable wherever Pass is expected. |
| SponsorPass | SponsorPass(String id) | Usable wherever Pass is expected. |
| DiscountPass | DiscountPass(Pass original) | Usable wherever Pass is expected. |

You may introduce additional public/private helpers and types. The table is not a list of fields to implement. Do not expose internal representation just to satisfy it.

## Checking your work
Use a terminal inside mock_3. Put your Java files beside TestMock.java, without package declarations.

Compile:
```text
javac -d out *.java
```

Run stages:
```text
java -cp out TestMock 1
java -cp out TestMock 2
java -cp out TestMock 3
java -cp out TestMock all
```

Each numbered group tests that stage and assumes its prerequisites are implemented. Group 4 checks common behavioral contracts across the completed arrangements. You can run it separately with `java -cp out TestMock 4`.

The test file uses reflection solely so it can compile before your future-stage classes exist and so it does not force an interface-versus-abstract-class declaration for Pass. Missing types or public operations are reported as failures when their stage is run. Do not imitate the test harness's reflection in your solution.

The untouched folder contains no solution classes, so tests are expected to fail until you implement the required stage. The runner does not require JUnit or the -ea flag. It returns a nonzero exit status when a selected test fails.

Tests check behavior and public type compatibility; they do not assign exam marks or prove the quality of your architecture. The remaining **10 marks** are for validation, encapsulation, sensible reuse and clear code across the stages. Diagram and written reasoning are assessed with Stage 4.

Commit your own work after each stage if possible, then push when ready for review. Keep generated .class files and the out directory out of your submission.

