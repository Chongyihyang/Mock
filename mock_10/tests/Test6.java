public final class Test6 {
  private Test6() { }

  public static void main(String[] args) throws Exception {
    TestSupport t = new TestSupport();
    ParcelDesk desk = new ParcelDesk(4);
    desk.addParcel("Twin", 0); desk.addParcel("Twin", -2);
    desk.addChilled("Herbs", 0); desk.addLocker("Wire", 1, "D4");
    t.check("first twin extension", "Extended: Twin (3 days) {1 extensions}", desk.extend(0));
    t.check("collect after extension", "Collected: Twin (3 days) {1 extensions}; paid $0", desk.collect(0));
    t.check("collected positive-day parcel cannot extend", "Cannot extend: Twin (3 days) {1 extensions}", desk.extend(0));
    t.check("repeat retains extended status", "Already collected: Twin (3 days) {1 extensions}", desk.collect(0));
    t.check("duplicate name is a different parcel", "Collected: Twin (-2 days); paid $4", desk.collect(1));
    t.check("extend chilled then collect", "Extended: Herbs (1 days) {1 extensions} [chilled]", desk.extend(2));
    t.check("collect chilled with extension suffix", "Collected: Herbs (1 days) {1 extensions} [chilled]; paid $0", desk.collect(2));
    t.check("collected chilled cannot extend", "Cannot extend: Herbs (1 days) {1 extensions} [chilled]", desk.extend(2));
    t.check("locker on time collected free", "Collected: Wire (1 days) [locker D4]; paid $0", desk.collect(3));
    t.check("fees collected across free and paid pickups", 4, desk.feesCollected());
    t.check("no active parcels", "", desk.allParcels());
    ParcelDesk other = new ParcelDesk(1); other.addParcel("Twin", -1);
    t.check("desk collected totals independent", 0, other.feesCollected());
    t.check("desk outstanding totals independent", 2, other.totalFees());
    t.check("other desk collection", "Collected: Twin (-1 days); paid $2", other.collect(0));
    t.check("first desk paid total unchanged", 4, desk.feesCollected());
    t.check("second desk paid total", 2, other.feesCollected());
    t.check("all collection paths silent", "", t.capture(() -> {
      ParcelDesk quiet = new ParcelDesk(1); quiet.addParcel("Q", 1); quiet.collect(0);
      quiet.collect(0); quiet.extend(0); quiet.feesCollected(); quiet.totalFees();
    }));
    t.finish("Test6");
  }
}
