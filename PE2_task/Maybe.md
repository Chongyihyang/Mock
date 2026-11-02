# Maybe API (Abridged)
This abridged Maybe API contains documentation for the following methods:

- some
- none
- of
- map
- filter
- flatMap
- orElse
- orElseGet
- ifPresent


## some

```java
public static <T> Maybe<T> some(T val)
```

-  Create an instance of Maybe with a given value `val`.


## none

```java
public static <T> Maybe<T> none()
```

- Create an instance of Maybe with a missing value.  The same instance is 
  returned everytime.
    

of

```java    
public static <T> Maybe<T> of(T val)
```

- Create an instance of Maybe containing the value `val` if it is is not null; 
  an instance of Maybe with missing value otherwise.

    
## map

```java
public <U> Maybe<U> map(Transformer<? super T, ? extends U> transformer)
```

- If the value within this Maybe is missing, just return the current
  instance; otherwise, apply the given transformation on the value 
  and return a Maybe with the new value intact.  

    
## filter

```java
public Maybe<T> filter(BooleanCondition<? super T> cond)
```

- If the value within this Maybe does not exist, just return the current
  instance; otherwise, check the value against the given condition and 
  return a Maybe with missing value (if cond test is false), or return
  a Maybe with the value intact.  Note if the value contains is null, 
  the condition is not tested.
    
## flatMap

```java
public <U> Maybe<U> flatMap(Transformer<? super T, ? extends Maybe<? extends U>> transformer)
```

- If the value within this Maybe is missing, just return the current
  instance; otherwise, apply the given transformation on the value and 
  return a Maybe with the new value intact.   This is similar to map
  except that the given transformation returns a Maybe and flatMap does
  not wrap the new value with an additional layer of Maybe.

    
## orElse

```java
public <U extends T> T orElse(U u)
```

- If the value within this Maybe is missing, return the given u;
  otherwise, return the value in this Maybe.


## orElseGet

```java
public T orElseGet(Producer<? extends T> producer)
```

- If the value within this Maybe is missing, return the value
  produced by the given producer.  Otherwise, return the value 
  in this Maybe.


## ifPresent

```java
public void ifPresent(Consumer<? super T> consumer)
```

- If the value within this Maybe is missing, does nothing;
  Otherwise, consume the value with the given consumer.
