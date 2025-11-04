import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Epic extends Task {
   private List<Integer> subtaskIds = new ArrayList<>();
    protected LocalDateTime endTime;

    public Epic(String title, String description, LocalDateTime startTime, Duration duration) {
        super(title, description, startTime, duration);
    }


    private LocalDateTime endTime(Map<Integer, Subtask> subtaskMap) {
        List<Integer> subIds = getSubtaskIds();
        LocalDateTime startTime = subtaskMap.get(subIds.getFirst()).getStartTime();
        Duration epicDuration = subIds.stream().map(id -> {
            return subtaskMap.get(id).getDuration();
        }).reduce(Duration.ofDays(0), Duration::plus);
        return startTime.plus(duration);
    }

    public Epic(int id, String title, String description, Status status, LocalDateTime startTime, Duration duration) {
        super(title, description, startTime, duration);
        this.id = id;
        this.status = status;
    }

    public void addSubtaskId(int id) {
        subtaskIds.add(id);
    }

    public List<Integer> getSubtaskIds() {
        return  this.subtaskIds;
    }

    public void removeSubTaskId(int id) {
        for (int i = 0; i < subtaskIds.size(); i++) {
            int subId = subtaskIds.get(i);
            if (subId == id) {
                subtaskIds.remove(i);
                return;
            }
        }
    }

    public void setEpicStatus(Map<Integer, Subtask> subtaskMap) {
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
    public String toString() {
        return "Epic " + this.getId() + " " + this.title + " "  + this.description + " "  + this.status + " subtasks: " + this.subtaskIds;
    }
}
