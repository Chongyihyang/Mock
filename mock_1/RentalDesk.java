public class RentalDesk {

  public static void rentAll(Rentable[] items, int hours) {
    for (Rentable item : items) {
      try {
        item.rent(hours);
        System.out.println(item);
      } catch (Exception e) {
        System.out.println("ERROR: " + e.getMessage());
      }
    }
  }

}
