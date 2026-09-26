# Practice PE 1 (Library Loan) — Task 1
CS2030S Programming Methodology II

NATIONAL UNIVERSITY OF SINGAPORE  
SCHOOL OF COMPUTING  

Oct 2026  
Time Allowed: 90 minutes  

---

## INSTRUCTIONS TO CANDIDATES

1. Marks for each question are indicated below.  We may deduct marks for
   style violations and design issues.

2. This is a CLOSED-BOOK assessment.  You are only allowed to refer to one (1) 
   double-sided A4-size paper.

3. Only the files directly under your the `task1` and `task2` directories
   will be graded.

4. Some skeletons may be provided for you.  You may add new files if you
   need to.

5. Write your student number on top of EVERY FILE you created or edited as
   part of the `@author` tag.  Do not write your name.

6. You are not allowed to add any `import` statement unless otherwise 
   specified.  You are not allowed to use any `import` statement on files 
   you created.

7. To compile your code, run:
   ```shell
   javac -Xlint:unchecked -Xlint:rawtypes *.java
   ```

IMPORTANT: Any code you wrote that cannot be compiled may result in 0 marks
being given for the question(s) that depend on it.  Make sure your program
compiles before submission.

---

## Preliminary

Ensure that the following files are present in your home directory:

- `LibraryLoan.java`
- `Seq.java`

Ensure that the following directories are also present:

- `pristine`

Additionally, make sure that the following files are in your home directory:

- `task1.md`     — unencrypted question for Task 1  
- `task2.secret` — encrypted question for Task 2  
- `submit.sh`    — submission script  
- `test.jar`     — test jar file for Task 1 and Task 2  

**You may add new files as needed**

---

## Provided Classes

The following files have been provided for you.  DO NOT CHANGE THESE FILES 
unless otherwise specified.

### `LibraryLoan`

This is the main driver class for the problem.  We will test each question 
with the following sequence of commands.

```shell
rm -f *.class
javac -Xlint:unchecked -Xlint:rawtypes LibraryLoan.java
```

### `Seq<T>`

The `Seq<T>` is a generic class that provides a simple sequence data structure 
with limited capacity.

The constructor for `Seq` takes in an `int` parameter `size`, which is the 
maximum length of the sequence.  The class provides the following methods

| Method Name  | set                                                      |
| ------------ | -------------------------------------------------------- |
| Description  | Add an object of type `T` to the specified index.        |
| Parameters   | The index for the item.                                  |
|              | An object of type `T`.                                   |

| Method Name  | get                                                      |
| ------------ | -------------------------------------------------------- |
| Description  | Return the object at the specified index.                |
| Parameters   | The index of the item to be retrieved.                   |
| Return       | The item at the index (if any).  May throw error.        |

IMPORTANT: You are required to use `Seq`.  You are not allowed to use array or 
other data structure for list/sequence/collections.  Any use of array may be 
penalized.

---

## Background

You are hired by NUS libraries to help manage library loans.  Items that can 
loaned may include books, audio/visual materials, reserved books, etc.  Some 
items can be renewed multiple times, some can be renewed  once, and some 
cannot even be renewed.

We want to know how long until an item is overdue.  If an item is already 
overdue, then this number is negative.  If this number is 0, then the item is 
due today.  If an item is due, there is a fine associated with the item.  The 
fine is computed based purely on the number of days the item has been overdue.


### Loan List

A loan list is an ordered sequence of loans with a name (a string).  A 
loan is ordered in the order the the loan is created.  The order of the first 
loan is 0.  For simplicity, the maximum number of loans in a given list is 
known in advanced.


### Loan Item

All loan item in a loan list has a name (a string) and the number of days 
before the item is due (an int).  The number of days may be negative if the 
item is due.  We shall represent a string representation of a loan depending 
on whether the item is already due or not.

We can check if any loan items is overdue and we can try to renew a loan item.
However, not all loan items can be completely renewed (e.g., an item already 
overdue, some reserved book, maximum renewal limit, etc).  Recap that the 
overdue days determine the fine for the item.

Consider a loan list with `<name>` and `<day>` such that if `<day>` is 
negative, it is overdue.  Its string representation is the following.

```
<name> (<day> days)
```

NOTE: We use the term "days" even if `<day>` is 0, 1, or negative.  We 
will refer to this string format as `<base status>`.


### Open Shelf Book

The simplest kind of loan item is an open-shelf book.  All kinds of open-shelf 
book can be renewed at least 1 time.  An open-shelf book itself can be renewed 
up to 3 times.

Given a book loan with a name `<name>` and the number of days before the item 
is due `<days>`, the status of the open-shelf book is a string that depends on 
the number of times the item has been renewed.  The string representation of 
an open-shelf book that has been renewed `<renew>` number of times is as 
follows.

- If the item has been renewed.
  ```
  <base status> {<renew> times}
  ```
- If the item has not been renewed.
  ```
  <base status>
  ```

NOTE: We will always use "times" even if `<renew>` is equal to 0 or 1.  We 
will refer to this string format as `<item status>`.

---

## 1. (3 mark) Adding Books

Write the `LibraryLoan` constructor and `addBook`.

| Constructor  | LibraryLoan                                              |
| ------------ | -------------------------------------------------------- |
| Description  | The constructor for the class `LibraryLoan`.             |
| Parameters   | Takes one input: `numLoan`.  It is a positive integer    |
|              | representing the maximum number of loan in the list.     |

| Method Name  | addBook                                                  |
| ------------ | -------------------------------------------------------- |
| Description  | Add a book to the library collection.  The book is       |
|              | initially not renewed.                                   |
| Parameters   | Takes two inputs: `name` (string) and `days` (integer).  |
|              | `name` is the name of the book and guaranteed to be a    |
|              | non-empty string.  `days` is the number of days until    |
|              | loan is due and maybe negative.                          |
| Return       | A string `Loaned: <item status>`.                        |

We may create a library loan list with more loans (i.e., `numLoan`) than what 
we will add later.  However, we will not create a library loan list with fewer 
loans (i.e., `numLoan`) than what we will add later.

### Sample Run
```text
jshell> LibraryLoan library = new LibraryLoan(4)
library ==> LibraryLoan@########
jshell> library.addBook("Harry Porter and the Dining Philosophers", 2)
$.. ==> "Loaned: Harry Porter and the Dining Philosophers (2 days)"
jshell> library.addBook("The ABC of Programming", -2)
$.. ==> "Loaned: The ABC of Programming (-2 days)"
jshell> library.addBook("PE1 Question", 0)
$.. ==> "Loaned: PE1 Question (0 days)"
```

### Testing

Run the test cases for Questions 1 by running the following command.

```shell
java -cp test.jar:. Test1
```

---

## 2. (4 marks) Printing Loans

Implement the methods `allLoan` and `overdueLoan` to print information about 
the current loans.

| Method Name  | allLoan                                                  |
| ------------ | -------------------------------------------------------- |
| Description  | Returns string corresponding to the status of all loans  |
|              | in the order of creation separated by newline `"\n"`.    |
| Parameters   | none                                                     |
| Return       | Returns a string of the format shown below.              |
|              | ```                                                      |
|              | 0: <item status>                                         |
|              | 1: <item status>                                         |
|              | 2: <item status>                                         |
|              |   :                                                      |
|              | ```                                                      |

| Method Name  | overdueLoan                                              |
| ------------ | -------------------------------------------------------- |
| Description  | Returns string corresponding to the status of all        |
|              | overdue loan in the order of creation separated by       |
|              | newline `"\n"`.                                          |
| Parameters   | none                                                     |
| Return       | Returns a string of the format shown below.              |
|              | ```                                                      |
|              | 2: <item status>                                         |
|              |   :                                                      |
|              | ```                                                      |
|              | `item` at index 2 above is an overdue item.              |

NOTE: You do not have to care about efficiency.  Operating on a string 
directly is inefficient, but it is fine.  If you have a sequence of strings 
with size `size`, you can construct the string as follows.

```java
String res = "";
for (int i = 0; i < size; i += 1) {
  res += i + ": " + seq.get(i) + "\n";  // \n is newline
}
return res;
```

**You may add additional methods as needed.**

### Sample Run
```text
jshell> LibraryLoan library = new LibraryLoan(4)
library ==> LibraryLoan@########
jshell> library.addBook("Harry Porter and the Dining Philosophers", 2)
$.. ==> "Loaned: Harry Porter and the Dining Philosophers (2 days)"
jshell> System.out.println(library.allLoan())
0: Harry Porter and the Dining Philosophers (2 days)

jshell> System.out.println(library.overdueLoan())

jshell> library.addBook("The ABC of Programming", -2)  // overdue
$.. ==> "Loaned: The ABC of Programming (-2 days)"
jshell> System.out.println(library.allLoan())
0: Harry Porter and the Dining Philosophers (2 days)
1: The ABC of Programming (-2 days)

jshell> System.out.println(library.overdueLoan())
1: The ABC of Programming (-2 days)

jshell> library.addBook("PE1 Question", 0)
$.. ==> "Loaned: PE1 Question (0 days)"
jshell> System.out.println(library.allLoan())
0: Harry Porter and the Dining Philosophers (2 days)
1: The ABC of Programming (-2 days)
2: PE1 Question (0 days)

jshell> System.out.println(library.overdueLoan())
1: The ABC of Programming (-2 days)
```

### Testing

Run the test cases for Questions 2 by running the following command.

```shell
java -cp test.jar:. Test2
```

---

## 3. (3 marks) Renew and Paying Fines

Implement the methods `renew` and `totalFines` to renew a loan and to compute 
the total fines to be paid.

Any loan item can only be renewed if the item is not yet overdue.  Some loan 
items have their own condition for renewal.  For open-shelf book, the item can 
only be renewed up to 3 times.  If a loan item is successfully renewed, the 
loan period is extended by two weeks (i.e., add 14 to the due date).

| Method Name  | renew                                                    |
| ------------ | -------------------------------------------------------- |
| Description  | Renew the item specified by the given loan order.        |
| Parameters   | Takes one input: `loanOrder` (integer).  This is the     |
|              | order of the loan item in the sequence.                  |
| Return       | Returns the following string if the renewal successful.  |
|              | ```                                                      |
|              | Successfully Renewed: <item status>                      |
|              | ```                                                      |
|              | Returns the following string if the renewal failed.      |
|              | ```                                                      |
|              | Unable to Renew: <item status>                           |
|              | ```                                                      |

IMPORTANT: `<item status>` is the status AFTER the item is renewed (if 
successful).

| Method Name  | totalFines                                               |
| ------------ | -------------------------------------------------------- |
| Description  | Returns the total fines for all books.  A fine of $2 is  |
|              | imposed for each day an item is overdue.                 |
| Parameters   | none                                                     |
| Return       | Returns the total fine as integer.                       |

**You may add additional methods as needed.**

### Sample Run
```text
jshell> LibraryLoan library = new LibraryLoan(4)
library ==> LibraryLoan@########
jshell> library.addBook("Harry Porter and the Dining Philosophers", 2)
$.. ==> "Loaned: Harry Porter and the Dining Philosophers (2 days)"
jshell> library.addBook("The ABC of Programming", -2)  // overdue
$.. ==> "Loaned: The ABC of Programming (-2 days)"
jshell> library.addBook("PE1 Question", 0)
$.. ==> "Loaned: PE1 Question (0 days)"
jshell> System.out.println(library.allLoan())
0: Harry Porter and the Dining Philosophers (2 days)
1: The ABC of Programming (-2 days)
2: PE1 Question (0 days)

jshell> System.out.println(library.overdueLoan())
1: The ABC of Programming (-2 days)

jshell> library.renew(1)  // overdue, cannot renew
$.. ==> "Failed to Renew: The ABC of Programming (-2 days)"
jshell> library.renew(2)
$.. ==> "Successfully Renewed: PE1 Question (14 days) {1 times}"
jshell> System.out.println(library.allLoan())
0: Harry Porter and the Dining Philosophers (2 days)
1: The ABC of Programming (-2 days)
2: PE1 Question (14 days) {1 times}

jshell> System.out.println(library.overdueLoan())
1: The ABC of Programming (-2 days)

jshell> library.renew(2)
$.. ==> "Successfully Renewed: PE1 Question (28 days) {2 times}"
jshell> library.renew(2)
$.. ==> "Successfully Renewed: PE1 Question (42 days) {3 times}"
jshell> library.renew(2)  // max of 3 times
$.. ==> "Failed to Renew: PE1 Question (42 days) {3 times}"
jshell> System.out.println(library.allLoan())
0: Harry Porter and the Dining Philosophers (2 days)
1: The ABC of Programming (-2 days)
2: PE1 Question (42 days) {3 times}

jshell> System.out.println(library.overdueLoan())
1: The ABC of Programming (-2 days)

jshell> library.totalFines()  // 2 days overdue * $2 = $4
$.. ==> 4
jshell> LibraryLoan library2 = new LibraryLoan(4)
library2 ==> LibraryLoan@########
jshell> library2.addBook("Book1", -4)
$.. ==> "Loaned: Book1 (-4 days)"
jshell> library2.addBook("Book2", -3)
$.. ==> "Loaned: Book2 (-3 days)"
jshell> library2.addBook("Book3", -2)
$.. ==> "Loaned: Book3 (-2 days)"
jshell> library2.addBook("Book4", -1)
$.. ==> "Loaned: Book4 (-1 days)"
jshell> library2.totalFines()  // 10 days overdue * $2 = $20
$.. ==> 20
```

### Potential Changes

- We may have a different kind of items (e.g., different renewal, reserved 
  item for a course, never overdue, etc).
- We may change the way we represent the status of an item.
- We may change the way we define an item with overdue date.
- We may change the way we compute fines for each item.
- We may change the way we renew an item.
- We may change the way we check overdue for each item.

There may be other changes.  You are advised to use an abstract class or 
interface to abstract an item and determine the minimal methods required for 
the abstract class or interface.  If you are using an abstract class or 
interface, you are advised to name your abstract class or interface `Items`.
