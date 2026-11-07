# Supplied API reference

This is a reference, not an implementation guide. The Java streams used here are sequential and single-use. Obtain a fresh stream for each separate traversal.

| API | Meaning |
|---|---|
| `List.copyOf(list)` | Unmodifiable snapshot of non-null elements; shallow copy |
| `list.stream()` | Ordered stream of list elements |
| `list.get(i)`, `list.size()` | Indexed element and number of elements |
| `Stream.of(values...)` | Stream containing the supplied separate arguments |
| `Stream.concat(a, b)` | Elements of `a`, then elements of `b` |
| `IntStream.range(start, end)` | Integers from start inclusive to end exclusive; empty when start >= end |
| `stream.filter(predicate)` | Retain matching elements |
| `stream.map(function)` | Transform each element |
| `stream.flatMap(function)` | Transform to streams and concatenate their elements |
| `stream.limit(n)`, `stream.skip(n)` | Keep at most n elements; discard first n elements; n must be non-negative |
| `stream.anyMatch(predicate)` | Whether at least one element matches; false for empty input |
| `stream.toList()` | Unmodifiable list in encounter order |
| `intStream.boxed()` | Convert `IntStream` to `Stream<Integer>` |
| `intStream.sum()` | Sum, with zero for empty input |
| `stream.reduce(identity, accumulator)` | Fold using an identity of the stream element type |
| `stream.reduce(identity, accumulator, combiner)` | Fold into another type; accumulator takes current result and next element; combiner combines partial results |

For the three-argument Java reduction, choose a genuine identity and a compatible associative combiner even though this paper uses sequential streams. `mapToObj` is also available on `IntStream`. APIs that return `Optional` are not needed or permitted in this paper.

## Course interfaces

- `Transformer<T,R>`: `R transform(T value)`
- `Producer<T>`: `T produce()`
- `BooleanCondition<T>`: `boolean test(T value)`

## Maybe<T>

- `Maybe.<T>some(value)` creates a present value; `Maybe.<T>none()` creates absence.
- `map(Transformer<? super T, ? extends R>)` returns `Maybe<R>`.
- `flatMap(Transformer<? super T, ? extends Maybe<? extends R>>)` returns `Maybe<R>`.
- `filter(BooleanCondition<? super T>)` retains a present value only if it passes.
- `orElse(T value)` returns the contained value or the supplied alternative.
- `orElseGet(Producer<? extends T>)` calls the producer only on absence.
- Mapping or filtering absence does not call the callback.
- `some(0)` and `some("")` are present. `some(7).toString()` is `[7]`; absence prints `[]`.
- `get()` is not public; do not call it.

## Lazy<T>

- `Lazy.of(value)` creates an already evaluated lazy value.
- `Lazy.of(Producer<? extends T>)` defers the producer until first `get()` and caches the result.
- `get()` returns the value, computing it if needed.
- `map(Transformer<? super T, ? extends R>)` returns `Lazy<R>` without evaluating immediately.
- `flatMap(Transformer<? super T, ? extends Lazy<? extends R>>)` returns `Lazy<R>` without evaluating immediately.
- `toString()` is `?` before evaluation and does not force evaluation.
