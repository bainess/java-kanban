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

    public void setStartTime(Map<Integer, Subtask> subtaskMap) {
       this.startTime = getStartTime(subtaskMap);
    }

    public void setDuration(Map<Integer, Subtask> subtaskMap) {
        LocalDateTime endTime = getEndTime(subtaskMap);
        LocalDateTime startTime = getStartTime(subtaskMap);
        if (startTime != null && endTime != null) this.duration = Duration.between(startTime, endTime);

    }

    public LocalDateTime getStartTime(Map<Integer, Subtask> subtaskMap) {
        Optional<LocalDateTime> time = subtaskMap.entrySet().stream().
                filter(entry -> subtaskIds.contains(entry.getKey()))
                .filter(subtask -> subtask.getValue() != null)
                .map(entry -> entry.getValue())
                .map(Subtask::getStartTime)
                .sorted()
                .findFirst();
        return time.orElseGet(() -> null);
    }

    public Epic(int id, String title, String description, Status status, LocalDateTime startTime, Duration duration) {
        super(title, description, startTime, duration);
        this.id = id;
        this.status = status;
    }

    private LocalDateTime getEndTime(Map<Integer, Subtask> subtaskMap) {
        LocalDateTime startTime = null;
        Duration epicDuration = null;
        startTime = getStartTime(subtaskMap);
        if (startTime == null) return null;
        epicDuration = subtaskIds.stream().map(id -> subtaskMap.get(id).getDuration())
                    .reduce(Duration.ofDays(0), Duration::plus);
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
        List<Status> statusesList = subtaskMap.values().stream().filter(subtask ->this.subtaskIds.contains(subtask.getId())).map(Subtask::getStatus).toList();
        if (statusesList.contains(Status.IN_PROGRESS)) this.status = Status.IN_PROGRESS;
        if (statusesList.stream().allMatch(status -> status.equals(Status.DONE))) {
            this.status = Status.DONE;
        }  else  if (statusesList.stream().allMatch(status -> status.equals(Status.NEW))) {
            this.status = Status.NEW;
        }
    }

    @Override
    public String toString() {
        return "Epic " + this.getId() + " " + this.title + " "  + this.description + " "  + this.status + " " + this.startTime + " " + this.duration + " subtasks: " + this.subtaskIds;
    }
}
