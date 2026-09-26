public class LibraryLoan {

  private Seq<LoanItem> loanItems;
  private final int size;
  private int current = 0;

  public LibraryLoan(int size) {
    this.loanItems = new Seq<>(size);
    this.size = size;
  }

  public String addBook(String bookname, int days) {
    Book newBook = new Book(bookname, days);
    this.loanItems.set(current, newBook);
    this.current += 1;
    return "Loaned: " + newBook.toString();
  }

  public String allLoan() {
    String res = "";
    for (int i = 0; i < this.current; i += 1) {
      res += i + ": " + this.loanItems.get(i) + "\n";  // \n is newline
    } 
    return res;
  }

  public String overdueLoan() {
    String res = "";
    for (int i = 0; i < this.current; i += 1) {
      LoanItem tmp = this.loanItems.get(i);
      if (tmp.isOverDue()) {
        res += i + ": " + this.loanItems.get(i) + "\n";
      }
    } 
    return res;
  }

  public String renew(int index) {
    LoanItem tmp = this.loanItems.get(index);
    return tmp.renew();
  }

  public int totalFines() {
    int res = 0;
    for (int i = 0; i < this.current; i += 1) {
      LoanItem tmp = this.loanItems.get(i);
      if (tmp.isOverDue()) {
        res += tmp.calcFines(); 
      }
    }

    return res;
  }
  
}
