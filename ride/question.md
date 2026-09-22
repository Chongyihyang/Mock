# Past-Year PE1 Question: Snatch A Ride

Adapted from PE1 of AY 2019/20 Semester 1.

> Practice edition converted from the supplied PDF. Supplied solution listings have been removed. All sample interactions and expected results are retained; JShell result labels and page line numbers have been omitted. The original course-environment setup and references to unavailable `Test1.java`, `Test2.java`, `Test3.java`, and `CS2030STest.java` are replaced by the local testing instructions below. No starter implementations are supplied.

## Background

Snatch Pte Ltd is a transport service provider trying to vie for a place in the public transport arena. Snatch provides three types of ride services:

- **JustRide:** Charges 22 cents per kilometre. The fare is the same regardless of the number of passengers. There is a surcharge of 500 cents if a ride request is issued between 0600 and 0900 hours, both inclusive.
- **TakeACab:** Charges 33 cents per kilometre, plus a booking fee of 200 cents. The fare is the same regardless of the number of passengers. There is no peak-hour surcharge.
- **ShareARide:** The base fare is 50 cents per kilometre. The passengers pay less if they share the ride: the fare is divided by the number of passengers, with any fractional part absorbed by the driver. There is a surcharge of 500 cents for requests between 0600 and 0900 hours, both inclusive. **Clarification of the PDF's wording:** the surcharge is included before dividing the total by the number of passengers.

There are two types of cars under Snatch. A **Cab** can provide only JustRide and TakeACab services. A **PrivateCar** can provide only JustRide and ShareARide services.

A customer issues a ride request specified by the distance of the ride, the number of passengers, and the time of the request. A booking is made when a request is matched with a car under a particular ride service.

To get full marks, your code must be correct and its design extensible. If Snatch adds ride services or car types, or changes the fare structure, your code should require minimal changes.

## Part 1: Request and services

### Request

Implement a `Request` class that encapsulates a request for a ride. Its constructor takes three `int` parameters, in this order:

1. Distance of the ride.
2. Number of passengers.
3. Time of the request.

### Services

Implement `JustRide`, `TakeACab`, and `ShareARide`. Each should provide a `computeFare` method that takes a `Request` and returns the fare in cents.

```text
jshell> new JustRide().computeFare(new Request(20, 3, 1000))
440
jshell> new JustRide().computeFare(new Request(10, 1, 900))
720
jshell> new TakeACab().computeFare(new Request(20, 3, 1000))
860
jshell> new TakeACab().computeFare(new Request(10, 1, 900))
530
jshell> new ShareARide().computeFare(new Request(20, 3, 1000))
333
jshell> new ShareARide().computeFare(new Request(10, 1, 900))
1000
```

Each service should override `toString()` to return its name.

```text
jshell> new JustRide().toString()
"JustRide"
jshell> new TakeACab().toString()
"TakeACab"
jshell> new ShareARide().toString()
"ShareARide"
```

## Part 2: Cars

Implement `Cab` and `PrivateCar`. Their constructors take the licence plate as a `String`, followed by the time in minutes until the driver is available as an `int`.

Each class should override `toString()` to return the car type, licence plate, and waiting time, formatted exactly as below. Note the singular `min` when the waiting time is 1.

```text
jshell> new Cab("SHA1234", 5).toString()
"Cab SHA1234 (5 mins away)"
jshell> new Cab("SHA1234", 1).toString()
"Cab SHA1234 (1 min away)"
jshell> new PrivateCar("SU4032", 4).toString()
"PrivateCar SU4032 (4 mins away)"
jshell> new PrivateCar("SU4032", 1).toString()
"PrivateCar SU4032 (1 min away)"
```

## Part 3: Bookings

Implement `Booking`, which encapsulates a car, a service, and a request. It should implement `Comparable<Booking>`.

Compare bookings by fare, with lower fares ordered first. Break fare ties by waiting time, with shorter waits ordered first. When both fare and waiting time are equal, ties may be broken arbitrarily.

```text
jshell> Comparable<Booking> b = new Booking(new Cab("SHA1234", 5), new JustRide(), new Request(20, 3, 1000));
jshell> Booking b1 = new Booking(new Cab("SHA1234", 3), new JustRide(), new Request(20, 3, 1000));
jshell> Booking b2 = new Booking(new Cab("SBC8888", 5), new JustRide(), new Request(20, 3, 1000));
jshell> Booking b3 = new Booking(new PrivateCar("SU4032", 5), new ShareARide(), new Request(20, 3, 1000));
jshell> b3.compareTo(b2) < 0
true
jshell> b1.compareTo(b3) < 0
false
jshell> b1.compareTo(b2) < 0
true
```

If a booking is created with an incompatible car and service, throw an `IllegalArgumentException`. Pass the required message to its constructor; callers retrieve it with `getMessage()`.

```text
jshell> try {
   ...>   new Booking(new Cab("SHA1234", 5), new ShareARide(), new Request(20, 3, 1000));
   ...> } catch (IllegalArgumentException e) {
   ...>   System.out.println(e.getMessage());
   ...> }
Cab SHA1234 (5 mins away) does not provide the ShareARide service.
```

## Testing your work

The accompanying `TestSnatch.java` reproduces **all 17 displayed results** as automatic checks, plus the sample's compile-time assignment to `Comparable<Booking>`. It contains test code only, not implementations. It does not prescribe the names or kinds of any helper abstractions.

Place your implementation files and `TestSnatch.java` in the same directory, with no package declarations. Use public/private access in your implementation, in keeping with your course restriction.

Compile and run:

```sh
javac -d out *.java
java -cp out TestSnatch
```

You can select a group:

```sh
java -cp out TestSnatch 1  # Request and services: 9 checks
java -cp out TestSnatch 2  # Cars: 4 checks
java -cp out TestSnatch 3  # Bookings: 4 checks
```

**Compilation requires all the public types and operations used by the samples to exist**, even when you select a single group. While completing only Part 1 or Part 2, you can use the corresponding JShell examples above until your remaining declarations are ready to compile with the test file.

Each check reports `PASS` or `FAIL`; failures show the expected and actual values, or the unexpected exception. A complete successful run ends with:

```text
17 passed; 0 failed.
```

The runner exits with status 1 on any failed check and status 2 for invalid arguments. No JUnit or `-ea` flag is required. These are the PDF's samples, not the original course test suite or exhaustive tests. Passing them does not by itself establish extensibility or full correctness.

Follow the CS2030S Java style. On a course machine with the original tools installed, the PDF's style-check command is:

```sh
java -jar ~cs2030s/bin/checkstyle.jar -c ~cs2030s/bin/cs2030_checks.xml *.java
```
