import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class InMemoryTaskManager implements Manager {
    protected final Map<Integer, Task> taskList = new HashMap<>();
    protected final Map<Integer, Epic> epicList = new HashMap<>();
    protected final Map<Integer, Subtask> subtaskList = new HashMap<>();
    private final HistoryManager historyManager = new InMemoryHistoryManager();
    protected int count = 0;
    protected TreeSet<Task> tasksPrioritized = new TreeSet<>(Comparator.comparing(Task::getStartTime));

    @Override
    public void createTask(Task task) {
        if (canScheduleAtTime(task)) {
            canScheduleAtTime(task);
            int id = count++;
            task.setId(id);
            taskList.put(id, task);
            addToPrioritizedTasks(task);
        }
    }

    @Override
    public void createEpic(Epic epic) {
        int id = count++;
        epic.setId(id);
        epicList.put(id, epic);
        epic.setEpicStatus(subtaskList);
        epic.setStartTime(subtaskList);
        epic.setDuration(subtaskList);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        if (canScheduleAtTime(subtask)) {
            int id = count++;
            subtask.setId(id);
            subtaskList.put(id, subtask);
            int epicId = subtask.getEpicId();
            Epic epic = epicList.get(epicId);
            epic.addSubtaskId(id);
            epic.setEpicStatus(subtaskList);
            epic.setStartTime(subtaskList);
            epic.setDuration(subtaskList);
            addToPrioritizedTasks(subtask);
        }

    }

    @Override
    public void removeTaskById(int id) {
        if (taskList.containsKey(id)) {
            tasksPrioritized.remove(taskList.get(id));
            taskList.remove(id);
            historyManager.remove(id);

        }
    }

    @Override
    public void removeEpicById(int id) {
        if (epicList.containsKey(id)) {
            tasksPrioritized.remove(epicList.get(id));
            List<Integer> epicIds = epicList.get(id).getSubtaskIds();
            subtaskList.entrySet().stream()
                    .filter(entry -> epicIds.contains(entry.getKey()))
                    .map(entry -> subtaskList.remove(id)).findFirst();
        }
    }

    @Override
    public void removeSubtaskById(int id) {
        if (subtaskList.containsKey(id)) {
            tasksPrioritized.remove(subtaskList.get(id));
            historyManager.remove(id);
            Subtask subToRemove = subtaskList.get(id);
            int epicId = subToRemove.getEpicId();
            Epic epic = epicList.get(epicId);
            epic.removeSubTaskId(id);
            subtaskList.remove(id);
        }
    }

    @Override
    public Task getTaskById(int id) {
        boolean containsKey = taskList.containsKey(id);
        if (containsKey) {
            historyManager.addToHistoryList(taskList.get(id));
            return taskList.get(id);
        } else {
            System.out.println("no task by id " + id);
            return null;
        }
    }

    @Override
    public Epic getEpicById(int id) {
        if  (epicList.containsKey(id)) {
            historyManager.addToHistoryList(epicList.get(id));
            return epicList.get(id);
        } else {
            System.out.println("no epic by id " + id);
            return null;
        }
    }

    @Override
    public Subtask getSubtaskById(int id) {
        if (subtaskList.containsKey(id)) {
            historyManager.addToHistoryList(subtaskList.get(id));
            return subtaskList.get(id);
        }
        System.out.println("no subtask by id " + id);
        return null;
    }

    @Override
    public void removeAll() {
        tasksPrioritized.clear();
        taskList.clear();
        epicList.clear();
        subtaskList.clear();
        count = 0;
    }

    @Override
    public void editTask(Task task) {
        int id = task.getId();
            if (taskList.containsKey(id)) {
                taskList.put(id, task);
            }
    }

    @Override
    public void editEpic(Epic epic) {
        int id = epic.getId();
        epic.setEpicStatus(subtaskList);
        if (epicList.containsKey(id)) {
            epicList.put(id, epic);
        }
    }

    @Override
    public void editSubtask(Subtask subtask) {
        int id = subtask.getId();
        if (subtaskList.containsKey(id)) {
            subtaskList.put(id, subtask);
        }
        Epic epic = epicList.get(subtask.getEpicId());
        epic.setEpicStatus(subtaskList);
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(taskList.values());
    }

    @Override
    public List<Epic> getAllEpic() {
        return new ArrayList<>(epicList.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtaskList.values());
    }

    @Override
    public List<Subtask> getAllSubtasksByEpic(int id) {
        Epic epic = epicList.get(id);
        return epic.getSubtaskIds()
                .stream()
                .map(subtaskList::get)
                .toList();
    }

    @Override
    public void removeAllTasks() {
        tasksPrioritized.removeAll(taskList.values());
        taskList.clear();
    }

    @Override
    public void removeAllEpics() {
        tasksPrioritized.removeAll(epicList.values());
        epicList.clear();
        subtaskList.clear();
    }

    @Override
    public void removeAllSubtasks() {
        tasksPrioritized.removeAll(subtaskList.values());
        subtaskList.clear();
    }

    @Override
    public List<Task> showHistory() {
        return historyManager.getHistory();
    }

    public void addToPrioritizedTasks(Task task) {
        tasksPrioritized.add(task);
    }

    public void addToPrioritizedTasks(Subtask task) {
        tasksPrioritized.add(task);
    }


    public TreeSet<Task> getPrioritizedTasks() {
        return tasksPrioritized;
    }

    protected boolean canScheduleAtTime(Task newTask) {
        if (newTask.getStartTime() == null) return true;
        if (tasksPrioritized.isEmpty()) return true;
        LocalDateTime taskStart = newTask.getStartTime();
        LocalDateTime taskEnd = newTask.getStartTime().plus(newTask.getDuration());
        boolean a = false;
        for (Task task : tasksPrioritized) {
            if (taskStart.isAfter(task.getStartTime().plus(task.getDuration()))
                    || taskEnd.isBefore(task.getStartTime())) {
                a =  true;
            } else {
                a = false;
            }
        }
        return a;
    }
}

