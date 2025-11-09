import java.time.Duration;
import java.time.LocalDateTime;

public class Task implements Comparable<Task> {

    protected String title;
    protected String description;
    protected int id;
    protected Status status;
    protected Duration duration = null;
    protected LocalDateTime startTime = null;

    protected Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    protected Task(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

   public Task(String title, String description, LocalDateTime startTime, Duration duration) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(String title, String description, Status status,  LocalDateTime startTime, Duration duration) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(int id, String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.id = id;
    }

    public Task(int id, String title, String description, Status status,  LocalDateTime startTime, Duration duration) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.id = id;
        this.startTime = startTime;
        this.duration = duration;
    }


    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
       return duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDescription(String description) {
       this.description = description;
   }

   public String getDescription() {
       return this.description;
   }

   public Integer getId() {
        return id;
   }

    public void setStatus(Status status) {
       this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Task task)) return false;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return 31 * id;
    }

    @Override
    public String toString() {
        return "Task " + this.id  + " " + this.title + " "  + this.description + " "  + this.status + " " + this.startTime + " " + this.duration + "\n";
   }

    @Override
    public int compareTo(Task task) {
        return this.startTime.compareTo(task.startTime);
    }
}
