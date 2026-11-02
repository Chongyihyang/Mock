# InfiniteList API (Abridged)
This abridged InfiniteList API contains documentation for the following methods:

- fromList
- of
- generate
- iterate
- head
- tail
- map
- filter
- sentinel
- limit
- toList
- takeWhile
- isSentinel
- reduce
- count
- flatMap


## fromList

```java
public static <T> InfiniteList<T> fromList(List<T> list)
```

- returns an infinite list from the given list.


## of

```java
public static <T> InfiniteList<T> of(T... elems)
```

- returns an infinite list containing the given elements elems.


## generate

```java
public static <T> InfiniteList<T> generate(Producer<T> prod)
```

- returns [prod(), prod(), prod(), ...]


## iterate

```java
public static <T> InfiniteList<T> iterate(T seed, Transformer<? super T, ? extends T> next)
```

- returns [seed, next(seed), next(next(seed)), ...]


## head

```java
public T head()
```

- returns the first element in the `InfiniteList<T>`.


## tail

```java
public InfiniteList<T> tail()
```

- returns the rest of the `InfiniteList<T>` except the first element.
    

## map

```java    
public <R> InfiniteList<R> map(Transformer<? super T, ? extends R> func)
```

- given   [     v1 ,      v2 ,      v3 , ...]
- returns [func(v1), func(v2), func(v3), ...]

In other words, apply `func` to each elements `vi`.

    
## filter

```java
public InfiniteList<T> filter(BooleanCondition<? super T> pred)
```

- given     [     v1        ,      v2         , ...]
- such that [pred(v1) = true, pred(v2) = false, ...]
- returns   [     v1                          , ...]

In other words, keep only elements `vi` where `pred(vi)` evaluates to true.


## sentinel

```java
public static <T> InfiniteList<T> sentinel()
```

- returns a new `Sentinel` (i.e., the end of infinite list).


## limit

```java
public InfiniteList<T> limit(int n)
```

- given   [v1, v2, v3, v4, v5, ...]
- limit(0) returns []
- limit(1) returns [v1]
- limit(2) returns [v1, v2]
- ...


## toList

```java
public List<T> toList()
```

- returns a `List<T>` version of `InfiniteList<T>`.


## takeWhile

```java
public InfiniteList<T> takeWhile(BooleanCondition<? super T> cond)
```

- given     [v1, v2, v3, v4, v5, ...]
- such that `v1`, `v2`, and `v5` satisfies `cond(vi)`
- returns   [v1, v2]


## isSentinel

```java
public boolean isSentinel()
```

- returns true if the infinite list is a sentinel and false otherwise.


## reduce

```java
public <S> T reduce(S id, Combiner<? super S, ? super T, ? extends S> comb)
```

- given   [v1, v2, v3]
- returns (((e (+) v1) (+) v2) (+) v3)

**Note:** (+) is an "abstract" binary operation


## count

```java
public long count()
```

- returns the number of element in `InfiniteList<T>`.

    
## flatMap

```java
public <R> InfiniteList<R> flatMap(Transformer<? super T, ? extends InfiniteList<? extends R>> fn)
```

- given     [   v1             ,    v2                 ,            , ...]
- such that [fn(v1) = [v11,v12], fn(v2) = [v21,v22,v23], fn(v3) = [], ...]
- returns   [   v11 , v12      ,    v21 , v22 , v23    ,              ...]

In other words, map + flatten.