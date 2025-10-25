import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

public class FileBackedTaskManagerTest {
    static FileBackedTaskManager fileBackedManager = new FileBackedTaskManager("storageFile.csv");

    @BeforeEach
    void beforeEach() {
        fileBackedManager.removeAll();
    }

    @Test
    void shouldAddTasksToFile(){
        fileBackedManager.createTask(new Task("read", "read a book", Status.NEW));
        Assertions.assertTrue( fileBackedManager.getStorageFile().length() > 0);
        int length = (int) fileBackedManager.getStorageFile().length();
        fileBackedManager.createEpic(new Epic("build", "build a house"));
        Assertions.assertTrue( fileBackedManager.getStorageFile().length() > length);
        length = (int) fileBackedManager.getStorageFile().length();
        fileBackedManager.createSubtask(new Subtask("buy", "buy tools", Status.IN_PROGRESS, 1));
        Assertions.assertTrue( fileBackedManager.getStorageFile().length() > length);
    }


    @Test
    void shouldReturnTasksFromString() {
        Task task1  = new Task("kick" ,"kick a ball", Status.NEW);
        fileBackedManager.createTask(task1);
        Epic epic = new Epic("make", "make a cake");
        fileBackedManager.createEpic(epic);
        Subtask subtask = new Subtask("buy", "buy eggs and flour", Status.IN_PROGRESS, epic.getId());
        fileBackedManager.createSubtask(subtask);
        Task task2 = new Task("travel", "travel to Spain", Status.DONE);
        fileBackedManager.createTask(task2);

        FileBackedTaskManager.loadFromFile(new File(fileBackedManager.getStorageFile().toString()));
        List<Task> taskList = fileBackedManager.getAllTasks();
        List<Epic> epicList = fileBackedManager.getAllEpic();
        List<Subtask> subtaskList = fileBackedManager.getAllSubtasks();
        Assertions.assertEquals(task1, taskList.get(0));
        Assertions.assertEquals(task2, taskList.get(1));
        Assertions.assertEquals(epic, epicList.getFirst());
        Assertions.assertEquals(subtask, subtaskList.getFirst());
    }
}
