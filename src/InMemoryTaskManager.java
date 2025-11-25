import java.time.LocalDateTime;
import java.util.*;


public class InMemoryTaskManager implements Manager {
    protected final Map<Integer, Task> taskList = new HashMap<>();
    protected final Map<Integer, Epic> epicList = new HashMap<>();
    protected final Map<Integer, Subtask> subtaskList = new HashMap<>();
    private final HistoryManager historyManager = new InMemoryHistoryManager();
    protected int count = 1;
    protected TreeSet<Task> tasksPrioritized = new TreeSet<>(Comparator.comparing(Task::getStartTime));

    @Override
    public void createTask(Task task) throws TaskCreationException {
        if (canScheduleAtTime(task)) {
            int id = count++;
            task.setId(id);
            taskList.put(id, task);
            addToPrioritizedTasks(task);
        } else {
            throw new TaskCreationException("Time slot has been occupied");
        }
    }

    @Override
    public void createEpic(Epic epic) {
        int id = count++;
        Epic newEpic = new Epic(epic.title, epic.description);
        newEpic.setId(id);
        epicList.put(id, newEpic);
    }

    @Override
    public void createSubtask(Subtask subtask) throws TaskCreationException, SubtaskCreationException {
        if (!epicList.containsKey(subtask.getEpicId())) throw new SubtaskCreationException("The epic by given id doesn't exist");
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
        } else {
            throw new TaskCreationException("Time slot has been occupied");
        }

    }

    @Override
    public void removeTaskById(int id) {
        if (taskList.containsKey(id)) {
            Task task = taskList.get(id);
            if (task.getStartTime() != null) tasksPrioritized.remove(task);
            taskList.remove(id);
            historyManager.remove(id);

        }
    }

    @Override
    public void removeEpicById(int id) {
        if (epicList.containsKey(id)) {
            List<Integer> epicSubtasksIds = epicList.get(id).getSubtaskIds();
            for (int subtaskId : epicSubtasksIds) {
                subtaskList.remove(subtaskId);
            }
            historyManager.remove(id);
            epicList.remove(id);
        }
    }

    @Override
    public void removeSubtaskById(int id) {
        if (subtaskList.containsKey(id)) {
            Subtask subtask = subtaskList.get(id);
            if (subtask.getStartTime() != null) tasksPrioritized.remove(subtask);
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
        if (taskList.containsKey(id)) {
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
        count = 1;
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

    private void addToPrioritizedTasks(Task task) {
        if (task.getStartTime() != null) {
            tasksPrioritized.add(task);
        }

    }

    public TreeSet<Task> getPrioritizedTasks() {
         return new TreeSet<Task>(tasksPrioritized);

    }

    protected boolean canScheduleAtTime(Task newTask) {
        if (newTask.getStartTime() == null) return true;
        if (tasksPrioritized.isEmpty()) return true;
        LocalDateTime taskStart = newTask.getStartTime();
        LocalDateTime taskEnd = newTask.getStartTime().plus(newTask.getDuration());
        boolean canSchedule = false;
        for (Task task : tasksPrioritized) {
            if (taskStart.isAfter(task.getStartTime().plus(task.getDuration()))
                    || taskEnd.isBefore(task.getStartTime())) {
                canSchedule =  true;
            } else {
               return canSchedule = false;
            }
        }
        return canSchedule;
    }
}

