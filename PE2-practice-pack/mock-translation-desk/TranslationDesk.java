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
    return Maybe
      .some(key)
      .flatMap(x -> first.lookup(x))
      .flatMap(x -> second.lookup(x));
  }

  public static Maybe<String> translateAll(Dictionary dictionary, InfiniteList<String> words) {
    return Maybe
      .some("")
      .filter(x -> words.count() == 
          words
          .filter(word -> !dictionary.lookup(word).equals(Maybe.none()))
          .map(word -> dictionary.lookup(word).orElse(""))
          .count())
      .map(x -> words
          .map(word -> dictionary.lookup(word).toString())
          .reduce("", (z, y) -> z + y));
  }

  public static Lazy<String> deferredTranslation(Dictionary dictionary,
      InfiniteList<String> words, Producer<? extends String> fallback) {
    return Lazy
      .of(() -> translateAll(dictionary, words))
      .flatMap(x -> x
          .map(z -> Lazy.of(z)) 
          .orElse(Lazy.of(fallback)));
  }
}
