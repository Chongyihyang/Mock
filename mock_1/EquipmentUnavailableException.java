public class EquipmentUnavailableException extends Exception {

  public EquipmentUnavailableException(String id) {
    super(String.format("Equipment %s is already rented", id));
  }

}
