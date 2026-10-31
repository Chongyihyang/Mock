# PE2 practice pack
Nine independent exercises reconstructed from the six supplied PDFs. No exercise solutions are included. Original sample solutions were removed from task1.md; sample input/output remains.

| Folder | Focus | Time |
|---|---|---|
| 01-query | Stream filtering, flattening, joining and totals | suggested 45–60 min |
| 02-point | Stream generation and Monte Carlo | suggested 45–60 min |
| 03-either | Two-branch generic abstraction, PECS, map/flatMap/fold | suggested 90 min |
| 04-try | Exception-aware functional abstraction | suggested 90 min |
| 05-2324-session1-fruit | Functional interfaces, PECS, streams, comparison | paper: 120 min |
| 06-2324-session2-books | Functional interfaces, PECS, streams, grouping/search | paper: 120 min |
| 07-2223-session1-bank | Immutability and Maybe/Stream refactoring | paper: 90 min |
| 08-2223-session2-competition | Immutability and Maybe/Stream refactoring | paper: 90 min |
| 09-2223-session3-cart | Immutability and Maybe/Stream refactoring | paper: 90 min |

Work in one folder at a time. Read its README and task1.md, then use `sh test.sh 1`. Do not compile all nine together: they contain deliberately repeated class names.

These papers differ from your PE1 mock format: AY23/24 has eight questions/45 marks and 120 minutes; AY22/23 has two tasks/20 marks and 90 minutes. Nothing in these PDFs specifies unlocking Task2 for the last 40 minutes, so no lock has been added.

The provided PDFs are the sources for the questions; the test files, helper files, starter files and API notes here are reconstructions. In particular AY22/23 expects source files that were not attached, so its contracts are explicitly adapted. Use the original course skeletons if exact historic compatibility is required.

Tests are visible practice tests, not the official hidden marking suite. Passing them does not guarantee full marks. Use your current course rules if they differ from these older papers. Only public/private access modifiers are used in supplied code. Imports are supplied where these FP questions require Java collection/stream APIs.

Suggested order: Query → Point → Either → Try → Fruit → Books → the three refactoring sessions. If streams are new, use the condensed API notes first.
