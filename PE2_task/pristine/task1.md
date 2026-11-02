# Practice PE 2
CS2030S Programming Methodology II

NATIONAL UNIVERSITY OF SINGAPORE  
SCHOOL OF COMPUTING  

Nov 2026  
Time Allowed: 90 minutes  

---

## INSTRUCTIONS TO CANDIDATES

1. Marks for each question are indicated below.  We may deduct marks for
   style violations and design issues.

2. This is a CLOSED-BOOK assessment.  You are only allowed to refer to one (1) 
   double-sided A4-size paper.

3. Only the files directly under your the `task1`, `task2`, and `task3` 
   directories will be graded.

4. Some skeletons may be provided for you.  You may add new files if you
   need to.

5. Write your student number on top of EVERY FILE you created or edited as
   part of the `@author` tag.  Do not write your name.

6. You are not allowed to add any `import` statement unless otherwise 
   specified.  You are not allowed to use any `import` statement on files 
   you created.

7. To compile your code, run:
   ```shell
   javac -Xlint:unchecked -Xlint:rawtypes *.java
   ```

IMPORTANT: Any code you wrote that cannot be compiled may result in 0 marks
being given for the question(s) that depend on it.  Make sure your program
compiles before submission.

---

## Preliminary

Ensure that the following files are present in your home directory:

- `Portfolio.java`
- `PriceChange.java`
- `StockAction.java`
- `Stock.java`

Ensure that the following directories are also present:

- `pristine`

Additionally, make sure that the following files are in your home directory:

- `task1.md`     — unencrypted question for Task 1
- `submit.sh`    — submission script
- `test.jar`     — test jar file for Task 1 and Task 2

**You may add new files as needed**


---


## Provided Classes

`cs2030s.fp` package have been provided for you.  The package contains the 
following classes and interfaces.

- `BooleanCondition<T>`
- `Combiner<T, U, R>`
- `Consumer<T>`
- `InfiniteList<T>`
- `Lazy<T>`
- `Maybe<T>`
- `Producer<T>`
- `Transformer<T, R>`

The abridged API is available in `Maybe.md` and `InfiniteList.md`.

## Constraints

If **functional style** is needed, your code must conform to our functional 
style.  Any attempt at circumventing this restriction will invalidate the 
entire submission.

You are not allowed to do any of the following.  Note that the following is 
not meant to be a complete list of what functional style is.

- You are not allowed to modify the method descriptor of any given method.
- You are not allowed to use array.
- You are not allowed to use conditional expressions.
  This includes the ternary operator ?:.
- You are not allowed to add new methods except those stated in the question.
- You are not allowed to add new classes except those stated in the question.
- You are not allowed to use block-statement in lambda (e.g., `() -> { .. }`).
- You are not allowed to add import.
- You are not allowed to use `Optional`.
- You are not allowed to use `Stream`.
- You are not allowed to use array.


---


## Question 1 [5 marks]

You are given the following Java functional interfaces.
- `StockAction<T>` with `StockAction::perform` in `StockAction.java`.
- `PriceChange` with `PriceChange::apply` in `PriceChange.java`.

Rewrite the Java class named `Stock`.  Some code is already given, you may 
modify them but you are not allowed to add additional fields/methods not 
mentioned below.

- The class is an **immutable** class.

- The class has three fields.
  - `symbol` of type `String`.
  - `price` of type `double`.
  - `action` of type `StockAction<Double>`.

- The class has a constructor that takes in 3 parameters.
  - `symbol` of type `String`.
  - `price` of type `double`.
  - `action` of type `StockAction<Double>`.

- The class has 3 accessors.
  - `Stock::getSymbol` that returns `symbol`.
  - `Stock::getPrice` that returns `price`.
  - `Stock::getStockAction` that returns `action`.

- Re-implement the following methods to satisfy immutability.
  - `Stock::performStockAction`
  - `Stock::changePrice`
  - `Stock::changeStockAction`

- There should not be any other fields and/or methods except the above fields 
  and methods.  This includes another constructor.


### Sample Run

```text
jshell> StockAction<Double> buyStock = () -> { 
   ...>   System.out.println("Buy stock"); 
   ...>   return -100.0; 
   ...> };
jshell> StockAction<Double> payDividend = () -> { 
   ...>   System.out.println("Paying Dividend"); 
   ...>   return 100.0; 
   ...> };
jshell> StockAction<Double> sellStock = () -> { 
   ...>   System.out.println("Sell stock"); 
   ...>   return 200.0; 
   ...> };

jshell> Stock s1 = new Stock("AAPL", 165, buyStock)
s1 ==> AAPL: 165.0
jshell> Stock s2 = new Stock("JNJ", 147, payDividend)
s2 ==> JNJ: 147.0

jshell> s1.toString()
$.. ==> "AAPL: 165.0"
jshell> s2.toString()
$.. ==> "JNJ: 147.0"

jshell> s1.getSymbol()
$.. ==> "AAPL"
jshell> s2.getSymbol()
$.. ==> "JNJ"

jshell> s1.getPrice()
$.. ==> 165.0
jshell> s2.getPrice()
$.. ==> 147.0

jshell> s1.getStockAction()
jshell> s2.getStockAction()

jshell> s1.performStockAction()
Buy stock
$.. ==> -100.0
jshell> s2.performStockAction()
Paying Dividend
$.. ==> 100.0

jshell> Stock s3 = s1.changeStockAction(sellStock)
s3 ==> AAPL: 165.0

jshell> s3.performStockAction()
Sell stock
$.. ==> 200.0
jshell> s1.performStockAction()
Buy stock
$.. ==> -100.0

jshell> PriceChange discount =  s -> new Stock(s.getSymbol(), 
   ...>     0.9 * s.getPrice(), s.getStockAction())
jshell> Stock s4 = s1.changePrice(discount)
s4 ==> AAPL: 148.5

jshell> s4.getPrice()
$.. ==> 148.5
jshell> s1.getPrice()
$.. ==> 165.0
```

You are encouraged to test your code in jshell first.  You can run the test 
`Test1` as follows:

```shell
java -cp test.jar:. Test1
```


### Submit

Submit your question 1 by running the following command.

```shell
bash submit.sh q1
```


---


## Question 2 [5 marks]

Rewrite the Java class named `Portfolio` into functional style.  You are not 
allowed to use `java.util.List` after your modification.  There is no need to 
make the class immutable.  Some code is already given, you may modify them but 
you are not allowed to add additional fields/methods not mentioned below.

- The class has three fields.
  - `stocks` of type `InfiniteList<T>`.

- The class has a constructor that takes in 1 parameter.
  - `stocks` of type `InfiniteList<T>`.

- Re-implement the following methods to functional style.
  - `Portfolio::addStock`
  - `Portfolio::removeStock`
  - `Portfolio::performAllActions`

- There should not be any other fields and/or methods except the above fields 
  and methods.  This includes another constructor.

- The original code already pass all test cases.  You will not get any mark 
  unless you use functional style.


### Sample Run

```text
jshell> StockAction<Double> buyStock = () -> { 
   ...>   System.out.println("Buy stock"); 
   ...>   return -100.0; 
   ...> };
jshell> StockAction<Double> payDividend = () -> { 
   ...>   System.out.println("Paying Dividend"); 
   ...>   return 100.0; 
   ...> };
jshell> StockAction<Double> sellStock = () -> { 
   ...>   System.out.println("Sell stock"); 
   ...>   return 200.0; 
   ...> };

jshell> Stock s1 = new Stock("AAPL", 165, buyStock)
s1 ==> AAPL: 165.0
jshell> Stock s2 = new Stock("JNJ", 147, payDividend)
s2 ==> JNJ: 147.0
jshell> Stock s5 = new Stock("KO", 60, payDividend)
s5 ==> KO: 60.0

jshell> Portfolio<Stock> p1 = new Portfolio<>(InfiniteList.sentinel())
jshell> p1.addStock(s1)
jshell> p1.addStock(s2)
jshell> p1
p1 ==> [AAPL: 165.0][JNJ: 147.0]

jshell> Portfolio<Stock> p2 = new Portfolio<>(InfiniteList.sentinel())
jshell> p2.addStock(s1)
jshell> p2.addStock(s2)
jshell> p2.addStock(s5)
p2 ==> [AAPL: 165.0][JNJ: 147.0][KO: 60.0]

jshell> // our printing is different from list
jshell> p1.getStocks().toList().toString()
$.. ==> [AAPL: 165.0, JNJ: 147.0]

jshell> p1.performAllActions()
Buy stock
Paying Dividend
$.. ==> 0.0
jshell> p2.performAllActions()
Buy stock
Paying Dividend
Paying Dividend
$.. ==> 100.0

jshell> p1.toString()
$.. ==> "[AAPL: 165.0][JNJ: 147.0]"
jshell> p2.toString()
$.. ==> "[AAPL: 165.0][JNJ: 147.0][KO: 60.0]"

jshell> p2.addStock(new Stock("CS20", 30, payDividend))
jshell> p2.toString()
$.. ==> "[AAPL: 165.0][JNJ: 147.0][KO: 60.0][CS20: 30.0]"

jshell> p2.removeStock(s2)
jshell> p2.toString()
$.. ==> "[AAPL: 165.0][KO: 60.0][CS20: 30.0]"

jshell> p2.removeStock(new Stock("CS30", 0.0, sellStock))
jshell> p2.toString()
$.. ==> "[AAPL: 165.0][KO: 60.0][CS20: 30.0]"

jshell> p2.removeStock(new Stock("KO", 0.0, sellStock))
jshell> p2.toString()
$.. ==> "[AAPL: 165.0][CS20: 30.0]"
```

You are encouraged to test your code in jshell first.  You can run the test 
`Test2` as follows:

```shell
java -cp test.jar:. Test2
```


### Submit

Submit your question 2 by running the following command.

```shell
bash submit.sh q2
```


---


## Question 3 [5 marks]

Implement the following methods in `PortfolioManager.java`.

| Method Name  | calculateValue                                           |
| ------------ | -------------------------------------------------------- |
| Description  | Returns the total price of all stocks in the portfolio.  |
| Parameters   | Takes one input: `portfolio` (stock portfolio).          |
| Return       | Returns a `double` value corresponding to the total      |
|              | price of all stocks in the portfolio.                    |

| Method Name  | highestValueStock                                        |
| ------------ | -------------------------------------------------------- |
| Description  | Returns the stock with the highest value.  If there are  |
|              | multiple stocks with the same highest value, choose the  |
|              | stock that appears first in the original order.          |
| Parameters   | Takes one input: `portfolio` (stock portfolio).          |
| Return       | Returns a stock with the highest value.                  |

| Method Name  | predict                                                  |
| ------------ | -------------------------------------------------------- |
| Description  | Returns a new stock portfolio consisting of stocks that  |
|              | are obtained from applying each `PriceChange` from the   |
|              | input to each stock in the portfolio.                    |
|              | Check the sample run for the order of operation.         |
| Parameters   | Takes two inputs: `portfolio` (stock portfolio) and      |
|              | `changes` (infinite list of `PriceChange`).              |
| Return       | Returns a stock with the highest value.                  |


### Sample Run

```text
jshell> Stock s1 = new Stock("AAPL", 165.0, buyStock)
s1 ==> AAPL: 165.0
jshell> Stock s2 = new Stock("JNJ", 147.0, payDividend)
s2 ==> JNJ: 147.0
jshell> Stock s3 = new Stock("ORCL", 114.0, buyStock)
s3 ==> ORCL: 114.0
jshell> Stock s4 = new Stock("JNJ", 144.0, buyStock)
s4 ==> JNJ: 144.0
jshell> Stock s5 = new Stock("KO", 60.0, payDividend)
s5 ==> KO: 60.0

jshell> Portfolio<Stock> p1 = new Portfolio<>(InfiniteList.sentinel())
jshell> p1.addStock(s1)
jshell> p1.addStock(s2)
jshell> p1
p1 ==> [AAPL: 165.0][JNJ: 147.0]

jshell> Portfolio<Stock> p2 = new Portfolio<>(InfiniteList.sentinel())
jshell> p2.addStock(s3)
jshell> p2.addStock(s4)
jshell> p2.addStock(s5)
p2 ==> [ORCL: 114.0][JNJ: 144.0][KO: 60.0]

jshell> PortfolioManager.calculateValue(p1)
$.. ==> 312.0
jshell> PortfolioManager.calculateValue(p2)
$.. ==> 318.0

jshell> PortfolioManager.highestValueStock(p1)
$.. ==> AAPL: 165.0
jshell> PortfolioManager.highestValueStock(p2)
$.. ==> JNJ: 144.0

jshell> PriceChange up = s -> new Stock(s.getSymbol(), 1.1 * s.getPrice(), s.getStockAction());
jshell> PriceChange down = s -> new Stock(s.getSymbol(), 0.9 * s.getPrice(), s.getStockAction());
jshell> PriceChange moon = s -> new Stock(s.getSymbol(), 4.2 * s.getPrice(), s.getStockAction());
jshell> InfiniteList<PriceChange> lst = InfiniteList.of(up, down, moon);

jshell> PortfolioManager.predict(p1, lst)
$.. ==> [AAPL: 181.5][AAPL: 148.5][AAPL: 693.0][JNJ: 161.7][JNJ: 132.3][JNJ: 617.4]
jshell> PortfolioManager.predict(p2, lst)
$.. ==> [ORCL: 125.4][ORCL: 102.6][ORCL: 478.8][JNJ: 158.4][JNJ: 129.6][JNJ: 604.8][KO: 66.0][KO: 54.0][KO: 252.0]
jshell> PortfolioManager.predict(PortfolioManager.predict(p1, lst), lst)
$.. ==> [AAPL: 199.7][AAPL: 163.4][AAPL: 762.3][AAPL: 163.4][AAPL: 133.7][AAPL: 623.7][AAPL: 762.3][AAPL: 623.7][AAPL: 2910.6][JNJ: 177.9][JNJ: 145.5][JNJ: 679.1][JNJ: 145.5][JNJ: 119.1][JNJ: 555.7][JNJ: 679.1][JNJ: 555.7][JNJ: 2593.1]
```

You are encouraged to test your code in jshell first.  You can run the test 
`Test3` as follows:

```shell
java -cp test.jar:. Test3
```


### Submit

Submit your question 3 by running the following command.

```shell
bash submit.sh q3
```
