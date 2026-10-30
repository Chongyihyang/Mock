public class JobBundle extends WorkItem {

  private final WorkItem[] items;

  public JobBundle(String label, WorkItem[] items) {
    super(label, calcTotalSheets(items));
    this.items = newList(items);
  }

  private static WorkItem[] newList(WorkItem[] items) {
    WorkItem[] ret = new WorkItem[items.length];
    for (int i = 0; i < items.length; i++) {
      ret[i] = items[i];
    }

    return ret;
  }

  private static int calcTotalSheets(WorkItem[] items) {
    int total = 0;
    for (WorkItem item : items) {
      total += item.sheets();
    }
    return total;
  }

  @Override
  public int minutes() {
    int total = 0;
    for (WorkItem item : items) {
      total += item.minutes();
    }
    return total;
  }
  
  @Override
  public int price() {
    int total = 0;
    for (WorkItem item : items) {
      total += item.price();
    }
    total -= 2;
    if (total < 0) {
      return 0;
    }

    return total;
  }
}
