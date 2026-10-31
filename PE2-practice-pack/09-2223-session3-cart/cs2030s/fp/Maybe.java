package cs2030s.fp;
/** Reconstructed PROVIDED support; not part of the exercise. */
public final class Maybe<T> {
 private final boolean present;
 private final T value;
 private Maybe(boolean present,T value) { this.present=present;this.value=value; }
 public static <T> Maybe<T> none() { return new Maybe<>(false,null); }
 public static <T> Maybe<T> some(T value) { return new Maybe<>(true,value); }
 public static <T> Maybe<T> of(T value) { return value==null ? none() : some(value); }
 public Maybe<T> filter(BooleanCondition<? super T> p) { return present && p.test(value) ? this : none(); }
 public <R> Maybe<R> map(Transformer<? super T,? extends R> f) { return present ? some(f.transform(value)) : none(); }
 public <R> Maybe<R> flatMap(Transformer<? super T,? extends Maybe<? extends R>> f) {
  if (!present) { return none(); }
  Maybe<? extends R> m=f.transform(value);
  return m.present ? some(m.value) : none();
 }
 public T orElse(T fallback) { return present ? value : fallback; }
 public T orElseGet(Producer<? extends T> p) { return present ? value : p.produce(); }
 public void ifPresent(Consumer<? super T> c) { if (present) { c.consume(value); } }
 public String toString() { return present ? "["+value+"]" : "[]"; }
 public boolean equals(Object o) { return o instanceof Maybe<?> && present==((Maybe<?>)o).present && java.util.Objects.equals(value,((Maybe<?>)o).value); }
 public int hashCode() { return java.util.Objects.hash(present,value); }
}
