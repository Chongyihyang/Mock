/** Task 1 public API only. Choose your own fields and supporting classes. */
public class ParcelDesk {
  private Parcel[] parcels;
  private final int size;
  private int current = 0;

  public ParcelDesk(int capacity) {
    this.parcels = new Parcel[capacity];
    this.size = capacity;
  }

  public String addParcel(String name, int days) {
    Parcel tmp = new Parcel(name, days);
    this.parcels[current] = tmp;
    current += 1;
    return "Registered: " + tmp.toString();
  }

  public String allParcels() {
    String res = "";
    for (int i = 0; i < this.current; i++) {
      res += String.format("%d: %s\n", i, this.parcels[i].toString());
    }

    return res;
  }

  public String overdueParcels() {
    String res = "";
    for (int i = 0; i < this.current; i++) {
      Parcel item = this.parcels[i];
      if (item.isOverdue()) {
        res += String.format("%d: %s\n", i, item.toString());
      }
    }

    return res;
  }

  public String extend(int index) {
    Parcel item = this.parcels[index];
    boolean tmp = item.extend();
    if (tmp) {
      return "Extended: " + item.toString();
    }

    return "Cannot extend: " + item.toString();
  }

  public int totalFees() {
    int res = 0;
    for (int i = 0; i < this.current; i++) {
      res += this.parcels[i].overdueFees();
    }

    return res;
  }
}
