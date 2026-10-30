# Practice PE 1 (Library Loan) — Task 2
CS2030S Programming Methodology II

NATIONAL UNIVERSITY OF SINGAPORE  
SCHOOL OF COMPUTING  

Oct 2026  
Time Allowed: 90 minutes  

---

## INSTRUCTIONS TO CANDIDATES

1. Continue from Task 1.  Marks for this task are indicated in the paper.  We
   may deduct marks for style violations.

   ** IMPORTANT **
   - MAKE SURE YOU HAVE SUBMITTED TASK 1
   - MAKE SURE YOU PASSED ALL TEST CASES IN TASK 1 BEFORE PROCEEDING TO TASK 2
   - TASK 2 WILL NOT BE GRADED IF YOU FAILED ANY TEST CASE IN TASK 1

2. Only the files directly under your the task2 directory will be graded.

3. Make sure your program compiles before submission:

   ```bash
   javac -Xlint:unchecked -Xlint:rawtypes *.java
   ```

---

## Background

We want to provide a different kind of loan items as well as some changes to 
existing printing and fine computation.

### DVD

A DVD
- has an additional attribute of the movie duration in minutes (an int).
- cannot be renewed.

Given a DVD loan with `<name>`, the number of days before the item is due 
`<days>`, and the movie duration `<duration>`, the status of the DVD loan item 
is the following string.

```
<base status> | duration <hour> hours <min> mins
```

Given `<duration>` in mins, we can compute `<hour>` and `<min>` using the 
following formula.

```java
hour = duration / 60;
min = duration % 60;
```

NOTE: We will always use "hours" even if `<hour>` is equal to 0 or 1.  We 
will always use "mins" even if `<min>` is equal to 0 or 1.  We will refer 
to this string format also as `<item status>`.

### Textbook

A textbook is an open-shelf book that
- has an additional attribute of the course (a string).
- can only be renewed one time.

Given a textbook loan with `<name>`, the number of days before the item is 
due  `<days>`, and the course `<course>`, the status of the textbook loan item 
is the following string.

- If the item has been renewed.
   ```
   <base status> | renewed <renew> times | for <course>
   ```
- If the item has not been renewed.
   ```
   <base status> | no renewal | for <course>
   ```

NOTE: We will always use "times" even if `<renew>` is equal to 0 or 1.  We 
will refer to this string format also as `<item status>`.

---

## (5 marks) Changes

Implement the methods `addDVD` and `addTextbook` to add DVD and textbook.

| Method Name  | addDVD                                                   |
| ------------ | -------------------------------------------------------- |
| Description  | Add a dvd to the library collection.                     |
| Parameters   | Takes three inputs: `name` (string), `days` (integer),   |
|              | and `duration` (integer) which is the duration of the    |
|              | movie in minutes.  All inputs are guaranteed to be       |
|              | valid inputs.                                            |
| Return       | A string `Loaned: <item status>`.                        |

| Method Name  | addTextbook                                              |
| ------------ | -------------------------------------------------------- |
| Description  | Add a textbook to the library collection.  The textbook  |
|              | is initially not renewed.                                |
| Parameters   | Takes two inputs: `name` (string), `days` (integer),     |
|              | and `course` (string) which is the course using the      |
|              | textbook.  All inputs are guaranteed to be valid inputs. |
| Return       | A string `Loaned: <item status>`.                        |

Modify the way item status is printed and the way the fine is computed.

| Entities     | Print Change                                             |
| ------------ | -------------------------------------------------------- |
| Loan Item    | Before: `<name> (<day> days)`                            |
|              | After:                                                   |
|              |   if item is overdue => `<name> | overdue by <due> days` |
|              |   otherwise          => `<name> | due in <day> days`     |
| Open Shelf   | If item is renewed:                                      |
|              |   before => `<base status> {<renew> times}`              |
|              |   after  => `<base status> | renewed <renew> times`      |
|              | Otherwise:                                               |
|              |   before => `<base status>`                              |
|              |   after  => `<base status> | no renewal`                 |

| Fine         | Change                                                   |
| ------------ | -------------------------------------------------------- |
| Loan Item    | Before: `2 * overdue`                                    |
|              | After: if overdue is less than or equal to 2 days, $0    |
|              |        otherwise, $3 per day.                            |

NOTE: Once we computed the number of days an item is due, we can compute the 
new fine as follows.

```java
if (due <= 2) {
  fine = 0;
} else {
  fine = 3 * due;
}
```

**You may add additional methods as needed.**

### Sample Run
```text
jshell> 
jshell> LibraryLoan library = new LibraryLoan(7)
library ==> LibraryLoan@########
jshell> library.addDVD("Harry Porter and the Dining Philosophers", 2, 121)
$.. ==> "Loaned: Harry Porter and the Dining Philosophers | due in 2 days | duration 2 hour 1 mins"
jshell> library.addTextbook("The ABC of Programming", -2, "CS2030S")
$.. ==> "Loaned: The ABC of Programming | overdue by 2 days | no renewal | for CS2030S"
jshell> library.addBook("PE1 Question", 0)
$.. ==> "Loaned: PE1 Question | due in 0 days | no renewal"
jshell> library.addDVD("Star Wars Episode 1010", -3, 135)
$.. ==> "Loaned: Star Wars Episode 1010 | overdue by 3 days | duration 2 hour 15 mins"
jshell> library.addTextbook("Algorithms for Babies", 1, "CS2040S")
$.. ==> "Loaned: Algorithms for Babies | due in 1 days | no renewal | for CS2040S"
jshell> library.addBook("PE2 Question", 30)
$.. ==> "Loaned: PE2 Question | due in 30 days | no renewal"
jshell> System.out.println(library.allLoan())
0: Harry Porter and the Dining Philosophers | due in 2 days | duration 2 hour 1 mins
1: The ABC of Programming | overdue by 2 days | no renewal | for CS2030S
2: PE1 Question | due in 0 days | no renewal
3: Star Wars Episode 1010 | overdue by 3 days | duration 2 hour 15 mins
4: Algorithms for Babies | due in 1 days | no renewal | for CS2040S
5: PE2 Question | due in 30 days | no renewal

jshell> System.out.println(library.overdueLoan())
1: The ABC of Programming | overdue by 2 days | no renewal | for CS2030S
3: Star Wars Episode 1010 | overdue by 3 days | duration 2 hour 15 mins

jshell> library.renew(0)
$.. ==> "Failed to Renew: Harry Porter and the Dining Philosophers | due in 2 days | duration 2 hour 1 mins"
jshell> library.renew(1)
$.. ==> "Failed to Renew: The ABC of Programming | overdue by 2 days | no renewal | for CS2030S"
jshell> library.renew(3)
$.. ==> "Failed to Renew: Star Wars Episode 1010 | overdue by 3 days | duration 2 hour 15 mins"
jshell> library.renew(4)
$.. ==> "Successfully Renewed: Algorithms for Babies | due in 15 days | renewed 1 times | for CS2040S"
jshell> library.renew(4)
$.. ==> "Failed to Renew: Algorithms for Babies | due in 15 days | renewed 1 times | for CS2040S"
jshell> library.totalFines()
$.. ==> 9
```

### Testing

Run the test cases for Task 2 by running the following command.

```shell
java -cp test.jar:. Test4
```

---

## Submit

Submit your Task 2 by running the following command.

```shell
bash submit.sh task2
```

---

END OF PAPER
