# Mock Practice PE 2 — Translation Desk
CS2030S Programming Methodology II

Time Allowed: **90 minutes**  
Total: **15 marks**

---

## INSTRUCTIONS TO CANDIDATES

1. Marks for each question are indicated below. Style and design form part of the assessment.
2. This is a closed-book assessment. You may refer to one double-sided A4-size paper and the supplied API references.
3. Edit `Entry.java`, `Dictionary.java`, and `TranslationDesk.java`. Keep your answers directly in the home directory.
4. Write your student number in the `@author` tag of each file you edit.
5. Do not add imports. Remove the `java.util.ArrayList` and `java.util.List` imports from `Dictionary.java` when completing Question 2.
6. Use JDK 17 or later. Compile with:

   ```shell
   javac -Xlint:unchecked -Xlint:rawtypes *.java
   ```

7. Run the tests with:

   ```shell
   java -cp test.jar:. Test1
   java -cp test.jar:. Test2
   java -cp test.jar:. Test3
   ```

   Recompile after changing your Java files.

---

## Preliminary

Your home directory contains:

- `Entry.java`
- `Dictionary.java`
- `TranslationDesk.java`
- `cs2030s/fp/` — the supplied package source files
- `pristine/` — clean copies of the three question files
- `task.md` — this paper
- `test.jar` — compiled tests for Questions 1–3
- `Maybe.md`, `InfiniteList.md`, and `Lazy.md` — abridged API references

## Provided Classes

The `cs2030s.fp` package contains:

- `BooleanCondition<T>`
- `Combiner<S, T, R>`
- `Consumer<T>`
- `InfiniteList<T>`
- `Lazy<T>`
- `Maybe<T>`
- `Producer<T>`
- `Transformer<T, R>`

Keep these source files unchanged. Use the supplied `InfiniteList` for sequence processing, `Maybe` for optional values, `Lazy` for deferred computation, and the package's functional interfaces.

## Constraints

Your completed answers must satisfy all of the following:

- **Every method body must contain exactly one `return` statement and nothing else.**
- Do not declare local variables. Lambda parameters are allowed; declarations inside lambda bodies are not.
- Do not use loops, `if`, `switch`, conditional expressions (`?:`), or recursion.
- Do not use block-bodied lambdas such as `x -> { ... }`.
- Do not use arrays, Java collection types such as `List`, or conversions through `toList()` or `fromList()`.
- Do not use `Stream`, `IntStream`, `Optional`, or types from `java.util.function`.
- Do not assign to fields, mutate objects or collections, or use assignment, compound assignment, increment, or decrement expressions inside a return expression or lambda.
- Do not use casts, raw types, `@SuppressWarnings`, explicit null values, or exception-based control flow.
- Do not change any given method's name, parameters, or return type.
- Do not add fields, constructors, methods, classes, anonymous classes, or initialiser blocks. This also prohibits private helper methods.
- Do not add imports or use fully qualified names to access prohibited APIs.

The supplied constructors are not methods; leave their bodies unchanged. Make only the class and field modifier changes requested in Question 1. The supplied `cs2030s.fp` implementations are not subject to the answer restrictions.

The imperative starter methods are code to rewrite. The restrictions apply to your completed versions. All methods you are asked to implement return a value.

---

## Question 1 [5 marks]

Rewrite `Entry` as an **immutable** class.

An entry maps a key to a value. Both are non-null strings, and either may be empty. The class has exactly two fields, `key` and `value`, both of type `String`. Its constructor is `Entry(String key, String value)`.

Make the class `final` and the fields `private final`. Re-implement the following methods, using one return statement in each:

| Method | Required behaviour |
| --- | --- |
| `Entry withValue(Transformer<? super String, ? extends String> change)` | Apply `change` exactly once to the current value. Return a new entry with the same key and the resulting value. |
| `Entry reverse()` | Return a new entry whose key is the original value and whose value is the original key. |

The transformer returns a non-null string. Neither method changes the original entry. Both return a new object even when the resulting strings equal the original strings.

Keep the constructor, `getKey`, `getValue`, `matches`, and `toString` unchanged. `matches` compares keys by string value and is case-sensitive. The string representation is exactly `key -> value`.

### Sample Run

```text
jshell> Entry e = new Entry("cat", "chat")
e ==> cat -> chat

jshell> Entry upper = e.withValue(x -> x.toUpperCase())
upper ==> cat -> CHAT

jshell> e
e ==> cat -> chat

jshell> e.reverse()
$.. ==> chat -> cat

jshell> e.getKey()
$.. ==> "cat"

jshell> e.getValue()
$.. ==> "chat"

jshell> e.matches(new String("cat"))
$.. ==> true

jshell> e.matches("Cat")
$.. ==> false

jshell> e.withValue(x -> x) == e
$.. ==> false

jshell> Entry same = new Entry("echo", "echo")
same ==> echo -> echo

jshell> same.reverse() == same
$.. ==> false
```

Run Question 1's tests with:

```shell
java -cp test.jar:. Test1
```

---

## Question 2 [5 marks]

Rewrite `lookup`, `overlay`, and `reverse` in `Dictionary.java` in functional style. Each method body must consist of a single return statement. Remove all use of `java.util.List` and `java.util.ArrayList` from the class.

The class has one field, `entries`, of type `InfiniteList<Entry>`. Keep its field, constructor, `getEntries`, and `toString` unchanged. The input sequence is finite and contains non-null entries. Duplicate keys and repeated entry objects are allowed.

### `lookup` [2 marks]

```java
public Maybe<String> lookup(String key)
```

Return the value of the **first matching entry**, wrapped in `Maybe`. Return `Maybe.none()` when there is no matching entry. Matching is case-sensitive and uses string values. An empty string stored as a value is a successful result, `Maybe.some("")`.

### `overlay` [2 marks]

```java
public Dictionary overlay(Dictionary newer)
```

Return a new dictionary with the following entries, in this order:

1. Every entry from `newer`, in its original order, including duplicate keys.
2. Every entry from this dictionary whose key does **not** occur in `newer`, in its original order.

Keep the original entry references. When a key occurs in `newer`, remove all occurrences of that key from the older dictionary's contribution, regardless of their values. Do not remove duplicates from `newer` or from the retained older entries.

Neither input dictionary is modified. Always return a new dictionary, including when one or both inputs are empty.

### `reverse` [1 mark]

```java
public Dictionary reverse()
```

Return a new dictionary obtained by reversing every entry using Question 1's operation. Preserve the original entry order and keep repeated results. Reversing may create duplicate keys; `lookup` still uses the first matching entry. Leave the original dictionary and its entries unchanged.

The supplied imperative methods already produce the required results once Question 1 is complete. This question assesses their functional rewrite. Results may be evaluated lazily by the supplied `InfiniteList` operations.

### Sample Run

```text
jshell> Entry cat = new Entry("cat", "chat")
cat ==> cat -> chat
jshell> Entry dog = new Entry("dog", "chien")
dog ==> dog -> chien

jshell> Dictionary base = new Dictionary(InfiniteList.of(cat, dog,
   ...>     new Entry("cat", "minou"), new Entry("bird", "oiseau")))
base ==> [cat -> chat][dog -> chien][cat -> minou][bird -> oiseau]

jshell> base.lookup("cat")
$.. ==> [chat]

jshell> base.lookup("Cat")
$.. ==> []

jshell> Dictionary newer = new Dictionary(InfiniteList.of(new Entry("dog", "toutou"),
   ...>     new Entry("fox", "renard"), new Entry("dog", "chien2")))
newer ==> [dog -> toutou][fox -> renard][dog -> chien2]

jshell> Dictionary combined = base.overlay(newer)
combined ==> [dog -> toutou][fox -> renard][dog -> chien2][cat -> chat][cat -> minou][bird -> oiseau]

jshell> combined.lookup("dog")
$.. ==> [toutou]

jshell> base
base ==> [cat -> chat][dog -> chien][cat -> minou][bird -> oiseau]

jshell> base.reverse()
$.. ==> [chat -> cat][chien -> dog][minou -> cat][oiseau -> bird]

jshell> Dictionary colours = new Dictionary(InfiniteList.of(new Entry("red", "rouge"),
   ...>     new Entry("scarlet", "rouge")))
colours ==> [red -> rouge][scarlet -> rouge]

jshell> colours.reverse().lookup("rouge")
$.. ==> [red]

jshell> Dictionary silent = new Dictionary(InfiniteList.of(new Entry("pause", "")))
silent ==> [pause -> ]

jshell> silent.lookup("pause").equals(Maybe.some(""))
$.. ==> true
```

Run Question 2's tests with:

```shell
java -cp test.jar:. Test2
```

---

## Question 3 [5 marks]

Implement the three static methods in `TranslationDesk.java`. Each body must consist of one return statement. All supplied sequences are finite; dictionaries, words, and callbacks are non-null.

### `relay` [1 mark]

| Item | Description |
| --- | --- |
| Parameters | `Dictionary first`, `Dictionary second`, `String key` |
| Return type | `Maybe<String>` |
| Result | Look up `key` in `first`, then use its translated value as the key to look up in `second`. Return the second translation. If either lookup fails, return `Maybe.none()`. |

The second lookup uses the first lookup's **value**, not the original key. An empty translated string is a valid key for the second lookup.

### `translateAll` [2 marks]

| Item | Description |
| --- | --- |
| Parameters | `Dictionary dictionary`, `InfiniteList<String> words` |
| Return type | `Maybe<String>` |
| Result | Translate every word and join the translations in input order, enclosing each translated value in square brackets. Return this entire string wrapped in `Maybe` only if every word has a translation. Otherwise return `Maybe.none()`. |

Use `lookup`, including its first-match rule. Preserve repeated words. Do not return a partial result when a word is missing, and do not omit failed words.

An empty word sequence succeeds with the empty string: `Maybe.some("")`. A successful translation whose value is empty contributes `"[]"` to the result. Do not insert spaces, separators, or a trailing newline.

### `deferredTranslation` [2 marks]

| Item | Description |
| --- | --- |
| Parameters | `Dictionary dictionary`, `InfiniteList<String> words`, `Producer<? extends String> fallback` |
| Return type | `Lazy<String>` |
| Result | Defer the operation performed by `translateAll`. On success, use its string. On failure, invoke `fallback` to obtain the string instead. |

Creating the returned `Lazy` must not traverse the word sequence, perform dictionary lookups, or invoke the fallback. Calling its `toString()` before evaluation must also leave the computation deferred.

The first `get()` performs the translation. Invoke the fallback exactly once if translation fails and never if it succeeds. The fallback returns a non-null string. Later `get()` calls return the cached result without performing the translation or invoking the fallback again. Use the supplied `Lazy` to provide this behaviour.

### Sample Run

```text
jshell> Dictionary french = new Dictionary(InfiniteList.of(new Entry("cat", "chat"),
   ...>     new Entry("dog", "chien"), new Entry("bird", "oiseau"), new Entry("pause", "")))
french ==> [cat -> chat][dog -> chien][bird -> oiseau][pause -> ]

jshell> Dictionary spanish = new Dictionary(InfiniteList.of(new Entry("chat", "gato"),
   ...>     new Entry("chien", "perro")))
spanish ==> [chat -> gato][chien -> perro]

jshell> TranslationDesk.relay(french, spanish, "cat")
$.. ==> [gato]

jshell> TranslationDesk.relay(french, spanish, "bird")
$.. ==> []

jshell> TranslationDesk.relay(french, spanish, "fox")
$.. ==> []

jshell> TranslationDesk.translateAll(french, InfiniteList.of("cat", "dog", "cat"))
$.. ==> [[chat][chien][chat]]

jshell> TranslationDesk.translateAll(french, InfiniteList.of("cat", "fox", "dog"))
$.. ==> []

jshell> TranslationDesk.translateAll(french, InfiniteList.sentinel()).orElse("missing")
$.. ==> ""

jshell> TranslationDesk.translateAll(french, InfiniteList.of("pause", "cat")).orElse("missing")
$.. ==> "[][chat]"

jshell> Lazy<String> good = TranslationDesk.deferredTranslation(french,
   ...>     InfiniteList.of("cat", "dog"), () -> "unavailable")
good ==> ?

jshell> good.get()
$.. ==> "[chat][chien]"

jshell> good.get()
$.. ==> "[chat][chien]"

jshell> Lazy<String> failed = TranslationDesk.deferredTranslation(french,
   ...>     InfiniteList.of("cat", "fox"), () -> "unavailable")
failed ==> ?

jshell> failed.get()
$.. ==> "unavailable"

jshell> failed.get()
$.. ==> "unavailable"
```

The doubled outer brackets in the `translateAll` sample come from printing a `Maybe` whose contained string already includes brackets.

Run Question 3's tests with:

```shell
java -cp test.jar:. Test3
```
