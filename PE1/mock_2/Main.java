public class Main {

  public static void main(String[] args) {

    Printer printer = new Printer(5);

    Printable[] firstBatch = {
      new SingleSidedDocument("Notes", 3),
      new DuplexDocument("Slides", 5),
      new DuplexDocument("Checklist", 4)
    };

    PrintRoom.process(printer, firstBatch);
    System.out.println(printer.paperRemaining());

    printer.setOnline(false);

    Printable[] secondBatch = {
      new SingleSidedDocument("Letter", 1),
      new SingleSidedDocument("Form", 1)
    };

    PrintRoom.process(printer, secondBatch);

    printer.setOnline(true);
    printer.addPaper(3);

    PrintRoom.process(printer, new Printable[] {
        new DuplexDocument("Slides", 5)
    });

    System.out.println(printer.paperRemaining());

  }
}
