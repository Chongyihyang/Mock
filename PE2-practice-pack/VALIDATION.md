# Validation
- 199 runtime assertions across 27 Java test programs passed against separate private validation implementations. Those implementations are not included.
- Seven invalid generic programs were rejected after compiling the valid test APIs.
- All three AY22/23 mutable baselines pass Test3. Their immutability tests fail as intended.
- All test jars contain only Test classes and CS2030STest; no exercise implementations.
- Tests compiled with unchecked/rawtype warnings enabled on Java 17 without warnings.
- Functional style constraints and exhaustive hidden marking are not certified by this test suite.

Detailed runtime results:
```
01-query Test1: 5 passed; 0 failed
01-query Test2: 5 passed; 0 failed
02-point Test1: 7 passed; 0 failed
03-either Test1: 14 passed; 0 failed
03-either Test2: 11 passed; 0 failed
04-try Test1: 11 passed; 0 failed
04-try Test2: 5 passed; 0 failed
04-try Test3: 4 passed; 0 failed
04-try Test4: 8 passed; 0 failed
05-2324-session1-fruit Test1: 4 passed; 0 failed
05-2324-session1-fruit Test2: 8 passed; 0 failed
05-2324-session1-fruit Test3: 7 passed; 0 failed
06-2324-session2-books Test1: 6 passed; 0 failed
06-2324-session2-books Test2: 8 passed; 0 failed
06-2324-session2-books Test3: 9 passed; 0 failed
07-2223-session1-bank Test1: 5 passed; 0 failed
07-2223-session1-bank Test2: 12 passed; 0 failed
07-2223-session1-bank Test3: 8 passed; 0 failed
07-2223-session1-bank Test4: 2 passed; 0 failed
08-2223-session2-competition Test1: 5 passed; 0 failed
08-2223-session2-competition Test2: 12 passed; 0 failed
08-2223-session2-competition Test3: 10 passed; 0 failed
08-2223-session2-competition Test4: 2 passed; 0 failed
09-2223-session3-cart Test1: 5 passed; 0 failed
09-2223-session3-cart Test2: 13 passed; 0 failed
09-2223-session3-cart Test3: 7 passed; 0 failed
09-2223-session3-cart Test4: 6 passed; 0 failed
```
