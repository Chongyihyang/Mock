// @author:

public class Stock {
  private String symbol;
  private double price;
  private StockAction<Double> action;

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
    Stock stonk = event.apply(this);
    this.symbol = stonk.symbol;
    this.price = stonk.price;
    this.action = stonk.action;
    return this;
  }

  public Stock changeStockAction(StockAction<Double> action) {
    this.action = action;
    return this;
  }

  @Override
  public String toString() {
    return this.symbol + ": " + String.format("%.1f", this.price);
  }
}