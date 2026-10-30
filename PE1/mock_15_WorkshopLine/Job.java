public abstract class Job {

  private final String name;

  public Job(String name) throws IllegalArgumentException {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

  public abstract boolean isCompleted();

  public abstract void execute();

}
