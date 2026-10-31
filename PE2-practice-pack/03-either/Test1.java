import cs2030s.fp.*;
import java.util.List;
public class Test1 {
 public static void main(String[] args) throws Throwable {
CS2030STest.check("left value", 2, () -> Either.left(2).getLeft());
CS2030STest.check("right value", "two", () -> Either.right("two").getRight());
CS2030STest.check("left flag", true, () -> Either.left(2).isLeft());
CS2030STest.check("right flag", false, () -> Either.left(2).isRight());
CS2030STest.check("right flags", true, () -> Either.right(2).isRight() && !Either.right(2).isLeft());
CS2030STest.check("left format", "Left[2]", () -> Either.left(2).toString());
CS2030STest.check("right format", "Right[two]", () -> Either.right("two").toString());
CS2030STest.check("value equality", true, () -> Either.left(new String("x")).equals(Either.left("x")));
CS2030STest.check("branch equality", false, () -> Either.left("x").equals(Either.right("x")));
CS2030STest.check("null equality", true, () -> Either.right(null).equals(Either.right(null)));
CS2030STest.check("null versus value", false, () -> Either.left(null).equals(Either.left("x")));
CS2030STest.check("non Either", false, () -> Either.left(2).equals(2));
CS2030STest.throwsType("wrong side", java.util.NoSuchElementException.class, ()->Either.left(2).getRight());
CS2030STest.throwsType("wrong side reverse", java.util.NoSuchElementException.class, ()->Either.right(2).getLeft());
 CS2030STest.finish();
 }
}
