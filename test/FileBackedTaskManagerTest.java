import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class FileBackedTaskManagerTest {
    static FileBackedTaskManager fileBackedManager = new FileBackedTaskManager("storageFile.csv");
    @BeforeEach
    void beforeEach() {
        fileBackedManager.removeAll();
    }

    @Test
    void shouldAddTasksToFile(){

        fileBackedManager.createTask(new Task("read", "read a book", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30)));
        Assertions.assertTrue( fileBackedManager.getStorageFile().length() > 0);
        int length = (int) fileBackedManager.getStorageFile().length();
        fileBackedManager.createEpic(new Epic("build", "build a house"));
        Assertions.assertTrue( fileBackedManager.getStorageFile().length() > length);
        length = (int) fileBackedManager.getStorageFile().length();
        fileBackedManager.createSubtask(new Subtask("buy", "buy tools", Status.IN_PROGRESS,
                LocalDateTime.of(2022, 11, 04, 14, 46), Duration.ofMinutes(45), 1));
        Assertions.assertTrue( fileBackedManager.getStorageFile().length() > length);
    }


    @Test
    void shouldReturnTasksFromString() {
        Task task1  = new Task("kick" ,"kick a ball", Status.NEW,
                LocalDateTime.of(2022, 11, 4, 14, 47), Duration.ofMinutes(15));
        fileBackedManager.createTask(task1);
        Epic epic = new Epic("make", "make a cake");
        fileBackedManager.createEpic(epic);
        Subtask subtask = new Subtask("buy", "buy eggs and flour", Status.IN_PROGRESS,
                LocalDateTime.of(2022, 11, 4, 14, 49), Duration.ofMinutes(30), epic.getId());
        fileBackedManager.createSubtask(subtask);
        Task task2 = new Task("travel", "travel to Spain", Status.DONE, LocalDateTime.of(2022, 11, 4, 14, 50), Duration.ofMinutes(30));
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
