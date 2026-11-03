import cs2030s.fp.InfiniteList;
import cs2030s.fp.Maybe;

public class PortfolioManager {
  public static double calculateValue(Portfolio<Stock> portfolio) {
    return portfolio.getStocks().reduce(0.0, (x, y) -> x + y.getPrice());
  }

  public static Stock highestValueStock(Portfolio<Stock> portfolio) {
    return portfolio
      .getStocks()
      .reduce(Maybe.some(new Stock("", 0.0, () -> 0.0)),
          (x, y) -> Maybe.some(x
          .filter(z -> z.getPrice() > y.getPrice())
          .orElse(y)))
      .orElse(null);
  }

  public static Portfolio<Stock> predict(Portfolio<Stock> portfolio,
      InfiniteList<PriceChange> change) {
    return new Portfolio<>(portfolio
      .getStocks()
      .flatMap(x -> change.map(y -> x.changePrice(y))));
  }
}
