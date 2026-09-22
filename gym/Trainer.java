public class Trainer extends GymStaff {

  private Customer customer = null;
  private Equipment equipment = null;

  public Trainer(String name) {
    super(name);
  }

  public void startTraining(Customer customer, Equipment equipment) throws CannotTrainException {
    if (this.customer != null || this.equipment != null) {
      throw new CannotTrainException();
    }
    this.customer = customer;
    this.equipment = equipment;
    equipment.setInUse(true);
  }

  public void stopTraining() {
    this.equipment.setInUse(false);
    this.customer = null;
    this.equipment = null;
  }

  public String getStatus() {
    String res =  this.toString();
    if (customer == null) {
      res += " not training";
    } else {
      res += " training " + customer;
    }
    return res;
  }

  @Override
  public String toString() {
    return "Trainer: " + super.toString();
  }
}
