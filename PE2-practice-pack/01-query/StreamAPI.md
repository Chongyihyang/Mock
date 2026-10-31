# Stream reference (local condensed aid)
`Stream.of(...)`, `Stream.empty()`, `Stream.iterate(seed, unaryOperator)`, `Stream.generate(supplier)`, `Stream.concat(a,b)`.
Intermediate operations: `filter(Predicate)`, `map(Function)`, `flatMap(Function returning Stream)`, `distinct()`, `sorted()`, `sorted(Comparator)`, `limit(long)`, `skip(long)`.
Terminal operations: `count()`, `reduce(identity, BinaryOperator)`, `min(Comparator)`, `max(Comparator)`, `anyMatch(Predicate)`, `allMatch(Predicate)`, `forEach(Consumer)`, `collect(Collectors.toList())`.
A stream is single-use. Encounter order matters unless the question says otherwise. `min`/`max` return an Optional; `orElse(defaultValue)` supplies a fallback.
`mapToDouble(ToDoubleFunction).sum()` and `mapToInt(ToIntFunction).sum()` produce numeric totals.
This aid is reconstructed, not the official supplied reference.
