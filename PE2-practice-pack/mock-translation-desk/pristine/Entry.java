import cs2030s.fp.Transformer;

/**
 * A dictionary entry. Question 1.
 *
 * @author
 */
public class Entry {
  private String key;
  private String value;

  public Entry(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return this.key;
  }

  public String getValue() {
    return this.value;
  }

  public boolean matches(String key) {
    return this.key.equals(key);
  }

  public Entry withValue(Transformer<? super String, ? extends String> change) {
    this.value = change.transform(this.value);
    return this;
  }

  public Entry reverse() {
    String oldKey = this.key;
    this.key = this.value;
    this.value = oldKey;
    return this;
  }

  @Override
  public String toString() {
    return this.key + " -> " + this.value;
  }
}
