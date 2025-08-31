import java.util.stream.StreamSupport;

public class Task {
    String title;
    String description;
    private static int count = 0;
    private int id;
    Status status;

    Task(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
        id = count++;
    }
    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Task task)) return false;
        return id == task.id;
    }

    public Integer getId(){
        return id;
    }

    @Override
    public int hashCode() {
        int result = 31 * id;
        return result;
    }

    @Override
    public String toString () {
        return "Task" + this.title + " " + this.id + " "  + this.description + " "  + this.status;

    }

}
