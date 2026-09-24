import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/** Reconstructed procedural starting code; not original exam source or an OO solution. */
public class DayCalendar {
  private Array<Integer> types;
  private Array<String> descriptions;
  private Array<Integer> starts;
  private Array<Integer> ends;
  private Array<String> people;
  private Array<Boolean> cancelled;
  private int count;

  public DayCalendar() {
    this.load(new Scanner(System.in));
  }

  public DayCalendar(String filename) {
    try (Scanner input = new Scanner(new File(filename), "UTF-8")) {
      this.load(input);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Input file unavailable: " + filename, e);
    }
  }

  private void load(Scanner input) {
    this.count = Integer.parseInt(input.nextLine());
    this.types = new Array<>(this.count);
    this.descriptions = new Array<>(this.count);
    this.starts = new Array<>(this.count);
    this.ends = new Array<>(this.count);
    this.people = new Array<>(this.count);
    this.cancelled = new Array<>(this.count);
    for (int i = 0; i < this.count; i++) {
      String[] fields = input.nextLine().split(",", -1);
      int type = Integer.parseInt(fields[0]);
      this.types.set(i, type);
      this.descriptions.set(i, fields[1]);
      this.cancelled.set(i, false);
      if (type == 0) {
        this.starts.set(i, null);
        this.ends.set(i, null);
        this.people.set(i, null);
      } else {
        this.starts.set(i, Integer.parseInt(fields[2]));
        this.ends.set(i, Integer.parseInt(fields[3]));
        this.people.set(i, type == 2 ? fields[4] : null);
      }
    }
  }

  private String description(int index) {
    if (this.types.get(index) == 0) {
      return "Birthday (" + this.descriptions.get(index) + ")";
    }
    return this.descriptions.get(index);
  }

  private String details(int index) {
    String result = this.description(index);
    if (this.types.get(index) != 0) {
      result += " | " + this.starts.get(index) + " - " + this.ends.get(index);
    }
    if (this.types.get(index) == 2) {
      result += " | Meet with " + this.people.get(index);
    }
    return result;
  }

  public void printEventDescriptions() {
    for (int i = 0; i < this.count; i++) {
      if (!this.cancelled.get(i)) {
        System.out.println(i + " " + this.description(i));
      }
    }
  }

  public void printEventDetails() {
    for (int i = 0; i < this.count; i++) {
      if (!this.cancelled.get(i)) {
        System.out.println(i + " " + this.details(i));
      }
    }
  }

  public void cancelEvent(int index) {
    if (this.types.get(index) == 2) {
      this.cancelled.set(index, true);
    } else {
      System.out.println("Unable to cancel event: " + this.description(index));
    }
  }

  public void remind(int time) {
    for (int i = 0; i < this.count; i++) {
      if (!this.cancelled.get(i) && this.types.get(i) != 0
          && this.starts.get(i) >= time) {
        System.out.println(i + " " + this.details(i));
      }
    }
  }

  public int getBusyPeriod() {
    int hours = 0;
    for (int i = 0; i < this.count; i++) {
      if (!this.cancelled.get(i) && this.types.get(i) != 0) {
        hours += this.ends.get(i) - this.starts.get(i);
      }
    }
    return hours;
  }
}
