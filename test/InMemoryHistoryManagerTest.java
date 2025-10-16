import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class InMemoryHistoryManagerTest {
    static InMemoryTaskManager taskManager;

    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
        taskManager.createTask(new Task("do the dishes", "after the party", Status.NEW));
        taskManager.createTask(new Task("do hwk", "math, biology", Status.IN_PROGRESS));
        taskManager.createTask(new Task("wallpaper", "in the hallway", Status.DONE));
        taskManager.createEpic(new Epic("sweep", "sweep the floor"));
        taskManager.createEpic(new Epic("cook dinner", ""));

        taskManager.createSubtask(new Subtask("broom", "buy the broom", Status.NEW, 3));
        taskManager.createSubtask(new Subtask("get the dustpan", "", Status.NEW, 3));
        taskManager.createSubtask(new Subtask("buy veggies", "tomatoes, mushrooms", Status.DONE, 4));
        taskManager.createSubtask(new Subtask("cut ingredients", "dice, slice", Status.DONE, 4));
        taskManager.createTask(new Task("tests", "complete tests for tm", Status.IN_PROGRESS));
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