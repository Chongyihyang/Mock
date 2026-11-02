package cs2030s.fp;
public abstract class Try<T> {
 // TODO: derive the API and implement the paper.
 //
 
  private Producer<T> prod;
  private T res;
  private Throwable e;

  private Try(Producer<T> prod) {
    this.prod = prod;
  }

  private Try(T res) {
    this.res = res;
  }

  private Try(Throwable res) {
    this.e = e;
  }

  public static <T> Try<T> success(T res) {
    return new Try.Success(res);
  }

  public static <T> Try<T> failure(T res) {
    return new Try.Failure(res);
  }

  public static <T> Try<T> of(Producer<T> prod) {
    try {
      return new Try.Success(prod.produce());
    } catch (Exception e) {
      return new Try<T>.Failure(e);
    }
  }


  public static class Success<T> extends Try<T> {
    
    private Success(T res) {
      super(res);
    }

  }

  public static class Failure<T> extends Try<T> {

    private Failure(Throwable e) {
      super(e);
    }
    
  }

}
