public class FullWorkshopException extends AdmissionException {

  public FullWorkshopException(String code) {
    super(String.format("Workshop %s is full", code));
  }

}
