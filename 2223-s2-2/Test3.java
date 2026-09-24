/** Partial structural checks only. Exception flow and OO design need manual review. */
public final class Test3 {
  private Test3() {
  }

  private static boolean eventExists() {
    try {
      return !Class.forName("Event").isInterface();
    } catch (ClassNotFoundException e) {
      return false;
    }
  }

  private static boolean checkedExceptionExists() {
    try {
      Class<?> type = Class.forName("IllegalCancellationException");
      return Exception.class.isAssignableFrom(type)
          && !RuntimeException.class.isAssignableFrom(type);
    } catch (ClassNotFoundException e) {
      return false;
    }
  }

  public static void main(String[] args) {
    TestSupport t = new TestSupport();
    t.check("Event class exists", true, eventExists());
    t.check("IllegalCancellationException is checked", true, checkedExceptionExists());
    t.finish();
  }
}
