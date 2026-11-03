// @author:

import cs2030s.fp.InfiniteList;
import cs2030s.fp.Maybe;
import java.util.List;

public class Portfolio<T extends Stock> {
  private InfiniteList<T> stocks;

  public Portfolio(InfiniteList<T> stocks) {
    this.stocks = stocks;
  }

  public void addStock(T stock) {
    this.stocks = InfiniteList
      .of(
        this.stocks, 
        InfiniteList.of(stock))
      .flatMap(x -> x);
  }

  public void removeStock(T stock) {
    this.stocks = this
      .stocks
      .filter(x -> !x.getSymbol().equals(stock.getSymbol()));
  }

  public InfiniteList<T> getStocks() {
    return this.stocks;
  }

  public double performAllActions() {
    return this.stocks.reduce(0.0, (x, y) -> x + y.performStockAction());
  }

  @Override
  public String toString() {
    return this.stocks.reduce("", (acc, elem) ->
            acc + "[" + elem.toString() + "]");
  }
}
