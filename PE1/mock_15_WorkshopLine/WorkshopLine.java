class WorkshopLine {

  private Queue<Job> jobs;
  private int capacity;
  private int curr = 0;

  public WorkshopLine(int capacity) {
    this.jobs = new Queue<>(capacity);
    this.capacity = capacity;
  }

  public String addCut(String name, int pieces) {
    Cut cut = new Cut(name, pieces);
    if (this.capacity > this.curr) {
      this.jobs.enqueue(cut);
      this.curr += 1;
      return "Added: " + cut.toString();
    }

    return "Full: " + cut.toString();
  }

  public String addEtch(String name, int pieces) {
    Etch etch= new Etch(name, pieces);
    if (this.capacity > this.curr) {
      this.jobs.enqueue(etch);
      this.curr += 1;
      return "Added: " + etch.toString();
    }

    return "Full: " + etch.toString();
  }


  public String runNext() {
    if (this.jobs.size() == 0) {
      return "Idle";
    }

    Job job = this.jobs.dequeue();
    job.execute();
    if (job.isCompleted()) {
      return "Finished: " + job.toString();
    } 
    this.jobs.enqueue(job);
    return "Waiting: " + job.toString();
  }


  public String pending() {
    return this.jobs.queueInfo();
  }

}
