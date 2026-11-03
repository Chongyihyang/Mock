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
    List<T> tmp = this.stocks.toList();
    double res = 0.0;
    for (int i = 0; i < tmp.size(); i += 1) {
      Stock stonk = tmp.get(i);
      res += stonk.performStockAction();
    }
    return res;
  }

  @Override
  public String toString() {
    return this.stocks.reduce("", (acc, elem) ->
            acc + "[" + elem.toString() + "]");
  }
}