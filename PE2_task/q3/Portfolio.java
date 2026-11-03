// @author:

import cs2030s.fp.InfiniteList;
import java.util.List;

public class Portfolio<T extends Stock> {
  private InfiniteList<T> stocks;

  public Portfolio(InfiniteList<T> stocks) {
    this.stocks = stocks;
  }

  public void addStock(T stock) {
    List<T> tmp = this.stocks.toList();
    tmp.add(stock);
    this.stocks = InfiniteList.fromList(tmp);
    //this.stocks = InfiniteList.of();
  }

  public void removeStock(T stock) {
    List<T> tmp = this.stocks.toList();
    for (int i = tmp.size() - 1; i > 0; i -= 1) {
      Stock stonk = tmp.get(i);
      if (stonk.getSymbol().equals(stock.getSymbol())) {
        tmp.remove(i);
      }
    }
    this.stocks = InfiniteList.fromList(tmp);
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
