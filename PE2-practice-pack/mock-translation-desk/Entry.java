import cs2030s.fp.Transformer;

/**
 * A dictionary entry. Question 1.
 *
 * @author
 */
public final class Entry {
  private final String key;
  private final String value;

  public Entry(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public final String getKey() {
    return this.key;
  }

  public final String getValue() {
    return this.value;
  }

  public final boolean matches(String key) {
    return this.key.equals(key);
  }

  public final Entry withValue(Transformer<? super String, ? extends String> change) {
    return new Entry(this.key, change.transform(this.value));
  }

  public final Entry reverse() {
    return new Entry(this.value, this.key);
  }

  @Override
  public final String toString() {
    return this.key + " -> " + this.value;
  }
}
