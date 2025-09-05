import java.util.ArrayList;
import java.util.HashMap;


public class Manager {
    HashMap<Integer, Task> taskList = new HashMap<>();
    HashMap<Integer, Epic> epicList = new HashMap<>();
    HashMap<Integer, Subtask> subtaskList = new HashMap<>();
    private int count = 0;
    public void createTask(Task task) {
        int id = count++;
        task.setId(id);
        taskList.put(id, task);
    }

    public void createEpic(Epic epic) {
        int id = count++;
        epic.setId(id);
        epicList.put(id, epic);
        epic.setStatus(subtaskList);
    }
    public void createSubtask (Subtask subtask) {
        int id = count++;
        subtask.setId(id);
        int epicId = subtask.getEpicId();
        subtaskList.put(id, subtask);
        Epic epic = epicList.get(epicId);
        epic.addSubtaskId(id);
        epic.setStatus(subtaskList);
    }

    public void removeTaskById(int id){
        if (taskList.containsKey(id)) {
            taskList.remove(id);
        }
    }

    public void removeEpicById(int id){
        if (epicList.containsKey(id)){
            ArrayList<Integer> epicIds = epicList.get(id).subtaskIds;
            for (int i = 0; i < epicIds.size(); i++) {
                subtaskList.remove(i);
            }
            epicList.remove(id);
        }
    }
    public void removeSubtaskById(int id){
        if (subtaskList.containsKey(id)) {
            Subtask subToRemove = subtaskList.get(id);
            int epicId = subToRemove.getEpicId();
            Epic epic = epicList.get(epicId);
            epic.removeSubTaskId(id);
            subtaskList.remove(id);
        }
    }

    public Object getTaskById(int id) {
        if (taskList.containsKey(id)) {
            return taskList.get(id);
        } else if (epicList.containsKey(id)) {
            return epicList.get(id);
        } else if (subtaskList.containsKey(id)){
            return subtaskList.get(id);
        }
        return "no task by id" + id;
    }


    public void removeAll() {
        taskList.clear();
        epicList.clear();
        subtaskList.clear();
    }

    public void editTask (Task task) {
        int id = task.getId();
            if (taskList.containsKey(id)) {
                taskList.put(id, task);
            }
    }
    public void editEpic(Epic epic){
        int id = epic.getId();
        Epic.setStatus(subtaskList);
        if (epicList.containsKey(id)) {
            epicList.put(id, epic);
        }
    }
    public void editSubtask(Subtask subtask) {
        int id = subtask.getId();
        if (subtaskList.containsKey(id)) {
            subtaskList.put(id, subtask);
        }
    }
    public Object getAllTasks() {
        ArrayList<Task> taskArray= new ArrayList<>();
        for (Task task : taskList.values()) taskArray.add(task);
        return taskArray;
    }

    public Object getAllEpic(){
        ArrayList<Task> epicArray= new ArrayList<>();
        for (Epic epic : epicList.values()) {
            epicArray.add(epic);
        }
        return epicArray;
    }

    public Object getAllSubtasks(){
        ArrayList<Task> subtaskArray= new ArrayList<>();
        for (Subtask subtask : subtaskList.values()) {
            subtaskArray.add(subtask);
        }
        return subtaskArray;
    }

    public void removeAllTasks () {
        taskList.clear();
    }

    public void removeAllEpics() {
        epicList.clear();
    }

    public void removeAllSubtasks() {
        subtaskList.clear();
    }
}

