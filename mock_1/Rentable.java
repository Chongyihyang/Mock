public interface Rentable {

  String id();

  int rent(int hours) throws Exception;

  void returnItem();

}
