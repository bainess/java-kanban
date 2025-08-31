import java.util.HashMap;


public class Manager {
    HashMap<Integer, Object> taskList = new HashMap<>();

    public void addTask(Object task) {
        String taskClass = String.valueOf(task.getClass());
        Integer id;
        if (taskClass.equals("class Task")) {
            Task newTask = (Task) task;
            id = newTask.getId();
        } else {
            Epic newTask = (Epic) task;
            id = newTask.getId();
        }
        taskList.put(id, task);
    }

    public Object getTaskById(int id) {
        if (taskList.containsKey(id)) {
            return taskList.get(id);
        }
        return "no task by id" + id;
    }

    public void removeTaskById(int id){
       taskList.remove(id);
    }
    public void removeAll() {
        taskList.clear();
    }

    public Object getAllTasks() {
        return taskList.values();
    }
}

