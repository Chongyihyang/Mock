import cs2030s.fp.InfiniteList;
import cs2030s.fp.Lazy;
import cs2030s.fp.Maybe;
import cs2030s.fp.Producer;

/**
 * Translation operations. Question 3.
 *
 * @author
 */
public class TranslationDesk {
  public static Maybe<String> relay(Dictionary first, Dictionary second, String key) {
    return Maybe.none();
  }

  public static Maybe<String> translateAll(Dictionary dictionary, InfiniteList<String> words) {
    return Maybe.none();
  }

  public static Lazy<String> deferredTranslation(Dictionary dictionary,
      InfiniteList<String> words, Producer<? extends String> fallback) {
    return Lazy.of("");
  }
}
