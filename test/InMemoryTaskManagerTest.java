import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
class InMemoryTaskManagerTest {
    static InMemoryTaskManager taskManager;
    @BeforeAll
    static void beforeAll() {
        taskManager = new InMemoryTaskManager();
        Task task1 = new Task("do the dishes", "after the party", Status.NEW);
        Task task2 = new Task("do hwk", "math, biology", Status.IN_PROGRESS);
        Task task3 = new Task("wallpaper", "in the hallway", Status.DONE);
        Epic epic1 = new Epic("sweep", "sweep the floor");
        Epic epic2 = new Epic("cook dinner", "");

        Subtask subtask1 = new Subtask("broom", "buy the broom", Status.NEW, 3);
        Subtask subtask2 = new Subtask("get the dustpan", "", Status.NEW , 3);
        Subtask subtask3 = new Subtask("buy veggies", "tomatoes, mushrooms", Status.DONE, 4);
        Subtask subtask4 = new Subtask("cut ingredients", "dice, slice", Status.DONE, 4);

        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);

        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);

        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
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
        taskManager.createTask(new Task("split", "split the task", Status.NEW));
        int size2 = taskManager.getAllTasks().size();
        assertNotEquals(size1,size2);
    }
    @Test
    void shouldAddAndReturnNewEpic() {
        int size1 = taskManager.getAllEpic().size();
        taskManager.createEpic(new Epic("create", "create Task Managet"));
        int size2 = taskManager.getAllEpic().size();
        assertNotEquals(size1,size2);
    }
}