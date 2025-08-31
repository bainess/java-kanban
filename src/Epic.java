import java.util.ArrayList;

public class Epic extends Task{
    ArrayList<Subtask> subtasksList;

    Epic(String title, String description, Status status, ArrayList<Subtask> subtasksList) {
        super(title, description, status);
        this.subtasksList = subtasksList;
    }

    @Override
    public String toString () {
        return this.title + " " + this.getId() + " "  + this.description + " "  + this.status;

    }
}
