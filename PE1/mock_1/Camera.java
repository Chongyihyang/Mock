public class Camera extends Equipment {

  public Camera(String id) {
    super(id);
  }

  @Override
  public int rentalFee(int hours) {
    return hours * 40;
  }

}
