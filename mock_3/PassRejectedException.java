public class PassRejectedException extends AdmissionException {

  public PassRejectedException(String id) {
    super(String.format("Pass %s cannot be used", id));
  }

}
