import java.util.ArrayList;

public class Epic extends Task{
    ArrayList<Subtask> subtasksList;
    ArrayList<Integer> subtaskIds = new ArrayList<>();

    Epic(String title, String description, ArrayList<Subtask> subtasksList) {
        super(title, description);
        this.subtasksList = subtasksList;
        setStatus(subtasksList);
        for (Subtask subtask : subtasksList) {
            subtaskIds.add(subtask.getId());
            subtask.setEpicId(this.getId());
        }
    }

    private void setStatus (ArrayList<Subtask> subtasksList) {
        int statusNew = 0;
        int statusDone = 0;
        for (Subtask subtask : subtasksList) {
            if (subtask.getStatus().equals(Status.NEW)) {
                statusNew++;
            } else if (subtask.getStatus().equals(Status.DONE)) {
                statusDone++;
            }
        }
        if (subtasksList.size() == statusNew) {
            this.status = Status.NEW;
        } else if (subtasksList.size() == statusDone) {
            this.status = Status.DONE;
        } else {
            this.status = Status.IN_PROGRESS;
        }
    }



}
