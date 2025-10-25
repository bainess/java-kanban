import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryTaskManager implements Manager {
    protected final Map<Integer, Task> taskList = new HashMap<>();
    protected final Map<Integer, Epic> epicList = new HashMap<>();
    protected final Map<Integer, Subtask> subtaskList = new HashMap<>();
    private final HistoryManager historyManager = new InMemoryHistoryManager();

    private int count = 0;

    private void setCounter() {
        int lastId = 0;
        if (!taskList.isEmpty() && !epicList.isEmpty() && !subtaskList.isEmpty()) {
            for (int key : taskList.keySet()) {
                if (key > lastId) lastId = key;
            }
            for (int key : epicList.keySet()) {
                if (key > lastId) lastId = key;
            }
            for (int key : subtaskList.keySet()) {
                if (key > lastId) lastId = key;
            }
            count = lastId + 1;
        }

    }

    @Override
    public void createTask(Task task) {
           setCounter();
           int id = count++;
           task.setId(id);
           taskList.put(id, task);
    }

    @Override
    public void createEpic(Epic epic) {
        setCounter();
        int id = count++;
        epic.setId(id);
        epicList.put(id, epic);
        epic.setEpicStatus(subtaskList);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        setCounter();
        int id = count++;
        subtask.setId(id);
        subtaskList.put(id, subtask);
        int epicId = subtask.getEpicId();
        Epic epic = epicList.get(epicId);
        epic.addSubtaskId(id);
        epic.setEpicStatus(subtaskList);
    }

    @Override
    public void removeTaskById(int id) {
        if (taskList.containsKey(id)) {
            taskList.remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public void removeEpicById(int id) {
        if (epicList.containsKey(id)) {
            List<Integer> epicIds = epicList.get(id).getSubtaskIds();
            for (int i = 0; i < epicIds.size(); i++) {
                subtaskList.remove(i);
            }
            epicList.remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public void removeSubtaskById(int id) {
        if (subtaskList.containsKey(id)) {
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
        taskList.clear();
        epicList.clear();
        subtaskList.clear();
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
        List<Task> taskArray = new ArrayList<>();
        for (Task task : taskList.values()) taskArray.add(task);
        return taskArray;
    }

    @Override
    public List<Epic> getAllEpic() {
        List<Epic> epicArray = new ArrayList<>();
        for (Epic epic : epicList.values()) {
            epicArray.add(epic);
        }
        return epicArray;
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        List<Subtask> subtaskArray = new ArrayList<>();
        for (Subtask subtask : subtaskList.values()) {
            subtaskArray.add(subtask);
        }
        return subtaskArray;
    }

    @Override
    public List<Subtask> getAllSubtasksByEpic(int id) {
        Epic epic = epicList.get(id);
        List<Subtask> subtasksOfEpic = new ArrayList<>();
        for (Integer i : epic.getSubtaskIds()) {
            subtasksOfEpic.add(subtaskList.get(i));
        }
        return subtasksOfEpic;
    }

    @Override
    public void removeAllTasks() {
        taskList.clear();
    }

    @Override
    public void removeAllEpics() {
        epicList.clear();
        subtaskList.clear();
    }

    @Override
    public void removeAllSubtasks() {
        subtaskList.clear();
    }

    @Override
    public List<Task> showHistory() {
        return historyManager.getHistory();
    }
    }

