public class PrintRoom {

  public static void process(Printer printer, Printable[] items) {
    for (Printable item : items) {
      try {
        System.out.println(printer.print(item));
      } catch (InsufficientPaperException e) {
        System.out.println("SKIP: " + e.getMessage());
      } catch (PrintingException e) {
        System.out.println("STOP: " + e.getMessage());
        break;
      }
    }
  }
}




