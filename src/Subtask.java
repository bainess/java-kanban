import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String title, String description, int epicId) {
        super(title, description);
        this.epicId = epicId;
    }

    public Subtask(String title, String description, Status status, LocalDateTime startTime, Duration duration, int epicId) {
        super(title, description, status, startTime, duration);
        this.epicId = epicId;
    }

    public Subtask(int id, String title, String description, Status status, LocalDateTime startTime, Duration duration, int epicId) {
        super(id, title, description, status, startTime, duration);
        this.epicId = epicId;
    }


    public int getEpicId() {
        return this.epicId;
    }

    @Override
    public String toString() {
        return "Subtask " + this.getId()  + " " + this.title + " "  + this.description + " "  + this.status + " " + this.startTime + " " + this.duration + " " + this.epicId + "\n";
    }
}
