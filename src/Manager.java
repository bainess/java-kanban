import java.util.ArrayList;
import java.util.HashMap;


public class Manager {
    HashMap<Integer, Task> taskList = new HashMap<>();
    HashMap<Integer, Epic> epicList = new HashMap<>();
    HashMap<Integer, Subtask> subtaskList = new HashMap<>();

    public void addTask(Object task) {
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
                break;
            case "class Subtask":
                Subtask newSubtask = (Subtask) task;
                id = newSubtask.getId();
                subtaskList.put(id, newSubtask);
                break;
        }

    }

    public Object getTaskById(int id) {
        if (taskList.containsKey(id)) {
            return taskList.get(id);
        } else if (epicList.containsKey(id)) {
            return taskList.get(id);
        } else if (subtaskList.containsKey(id)){
            return subtaskList.get(id);
        }
        return "no task by id" + id;
    }

    public void removeTaskById(int id){
       taskList.remove(id);
    }
    public void removeAll() {
        taskList.clear();
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
        return epicList.values();
    }

}

