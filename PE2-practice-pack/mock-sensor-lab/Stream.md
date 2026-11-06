# Stream and List API (Abridged)

## Stream<T>

| Operation | Behaviour |
| --- | --- |
| `list.stream()` | Create a sequential stream in list order. |
| `Stream.of(values...)` | Stream the given arguments in order. |
| `Stream.empty()` | An empty stream. |
| `Stream.concat(a, b)` | Elements from `a`, followed by elements from `b`. |
| `filter(condition)` | Retain matching elements. |
| `map(function)` | Transform each element. |
| `flatMap(function)` | Flatten the streams produced for the elements, in order. |
| `limit(n)` | Keep at most the first `n` elements. |
| `skip(n)` | Discard the first `n` elements. |
| `anyMatch(condition)` | True if an element matches; false for an empty stream. |
| `allMatch(condition)` | True if every element matches; true for an empty stream. |
| `noneMatch(condition)` | True if no element matches; true for an empty stream. |
| `reduce(identity, accumulator)` | Combine using an associative operation and identity. |
| `toList()` | An unmodifiable list in encounter order. |

Streams can be consumed only once. Lists can supply a new stream for each traversal.

Stream operations accept Java's functional interfaces. Lambdas and method references can be passed directly. An existing `cs2030s.fp.Transformer` has a `transform` method, a `BooleanCondition` has a `test` method, and a `Combiner` has a `combine` method.

## IntStream

| Operation | Behaviour |
| --- | --- |
| `IntStream.range(start, end)` | Integers from `start` inclusive to `end` exclusive; empty when `start >= end`. |
| `filter(condition)` | Retain matching integers. |
| `map(function)` | Transform integers to integers. |
| `mapToObj(function)` | Transform integers to objects and return a `Stream`. |
| `boxed()` | Convert to `Stream<Integer>`. |
| `limit(n)` / `skip(n)` | Retain or discard an initial portion. |
| `sum()` | Sum the integers, or return zero when empty. |

## List<T>

| Operation | Behaviour |
| --- | --- |
| `List.of(values...)` | An unmodifiable list of the arguments. |
| `List.copyOf(collection)` | An unmodifiable snapshot of the elements. |
| `get(index)` | Element at a zero-based index. |
| `size()` | Number of elements. |
| `isEmpty()` | Whether the list is empty. |

`Integer.sum(a, b)` adds two integers. `Math.min(a, b)` and `Math.max(a, b)` select the smaller and larger values.
