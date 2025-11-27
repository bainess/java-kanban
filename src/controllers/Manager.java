package controllers;

import controllers.model.Epic;
import controllers.model.Subtask;
import controllers.model.Task;
import exceptions.SubtaskCreationException;
import exceptions.TaskCreationException;

import java.util.List;
import java.util.Set;

public interface Manager {
    void createTask(Task task) throws TaskCreationException;

    void createEpic(Epic epic);

    void createSubtask(Subtask subtask) throws TaskCreationException, SubtaskCreationException, SubtaskCreationException;

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

    List<Task> getAllTasks();

    List<Epic> getAllEpic();

    List<Subtask> getAllSubtasks();

    List<Subtask> getAllSubtasksByEpic(int id);

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubtasks();

    List<Task> showHistory();

    Set<Task> getPrioritizedTasks();


}
