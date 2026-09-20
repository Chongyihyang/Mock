public class Main {

  public static void main(String[] args) {
    Equipment camera = new Camera("C1");
    Equipment laptop = new Laptop("L1");
    
    Rentable[] firstBatch = {camera, laptop, camera};
    RentalDesk.rentAll(firstBatch, 3);
    
    camera.returnItem();
    
    Rentable[] secondBatch = {camera, laptop};
    RentalDesk.rentAll(secondBatch, 0);
    
    RentalDesk.rentAll(secondBatch, 1); 

  }

}
