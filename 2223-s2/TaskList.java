import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * RECONSTRUCTED STARTER, NOT AN OO SOLUTION OR ORIGINAL EXAM SOURCE.
 * Refactor this deliberately procedural implementation as directed in question.md.
 */
public class TaskList {

  private int loaded;
  private int rewardPoints;
  private Array<Task> tasks;

  private Task getTask(int idx) {
    return this.tasks.get(idx);
  }

  public TaskList() {
    Scanner input = new Scanner(System.in);
    try {
      this.loadTasks(input);
    } catch (WrongTaskTypeException e) {
      System.out.println(e.getMessage());
    }
  }

  public TaskList(String filename) {
    try (Scanner input = new Scanner(new File(filename), "UTF-8")) {
      this.loadTasks(input);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Input file unavailable: " + filename, e);
    } catch (WrongTaskTypeException e) {
      System.out.println(e.getMessage());
    }
  }

  private void loadTasks(Scanner input) throws WrongTaskTypeException {
    Integer count = Integer.parseInt(input.nextLine());
    tasks = new Array<>(count);
    for (int i = 0; i < count; i++) {
      this.createTask(input.nextLine(), i); 
    }
  }

  private void createTask(String line, int index) throws WrongTaskTypeException {
    String[] fields = line.split(",", -1);
    int type = Integer.parseInt(fields[0]);
    Task tmp;
    if (type == 0) {
      tmp = new NoLimit(fields[1]);
    } else if (type == 1) {
      tmp = new WithLimit(fields[1], Integer.parseInt(fields[2]));
    } else if (type == 2) {
      tmp = new LimitAndAssignee(fields[1], Integer.parseInt(fields[2]), fields[3]);
    } else {
      throw new WrongTaskTypeException(type); 
    }
    this.tasks.set(index, tmp);
  }

  public void printTaskDescriptions() {
    for (int i = 0; i < this.loaded; i++) {
      System.out.println(i + " " + this.getTask(i).getDesciption());
    }
  }

  private String details(int i) {
    Task task_ = this.getTask(i);
    if (task_ == null) {
      return "";
    }

    String text = i + " " + (task_.isDone() ? "[X] " : "[ ] ") +
      task_.toString(); 
    return text;
  }

  public void printTaskDetails() {
    for (int i = 0; i < this.loaded; i++) {
      System.out.println(this.getTask(i).getDesciption());
    }
  }

  public void completeTask(int index) {
    Task task_ = this.getTask(index);
    if (!task_.isDone()) {
      task_.completeTask();
      this.rewardPoints += task_.getPoints();
    }
  }

  public void printDueToday() {
    for (int i = 0; i < this.loaded; i++) {
      Task task_ = this.getTask(i);
      if (task_.dueToday()) {
        System.out.println(task_.getDesciption());
      }
    }
  }

  public void remindAll() {
    for (int i = 0; i < this.loaded; i++) {
      Task task_ = this.getTask(i);
      if (task_.sendReminder() != null) {
        System.out.println(task_.sendReminder());
      }
    }
  }

  public int getRewardPoints() {
    return this.rewardPoints;
  }
}
