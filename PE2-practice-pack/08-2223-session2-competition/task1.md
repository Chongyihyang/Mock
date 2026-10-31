# AY22/23 PE2 — reconstructed local practice

> 90 minutes. The original source files are not included in the PDF. This pack defines a replacement baseline API; it is not an exact reproduction. Read CONTRACT.md with the original text below.

```text
Practical Assessment II (Session II @ 2 PM)                                            CS2030S AY22/23 Sem 2

                             NATIONAL UNIVERSITY OF SINGAPORE

                                     SCHOOL OF COMPUTING
                                  PRACTICAL ASSESSMENT II FOR
                                      Semester 2 AY2022/2023

                                CS2030S Programming Methodology II

                   April 2023                                       Time Allowed 90 minutes




INSTRUCTIONS TO CANDIDATES

   1. This practical assessment consists of one question. The total mark is 20: 2 marks for design; 2 for style; 16
      for correctness. Style and correctness are given on the condition that reasonable efforts have been made
      to solve the given tasks.
   2. This is an OPEN BOOK assessment. You are only allowed to refer to written/printed notes. No online re-
      sources/digital documents are allowed, except those accessible from the PE nodes (peXXX.comp.nus.edu.sg)
      (e.g., man pages are allowed).
   3. You should see the following files/directories in your home directory.
         • Competition.java, Team.java, and Member.java, for you to edit and solve the given task.
         • pristine, that contains a clean copy of the files above for your reference.
         • Test1.java, Test2.java, and Test3.java, for testing your code.
         • cs2030s/fp, that contains the cs2030s.fp package, including Maybe.java.
         • checkstyle.sh, checkstyle.jar, and cs2030s_checks.xml, for style checks.
         • StreamAPI.md and MapAPI.md, for references to Java’s Stream and Map API.
   4. Solve the programming tasks by editing Competition.java, Team.java, and Member.java. You can
      leave the files in your home directory and log off after the assessment is over. There is no separate step
      to submit your code.
   5. Only the files directly in your home directory will be graded. Do not put your code under a subdirectory.
   6. Write your student number on top of EVERY FILE you edited as part of the @author tag. Do not write
      your name.
   7. To compile your code, run javac -Xlint:unchecked -Xlint:rawtypes *.java. You can also com-
      pile the files individually if you wish.
   8. You can run each test individually. For instance, run java Test1 to execute Test1.
   9. To check the style of your code, run ./checkstyle.sh. You can also check the style of individual file
      with java -jar checkstyle.jar -c cs2030_checks.xml <FILENAME>.

IMPORTANT: If the submitted classes Competition.java, Team.java, and Member.java cannot be com-
piled, 0 marks will be given for the corresponding task. Make sure your program compiles by running

          javac -Xlint:unchecked -Xlint:rawtypes *.java


before submission.

You have been given three classes: Competition, Team, and Member.
Members. Each Member has an email address and a phone number. Members can update their email address
and their phone number using the updateEmail and updatePhoneNumber methods respectively.
Team. A Team keeps track of a team ID, the number of points, one or more members of the team, and a boolean
flag to indicate if the team is disqualified. Points can be added to a team using the score method and removed
from a team using the penalize method. Points can also be transferred from one team to another using the
transferTo method. A team can have negative points. Two Teams can be combined with the mergeWith
method. The team members and points are combined into the merged Team. The team ID of the merged team is
the team ID of the first team. Finally, a team can be disqualified by calling its disqualified method. We can
check if a team has been disqualified with the isDisqualified method.
Competition. The Competition class keeps track of all teams using a Map class, with the team ID as keys.
The method leaderBoard returns a string, listing all the non-disqualified teams in the competition, sorted in
decreasing order of points. The method totalPoints returns the total points by all non-disqualified teams in
the competition. You can add points to or deduct points from a team using the score and penalize methods.
You can disqualify a team using the disqualify method. Points can be transferred between two teams using
the transfer method. The Competition class places a rule that points can only be transferred from a team
with positive points to a team with negative points. If this rule is not satisfied, transfer does nothing. Finally,
non-disqualified teams can be combined with mergeTeams method. If one of the teams is disqualified, then
mergeTeams does nothing. If the methods above are applied to a non-existing team, methods do nothing.

You should read through the files Team.java, Member.java, and Competition.java to understand what
methods they have, the parameters and return value of the methods, and their behavior in detail.
The teams are stored using a Map (a collection of key-value pairs) as you have seen in Lab 5: Maybe. The keys in
a map are unique, so you cannot have duplicate keys. To put a key-value pair into a map m use m.put(K key,
V value), and to remove a key-value pair from a map m use remove(Object key). Although Map may throw
exceptions, you do not have to handle exceptions and may assume that all test cases will not cause exceptions.

Task 1 (4 marks): Rewrite Team.java and Member.java classes to be immutable.
All methods that mutate the class should return a new object. You are not allowed to add new methods in
Team.java and Member.java. As Team is dependent on Member we advise that you start by modifying the
Member class first, followed by the Team class. Make small changes at a time and make sure that your code
compiles after every change.
Note that for Team::transfer, you are expected to return a Pair of teams (first, second) such that
first corresponds to the current team and second corresponds to the destination team after the transfer.

The first two tests Test1.java and Test2.java will test the immutability of Member.java, and Team.java
respectively. Note that the given skeleton does not correctly pass all these tests. As in previous labs and PE1,
make sure all OO principles, including LSP, tell-don’t-ask, and information hiding, are adhered to, where appli-
cable.

Task 2 (12 marks): Remove all loops, conditional statements, and null from Competition
The Competition class has multiple methods that have loops, conditional statements, and null. Remove these
from the methods in Competition.java, except from Competition::equals, whilst retaining all of the
functionality of Competition.java, You may use Java Stream and our Maybe to achieve this result.
Note that you are also not allowed to use block statements in your lambdas and you are not allowed to add new
methods in Competition.java. For instance, the following is not allowed x.map(x -> { .. }).
To begin, you must change the return type of findTeam into Maybe<Team>. Make small changes at a time and
make sure that your code compiles after every change.
The last test Test3.java will test the functionality of Competition.java. It correctly works on the mutable
implementation you have been given.

                                              END OF PAPER
```
