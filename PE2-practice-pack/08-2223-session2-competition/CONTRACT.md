# Local reconstruction contract
The PDF omits the original source API. This baseline is a practice adaptation, not official files.
- `Member(String email, String phoneNumber)`; getters, `updateEmail`, `updatePhoneNumber` and value equality are supplied.
- `Team(String id, int amount, List<Member> people)`; the supplied public methods define this pack's API. `getId()` is the identifier getter.
- Task 1: existing mutation methods return new objects; input lists and returned lists must not allow external mutation of stored membership. Preserve flags across copies. `transferTo` returns source first, destination second. `Pair.first()`/`second()` retrieve them.
- `mergeWith` retains the receiver's ID/state; appends members/owners in receiver-then-other order, preserving duplicates.
- Task 2: `Competition` remains mutable. Its methods must store the new objects returned by immutable operations. Change `findTeam` to return `Maybe<Team>`; keep all other public signatures.
- Manager constructor accepts a list with distinct IDs. Map iteration is ID order. Leaderboard excludes disqualified teams; descending points, tied IDs alphabetical. Every listing row ends in `\n`; empty listing is empty string.
- Manager merge consumes the `from` entry into the `to` entry, removing `from`. Source and destination IDs in a transfer/merge are distinct. Amounts are nonnegative; overdrafts/negative points allowed.
- Score/penalize may update a disqualified team. Transfer uses only the positive-source/negative-target condition stated in the PDF.
- No loops, conditional statements, ternary expressions, explicit null, or block lambdas in manager after Task 2 (equals excepted). No new methods. Constructor setup and provided support classes need not be rewritten.
- `Maybe` and `Pair` are reconstructed provided infrastructure. The helpers are not your exercise solutions.
- Tests 1/2 target immutability and intentionally fail on baseline. Test 3 passes on the mutable baseline and must keep passing after refactoring.

Test4 checks the final Maybe lookup API; it only compiles after Task 2.
