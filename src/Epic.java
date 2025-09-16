import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Epic extends Task{
   private List<Integer> subtaskIds = new ArrayList<>();

    public Epic(String title, String description) {
        super(title, description);
    }

    public void addSubtaskId(int id) {
        subtaskIds.add(id);
    }
    public List<Integer> getSubtaskIds() {return  this.subtaskIds;}
    public void removeSubTaskId (int id) {
        for (int i = 0; i < subtaskIds.size(); i++) {
            int subId = subtaskIds.get(i);
            if (subId == id) {
                subtaskIds.remove(i);
            }
        }
    }

    public void setStatus(HashMap<Integer, Subtask> subtaskMap) {
        int statusNew = 0;
        int statusDone = 0;
        for (Subtask subtask : subtaskMap.values()) {
            if (this.subtaskIds.contains(subtask.getId())) {
                if (subtask.getStatus().equals(Status.NEW)) {
                    statusNew++;
                } else if (subtask.getStatus().equals(Status.DONE)) {
                    statusDone++;
                }
            }

        }

        if (this.subtaskIds.size() == statusNew) {
            this.status = Status.NEW;
        } else if (this.subtaskIds.size() == statusDone) {
            this.status = Status.DONE;
        } else {
            this.status = Status.IN_PROGRESS;
        }
    }

    @Override
    public String toString () {
        return "Epic " + this.getId() + " " + this.title + " "  + this.description + " "  + this.status + " subtasks: " + this.subtaskIds;
    }
}
