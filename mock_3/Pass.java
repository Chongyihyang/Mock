public class Pass {

  private final String identity;
  private int availableAdmissionsLeft;

  public Pass(String identity, int availableAdmissionsLeft) {
    if (identity == null || identity.trim().equals("")) {
      throw new IllegalArgumentException();
    }

    if (availableAdmissionsLeft < 0) {
      throw new IllegalArgumentException();
    }

    this.identity = identity;
    this.availableAdmissionsLeft = availableAdmissionsLeft;

  }

  public void usePass() throws PassRejectedException {
    if (this.availableAdmissionsLeft == 0) {
      throw new PassRejectedException(this.identity);
    }

    this.availableAdmissionsLeft -= 1;
  }

}
