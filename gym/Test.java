class Test {

  public static void main(String[] args) throws CannotTrainException {
    Treadmill treadmill1 = new Treadmill();
    Treadmill treadmill2 = new Treadmill();
    Customer c1 = new Customer("Bob");
    Customer c2 = new Customer("Sally");
    Trainer t1 = new Trainer("Frank");
    Trainer t2 = new Trainer("Sam");
    System.out.println(t1.getStatus());
    t1.startTraining(c1, treadmill1);
    System.out.println(t1.getStatus());
    t1.startTraining(c2, treadmill1);
  }
}
