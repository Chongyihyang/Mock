import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/** Reconstructed procedural starting code; not original exam source or an OO solution. */
public class DayCalendar {






  private int count;
  private Array<Event> events;

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
    this.events = new Array<>(this.count);
    for (int i = 0; i < this.count; i++) {
      String[] fields = input.nextLine().split(",", -1);
      Event e = null;
      Integer type_ = Integer.parseInt(fields[0]);
      int type = type_;
      if (type == 0) {
        e = new Birthday(fields[1]);
      } else if (type == 1) {
        e = new Lesson(fields[1], Integer.parseInt(fields[2]), Integer.parseInt(fields[3]));
      } else if (type == 2) {
        e = new Meeting(fields[1], Integer.parseInt(fields[2]), Integer.parseInt(fields[3]), fields[4]);
      }
      this.events.set(i, e);
    }
  }

  private String description(int index) {
    Event event_ = this.events.get(index);
    return event_.printDescription();
  }

  private String details(int index) {
    Event event_ = this.events.get(index);
    return event_.toString();
  }

  public void printEventDescriptions() {
    for (int i = 0; i < this.count; i++) {
      Event event_ = this.events.get(i);
      if (event_ != null && !event_.isCancelled()) {
        System.out.println(i + " " + this.description(i));
      }
    }
  }

  public void printEventDetails() {
    for (int i = 0; i < this.count; i++) {
      Event event_ = this.events.get(i);
      if (event_ != null && !event_.isCancelled()) {
        System.out.println(i + " " + this.details(i));
      }
    }
  }

  public void cancelEvent(int index) {
    try {
      Event event_ = this.events.get(index);
      event_.cancel();
    } catch (IllegalCancellationException e) {
      System.out.println(e.getMessage());
    }
  }

  public void remind(int time) {
    for (int i = 0; i < this.count; i++) {
      Event event_ = this.events.get(i);
      if (event_.needsReminder(time)) {
        System.out.println(i + " " + this.details(i));
      }
    }
  }

  public int getBusyPeriod() {
    int hours = 0;
    for (int i = 0; i < this.count; i++) {
      Event event_ = this.events.get(i);
      hours += event_.getBusy();
    }
    return hours;
  }
}
