import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
class InMemoryTaskManagerTest {
    static InMemoryTaskManager taskManager;
    @BeforeAll
     static void beforeAll() {
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
    @Test
    void shouldReturnTaskById() {
        Task task = taskManager.getTaskById(0);
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
        Task task = taskManager.getTaskById(0);
        taskManager.createTask(task);
        int id = 0;
        for (Task t : taskManager.getAllTasks()) id = t.getId();
        Task newTask = taskManager.getTaskById(id);
        assertEquals(task.getTitle(), newTask.getTitle());
        assertEquals(task.getDescription(), newTask.getDescription());
        assertEquals(task.getStatus(), newTask.getStatus());
    }

}