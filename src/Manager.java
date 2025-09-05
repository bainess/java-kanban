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
    //  оба метода ниже работают с любым типом заданий, можно ли оставить их или обязательно каждому классу
    //  свой метод? (если да, то почему)
    public void addAnyTask(Object task) {
        String taskClass = String.valueOf(task.getClass());
        Integer id;
        switch (taskClass) {
            case "class Task":
                Task newTask = (Task) task;
                id = newTask.getId();
                taskList.put(id, newTask);
                break;
            case "class Epic":
                Epic newEpic = (Epic) task;
                id = newEpic.getId();
                epicList.put(id, newEpic);
                newEpic.setStatus(subtaskList);
                break;
            case "class Subtask":
                Subtask newSubtask = (Subtask) task;
                id = newSubtask.getId();
                int epicId = newSubtask.getEpicId();
                subtaskList.put(id, newSubtask);
                Epic epic = epicList.get(epicId);
                epic.addSubtaskId(id);
                epic.setStatus(subtaskList);
                break;
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

    public void removeAnyTaskById(int id){
        if (taskList.containsKey(id)){
            taskList.remove(id);
        } else if (epicList.containsKey(id)){
            ArrayList<Integer> epicIds = epicList.get(id).subtaskIds;
            for (int i = 0; i < epicIds.size(); i++) {
                subtaskList.remove(i);
            }
            epicList.remove(id);
        } else if (subtaskList.containsKey(id)) {
            Subtask subToRemove = subtaskList.get(id);
            int epicId = subToRemove.getEpicId();
            Epic epic = epicList.get(epicId);
            epic.removeSubTaskId(id);
            subtaskList.remove(id);
        }
    }

    public void removeAll() {
        taskList.clear();
        epicList.clear();
        subtaskList.clear();
    }

    public void editTask (Object task) {
        int id = -1;
        String classTask = String.valueOf(task.getClass());
        if (classTask.equals("class Task")) {
            Task newTask = (Task) task;
            id = newTask.getId();
            if (taskList.containsKey(id)) {
                taskList.put(id, newTask);
            } else if (classTask.equals("class Epic")) {
                Epic newEpic = (Epic) task;
                id = newEpic.getId();
                newEpic.setStatus(subtaskList);
                if (epicList.containsKey(id)) {
                    epicList.put(id, newEpic);
                }
            } else if (classTask.equals("class Subtask")) {
                Subtask newSubtask = (Subtask) task;
                id = newSubtask.getId();
                if (subtaskList.containsKey(id)) {
                    subtaskList.put(id, newSubtask);
                }
            }
        }
    }

    public Object getAllTasks() {
        return taskList.values();
    }

    public Object getAllEpic(){
        return epicList.values();
    }

    public Object getAllSubtasks(){
        return subtaskList.values();
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

