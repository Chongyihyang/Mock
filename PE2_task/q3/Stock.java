// @author:

public class Stock {
  private final String symbol;
  private final double price;
  private final StockAction<Double> action;

  public Stock(String symbol, double price, StockAction<Double> action) {
    this.symbol = symbol;
    this.price = price;
    this.action = action;
  }

  public String getSymbol() {
    return this.symbol;
  }

  public double getPrice() {
    return this.price;
  }

  public StockAction<Double> getStockAction() {
    return this.action;
  }

  public double performStockAction() {
    return this.action.perform();
  }

  public Stock changePrice(PriceChange event) {
    return event.apply(new Stock(this.symbol, this.price, this.action));
    //Stock stonk = event.apply(this);
    //this.symbol = stonk.symbol;
    //this.price = stonk.price;
    //this.action = stonk.action;
    //return this;
  }

  public Stock changeStockAction(StockAction<Double> action) {
    //this.action = action;
    //return this;
    return new Stock(this.symbol, this.price, action);
  }

  @Override
  public String toString() {
    return this.symbol + ": " + String.format("%.1f", this.price);
  }
}
