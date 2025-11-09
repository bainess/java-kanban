import java.time.LocalDateTime;
import java.time.Duration;
import java.util.*;

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
        Duration epicDuration = subIds.stream().map(id -> subtaskMap.get(id).getDuration())
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
        //AtomicInteger statusNew = new AtomicInteger();
       // AtomicInteger statusDone = new AtomicInteger();
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
