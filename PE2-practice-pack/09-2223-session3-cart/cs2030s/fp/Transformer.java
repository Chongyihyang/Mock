package cs2030s.fp;
@FunctionalInterface
public interface Transformer<T, R> {
 public R transform(T value);
}
