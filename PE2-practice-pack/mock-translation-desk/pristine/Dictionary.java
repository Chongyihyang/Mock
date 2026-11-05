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
    for (Entry entry : this.entries.toList()) {
      if (entry.matches(key)) {
        return Maybe.some(entry.getValue());
      }
    }
    return Maybe.none();
  }

  public Dictionary overlay(Dictionary newer) {
    List<Entry> combined = new ArrayList<>(newer.entries.toList());
    for (Entry entry : this.entries.toList()) {
      if (newer.lookup(entry.getKey()).equals(Maybe.none())) {
        combined.add(entry);
      }
    }
    return new Dictionary(InfiniteList.fromList(combined));
  }

  public Dictionary reverse() {
    List<Entry> reversed = new ArrayList<>();
    for (Entry entry : this.entries.toList()) {
      reversed.add(entry.reverse());
    }
    return new Dictionary(InfiniteList.fromList(reversed));
  }

  @Override
  public String toString() {
    return this.entries.reduce("", (text, entry) -> text + "[" + entry + "]");
  }
}
