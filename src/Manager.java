import java.util.ArrayList;

public interface Manager {
    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubtask(Subtask subtask);

    void removeTaskById(int id);

    void removeEpicById(int id);

    void removeSubtaskById(int id);

    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    void removeAll();

    void editTask(Task task);

    void editEpic(Epic epic);

    void editSubtask(Subtask subtask);

    ArrayList<Task> getAllTasks();

    ArrayList<Epic> getAllEpic();

    ArrayList<Subtask> getAllSubtasks();

    ArrayList<Subtask> getAllSubtasksByEpic(int id);

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubtasks();
}
