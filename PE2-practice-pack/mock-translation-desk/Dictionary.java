import cs2030s.fp.InfiniteList;
import cs2030s.fp.Maybe;
import java.util.ArrayList;
import java.util.List;

/**
 * An ordered dictionary. Question 2.
 *
 * @author
 */
public final class Dictionary {
  private final InfiniteList<Entry> entries;

  public Dictionary(InfiniteList<Entry> entries) {
    this.entries = entries;
  }

  public InfiniteList<Entry> getEntries() {
    return this.entries;
  }

  public Maybe<String> lookup(String key) {
    return Maybe
      .some(Maybe.some(""))
      .filter(y -> this.entries.filter(x -> x.matches(key)).count() > 0)
      .map(y -> Maybe.some(this.entries
            .filter(x -> x.matches(key))
            .head()
            .getValue()))
      .orElse(Maybe.none());
  }

  public Dictionary overlay(Dictionary newer) {
    return new Dictionary(InfiniteList
      .of(
        newer.getEntries(),
        this.entries.filter(entry -> 
          Maybe
          .of(newer.lookup(entry.getKey()))
          .filter(y -> y.equals(Maybe.none()))
          .map(y -> true)
          .orElse(false)))
      .flatMap(x -> x));
  }


  public Dictionary reverse() {
    return new Dictionary(this.entries.map(x -> x.reverse()));
  }

  @Override
  public String toString() {
    return this.entries.reduce("", (text, entry) -> text + "[" + entry + "]");
  }
}
