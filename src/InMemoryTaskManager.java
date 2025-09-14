import java.util.ArrayList;
import java.util.HashMap;


public class InMemoryTaskManager implements Manager {
    private  HashMap<Integer, Task> taskList = new HashMap<>();
    private HashMap<Integer, Epic> epicList = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskList = new HashMap<>();
    public InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    private int count = 0;
    @Override
    public void createTask(Task task) {
        int id = count++;
        task.setId(id);
        taskList.put(id, task);
    }

    @Override
    public void createEpic(Epic epic) {
        int id = count++;
        epic.setId(id);
        epicList.put(id, epic);
        epic.setStatus(subtaskList);
    }
    @Override
    public void createSubtask(Subtask subtask) {
        int id = count++;
        subtask.setId(id);
        int epicId = subtask.getEpicId();
        subtaskList.put(id, subtask);
        Epic epic = epicList.get(epicId);
        epic.addSubtaskId(id);
        epic.setStatus(subtaskList);
    }

    @Override
    public void removeTaskById(int id){
        if (taskList.containsKey(id)) {
            taskList.remove(id);
        }
    }

    @Override
    public void removeEpicById(int id){
        if (epicList.containsKey(id)){
            ArrayList<Integer> epicIds = epicList.get(id).subtaskIds;
            for (int i = 0; i < epicIds.size(); i++) {
                subtaskList.remove(i);
            }
            epicList.remove(id);
        }
    }
    @Override
    public void removeSubtaskById(int id){
        if (subtaskList.containsKey(id)) {
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
            historyManager.add(taskList.get(id));
            return taskList.get(id);
        } else {
            System.out.println("no task by id" + id);
            return null;
        }
    }
    @Override
    public Epic getEpicById(int id) {
        if  (epicList.containsKey(id)) {
            historyManager.add(epicList.get(id));
            return epicList.get(id);
        } else {
            System.out.println("no task by id" + id);
            return null;
        }
    }
    @Override
    public Subtask getSubtaskById(int id) {
        if (subtaskList.containsKey(id)){
            historyManager.add(subtaskList.get(id));
            return subtaskList.get(id);
        }
        System.out.println("no task by id" + id);
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
    public void editEpic(Epic epic){
        int id = epic.getId();
        epic.setStatus(subtaskList);
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
        epic.setStatus(subtaskList);
    }
    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> taskArray= new ArrayList<>();
        for (Task task : taskList.values()) taskArray.add(task);
        return taskArray;
    }

    @Override
    public ArrayList<Epic> getAllEpic(){
        ArrayList<Epic> epicArray= new ArrayList<>();
        for (Epic epic : epicList.values()) {
            epicArray.add(epic);
        }
        return epicArray;
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks(){
        ArrayList<Subtask> subtaskArray= new ArrayList<>();
        for (Subtask subtask : subtaskList.values()) {
            subtaskArray.add(subtask);
        }
        return subtaskArray;
    }

    @Override
    public ArrayList<Subtask> getAllSubtasksByEpic(int id) {
        Epic epic = epicList.get(id);
        ArrayList<Subtask> subtasksOfEpic = new ArrayList<>();
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

    public ArrayList<Task> showHistory() {
        return historyManager.getHistory();
    }
}

