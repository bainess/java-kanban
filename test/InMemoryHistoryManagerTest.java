import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class InMemoryHistoryManagerTest {
    static InMemoryTaskManager taskManager;

    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
        taskManager.createTask(new Task("do the dishes", "after the party", Status.NEW, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(5)));
        taskManager.createTask(new Task("do hwk", "math, biology", Status.IN_PROGRESS, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(10)));
        taskManager.createTask(new Task("wallpaper", "in the hallway", Status.DONE, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(15)));
        taskManager.createEpic(new Epic("sweep", "sweep the floor",LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(20)));
        taskManager.createEpic(new Epic("cook dinner", "", LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(35)));

        taskManager.createSubtask(new Subtask("broom", "buy the broom", Status.NEW, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(40), 3));
        taskManager.createSubtask(new Subtask("get the dustpan", "", Status.NEW, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(45), 3));
        taskManager.createSubtask(new Subtask("buy veggies", "tomatoes, mushrooms", Status.DONE, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(8), 4));
        taskManager.createSubtask(new Subtask("cut ingredients", "dice, slice", Status.DONE, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(9), 4));
        taskManager.createTask(new Task("tests", "complete tests for tm", Status.IN_PROGRESS, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(18)));
    }

    @Test
   void shouldAddNewTask() {
        int oldhistory =  taskManager.showHistory().size();
        taskManager.getTaskById(0);
        int newHistory = taskManager.showHistory().size();
        assertNotEquals(oldhistory, newHistory);

    }
    @Test
    void shouldRemoveRepeatedTask() {
        taskManager.getTaskById(0);
        taskManager.getTaskById(1);
        taskManager.getTaskById(0);
        assertEquals(2, taskManager.showHistory().size());
    }
    @Test
    void shouldRemoveTaskTaskFromBeginningOfList() {
        taskManager.getTaskById(0);
        taskManager.getTaskById(1);
        taskManager.getTaskById(0);
        System.out.println(taskManager.showHistory().getFirst().getId());
        assertEquals(1, taskManager.showHistory().getFirst().getId());
    }
    @Test
    void shouldRemoveTaskFromMiddleToEndWhenRepeated() {
        taskManager.getTaskById(0);
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getTaskById(1);
        assertNotEquals(1, taskManager.showHistory().get(1).getId());
        assertEquals(1, taskManager.showHistory().getLast().getId());
    }
    @Test
    void shouldRemoveRemovedTaskFromHistory() {
        taskManager.getTaskById(0);
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.removeTaskById(0);
        assertEquals(2, taskManager.showHistory().size());
    }
}