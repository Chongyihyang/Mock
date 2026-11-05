# Lazy API (Abridged)

`cs2030s.fp.Lazy<T>` stores a value whose computation may be deferred.

| Method | Behaviour |
| --- | --- |
| `static <T> Lazy<T> of(T value)` | Store an already computed value. |
| `static <T> Lazy<T> of(Producer<? extends T> producer)` | Store a computation without invoking it. |
| `T get()` | Compute once if necessary, cache the value, and return it. |
| `<R> Lazy<R> map(Transformer<? super T, ? extends R> f)` | Return a deferred transformation of the value. |
| `<R> Lazy<R> flatMap(Transformer<? super T, ? extends Lazy<? extends R>> f)` | Return a deferred transformation that flattens a nested Lazy. |
| `Lazy<Boolean> filter(BooleanCondition<? super T> condition)` | Return a deferred test of the value. |
| `<R, S> Lazy<S> combine(Lazy<? extends R> other, Combiner<? super T, ? super R, ? extends S> f)` | Defer combining two lazy values. |

`toString()` returns `"?"` before evaluation and the cached value's string afterwards. Calling `toString()` does not force evaluation.
