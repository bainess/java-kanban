import java.util.stream.StreamSupport;

public class Task {

    String title;
    String description;
    private static int count = 0;
    private final int id;
    Status status;

   public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = status;
        id = count++;
    }

    public Task(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
        id = count++;
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
    public Integer getId(){
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
        int result = 31 * id;
        return result;
    }

    @Override
    public String toString () {
        return "Task " + this.id  + " " + this.title + " "  + this.description + " "  + this.status;
    }
}
