import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
class InMemoryTaskManagerTest {
    static InMemoryTaskManager taskManager;
    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
        taskManager.createTask(new Task("do the dishes", "after the party", Status.NEW, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30)));
        taskManager.createTask(new Task("do hwk", "math, biology", Status.IN_PROGRESS, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30)));
        taskManager.createTask(new Task("wallpaper", "in the hallway", Status.DONE, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30)));
        taskManager.createEpic(new Epic("sweep", "sweep the floor", LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30)));
        taskManager.createEpic(new Epic("cook dinner", "", LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30)));

        taskManager.createSubtask(new Subtask("broom", "buy the broom", Status.NEW, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30), 3));
        taskManager.createSubtask(new Subtask("get the dustpan", "", Status.NEW, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30), 3));
        taskManager.createSubtask(new Subtask("buy veggies", "tomatoes, mushrooms", Status.DONE, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30),  4));
        taskManager.createSubtask(new Subtask("cut ingredients", "dice, slice", Status.DONE, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30), 4));
    }
    @Test
    void shouldBeEqualTasksIfEqualId() {
        Task task = taskManager.getTaskById(0);
        Task task2 = taskManager.getTaskById(0);
        assertEquals(task, task2);
    }
    @Test
    void shouldBeEqualEpicsIfEqualId() {
        Epic epic = taskManager.getEpicById(3);
        Epic epic2 = taskManager.getEpicById(3);
        assertEquals(epic, epic2);
    }

    @Test
    void shouldBeEqualSubtasksIfEqualId() {
        Subtask subtask = taskManager.getSubtaskById(5);
        Subtask subtask2 = taskManager.getSubtaskById(5);
        assertEquals(subtask, subtask2);
    }

    @Test
    void shouldAddAndReturnNewTask() {
        int size1 = taskManager.getAllTasks().size();
        taskManager.createTask(new Task("split", "split the task", Status.NEW, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30)));
        int size2 = taskManager.getAllTasks().size();
        assertNotEquals(size1,size2);
    }
    @Test
    void shouldAddAndReturnNewEpic() {
        int size1 = taskManager.getAllEpic().size();
        taskManager.createEpic(new Epic("create", "create Task Managet", LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30)));
        int size2 = taskManager.getAllEpic().size();
        assertNotEquals(size1,size2);
    }
    @Test
    void shouldReturnTaskById() {
        Task task = taskManager.getTaskById(0);
        taskManager.removeAllTasks();
        assertEquals(0, task.getId());
    }

    @Test
    void shouldReturnEpicById() {
        Epic task = taskManager.getEpicById(4);
        assertEquals(4, task.getId());
    }
    @Test
    void shouldReturnSubtaskById() {
        Subtask task = taskManager.getSubtaskById(7);
        assertEquals(7, task.getId());
    }
    @Test
    void shouldReturnNewIdWhenIdIsGiven() {
        Task task = taskManager.getTaskById(0);
        taskManager.createTask(task);
        int id = 0;
        for (Task t : taskManager.getAllTasks()){
            id = t.getId();
        }
        assertNotEquals(0,  id );
    }
    @Test
    void shouldReturnEqualsIfTasksMatch() {
        Task task1 = taskManager.getTaskById(0);
        Task task2 = taskManager.getTaskById(0);
        assertEquals(task1.getId(), task2.getId());
    }
    @Test
    void shouldRemoveIdOfRemovedSubtaskFromEpic() {
        taskManager.removeSubtaskById(5);
        System.out.println(taskManager.getEpicById(3).getSubtaskIds().contains(5));
        assertFalse(taskManager.getEpicById(3).getSubtaskIds().contains(5));
    }
}