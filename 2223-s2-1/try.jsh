/open Array.java
/open Task.java
/open NoLimit.java
/open WithLimit.java
/open LimitAndAssignee.java
/open WrongTaskTypeException.java
/open TaskList.java
TaskList list = new TaskList("inputs/Sample.txt");
list.printTaskDetails();
list.completeTask(2);
list.printTaskDetails();
list.getRewardPoints();
