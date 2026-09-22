public abstract class Equipment {

  private boolean inUse = false;

  public boolean isInUse() {
    return this.inUse;
  }

  public void setInUse(boolean selector) {
    this.inUse = selector;
  }
  
  public abstract void repair(); 

}


