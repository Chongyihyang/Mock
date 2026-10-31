package cs2030s.fp;
@FunctionalInterface
public interface BooleanCondition<T> {
 public boolean test(T value);
}
