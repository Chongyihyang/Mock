public abstract class Job {

  private final String name;

  public Job(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

  public abstract int execute(int curr);

  public abstract boolean hasExecuted();

  public abstract boolean enoughEnergy(int curr);
}
