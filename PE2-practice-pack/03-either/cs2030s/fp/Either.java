package cs2030s.fp;
import java.util.NoSuchElementException;

public abstract class Either<L, R> {

  private L left;
  private R right;

  private Either(L left, R right) {
    this.left = left;
    this.right = right;
  }

  public static <L, R> Either<L, R> left (L left) {
    return new Either.Left<L, R>(left);
  }

  public static <L, R> Either<L, R> right (R right) {
    return new Either.Right<L, R>(right);
  } 

  public abstract boolean isLeft();
  public abstract boolean isRight();
  public abstract L getLeft();
  public abstract R getRight();
  public abstract <U, V> Either<U, V> map(
      Transformer<? super L, ? extends U> l,
      Transformer<? super R, ? extends V> r
      );
  public abstract <U, V> Either<U, V> flatMap(
      Transformer<? super L, ? extends Either<? extends U, ? extends V>> l,
      Transformer<? super R, ? extends Either<? extends U, ? extends V>> r
      );
  public abstract <U> U fold(
      Transformer<? super L, ? extends U> l,
      Transformer<? super R, ? extends U> r
      );

  public static final class Left<L, R> extends Either<L, R>{

    private Left(L left) {
      super(left, null);
    }

    @Override
    public boolean isLeft() {
      return true;
    }

    @Override
    public boolean isRight() {
      return false;
    }

    @Override
    public L getLeft() {
      return super.left;
    }

    @Override
    public R getRight() {
      throw new NoSuchElementException();
    }

    @Override
    public String toString() {
      return String.format("Left[%s]", super.left);
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof Left<?, ?>) {
        Left<?, ?> tmp = (Left<?, ?>) o;
        if (tmp.getLeft() == null) {
          return tmp.getLeft() == super.left;
        }

        return tmp.getLeft().equals(super.left);
      }
      return false;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    @Override
    public <U, V> Either<U, V> map(
        Transformer<? super L, ? extends U> l,
        Transformer<? super R, ? extends V> r
        ) {
      return Either.left(l.transform(super.left));
        }

    @Override
    public <U, V> Either<U, V> flatMap(
        Transformer<? super L, ? extends Either<? extends U, ? extends V>> l,
        Transformer<? super R, ? extends Either<? extends U, ? extends V>> r
        ){
      @SuppressWarnings("unchecked")
      Either<U, V> res2 = (Either<U, V>) l.transform(super.left);
      return res2;
        }

    @Override
    public <U> U fold(
        Transformer<? super L, ? extends U> l,
        Transformer<? super R, ? extends U> r
        ) {
      return l.transform(super.left);
        }

  }

  public static final class Right<L, R> extends Either<L, R>{

    private Right(R right) {
      super(null, right);
    }

    @Override
    public boolean isLeft() {
      return false;
    }

    @Override
    public boolean isRight() {
      return true;
    }

    @Override
    public L getLeft() {
      throw new NoSuchElementException();
    }

    @Override
    public R getRight() {
      return super.right;
    }

    @Override
    public String toString() {
      return String.format("Right[%s]", super.right);
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof Right<?, ?>) {
        Right<?, ?> tmp = (Right<?, ?>) o;
        if (tmp.getRight() == null) {
          return tmp.getRight() == super.right;
        }

        return tmp.getRight().equals(super.right);
      }
      return false;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    @Override
    public <U, V> Either<U, V> map(
        Transformer<? super L, ? extends U> l,
        Transformer<? super R, ? extends V> r
        ) {
      return Either.right(r.transform(super.right));
        }

    @Override
    public <U, V> Either<U, V> flatMap(
        Transformer<? super L, ? extends Either<? extends U, ? extends V>> l,
        Transformer<? super R, ? extends Either<? extends U, ? extends V>> r
        ){
      @SuppressWarnings("unchecked")
      Either<U, V> res2 = (Either<U, V>) r.transform(super.right);
      return res2;
        }

    @Override
    public <U> U fold(
        Transformer<? super L, ? extends U> l,
        Transformer<? super R, ? extends U> r
        ) {
      return r.transform(super.right);
        }

  }
}
