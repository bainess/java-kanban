import java.time.LocalDateTime;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class Epic extends Task {
   private final List<Integer> subtaskIds = new ArrayList<>();
    protected LocalDateTime endTime;


    public Epic(String title, String description) {
        super(title, description);
    }

    public void setStartTime(LocalDateTime startTime) {
        if (this.startTime == null || startTime == null || startTime.isBefore(this.startTime)) this.startTime = startTime;
    }

    public void setDuration(Duration duration) {
        LocalDateTime endTime = getEndTime().orElse(null);
        LocalDateTime startTime = null;
        if (this.startTime != null) startTime = this.startTime;
        if (startTime != null && endTime != null) this.duration = Duration.between(startTime, endTime);

    }

    public LocalDateTime getStartTime(Map<Integer, Subtask> subtaskMap) {
        List<Subtask> subtasks = subtaskIds.stream().map(subtaskMap::get).toList();
        Optional<Subtask> task = subtasks.stream().min(Comparator.comparing(Subtask::getStartTime));
        return task.map(Task::getStartTime).orElse(null);
    }

    public Epic(int id, String title, String description, Status status, LocalDateTime startTime, Duration duration) {
        super(title, description, startTime, duration);
        this.id = id;
        this.status = status;
        if (getEndTime().isPresent()) {
            endTime = getEndTime().get();
        } else {
            endTime = null;
        }
    }

    private LocalDateTime getEndTime(Map<Integer, Subtask> subtaskMap) {
        List<Integer> subIds = getSubtaskIds();
        LocalDateTime startTime = subtaskMap.get(subIds.getFirst()).getStartTime();
        Duration epicDuration = subIds.stream().map(id -> {
            return subtaskMap.get(id).getDuration();
        }).reduce(Duration.ofDays(0), Duration::plus);
        return startTime.plus(epicDuration);
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
        return "Epic " + this.getId() + " " + this.title + " "  + this.description + " "  + this.status + this.startTime + " " + this.duration + " subtasks: " + this.subtaskIds;
    }
}
