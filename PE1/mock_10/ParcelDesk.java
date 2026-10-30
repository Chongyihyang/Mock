/** Task 1 public API only. Choose your own fields and supporting classes. */
public class ParcelDesk {
  private Parcel[] parcels;
  private final int size;
  private int current = 0;
  private int fees = 0;

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
      Parcel item = this.parcels[i];
      if (!item.hasBeenCollected()) {
        res += String.format("%d: %s\n", i, this.parcels[i].toString());
      }
    }

    return res;
  }

  public String overdueParcels() {
    String res = "";
    for (int i = 0; i < this.current; i++) {
      Parcel item = this.parcels[i];
      if (item.isOverdue() && !item.hasBeenCollected()) {
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


  public String addChilled(String name, int days) {
    Parcel tmp = new Chilled(name, days);
    this.parcels[current] = tmp;
    current += 1;
    return "Registered: " + tmp.toString();
  }


  public String addLocker(String name, int days, String code) {
    Parcel tmp = new Locked(name, days, code);
    this.parcels[current] = tmp;
    current += 1;
    return "Registered: " + tmp.toString();
  }


  public String collect(int index) {
    Parcel parcel = this.parcels[index];
    boolean res = parcel.collectItem();
    if (res) {
      this.fees += parcel.overdueFees();
      return "Collected: " + parcel.toString() + "; paid $" + parcel.overdueFees();
    } 

    return "Already collected: " + parcel.toString();

  }

  public int feesCollected() {
    
    return this.fees;
  }

}
