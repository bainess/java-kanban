import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class InMemoryHistoryManagerTest {
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
        taskManager.createSubtask(subtask3);
        taskManager.createSubtask(subtask4);
    }

    @Test
    void shouldPreservePreviousTaskVersionWhenNewTaskAdded() {
        taskManager.getTaskById(0);
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getEpicById(3);
        taskManager.getEpicById(4);
        taskManager.getSubtaskById(5);
        taskManager.getSubtaskById(6);
        taskManager.getSubtaskById(7);
        taskManager.getSubtaskById(8);
        ArrayList<Task> previousHitoryVersion = new ArrayList<>();
        for (Task task : taskManager.historyManager.getHistory()){
            previousHitoryVersion.add(task);
        }
        taskManager.createEpic(new Epic("draw", "draw a painting on the wall"));
        for (int i = 0; i < taskManager.showHistory().size() - 2; i++ ){
            Task task = taskManager.showHistory().get(i);
            assertEquals(previousHitoryVersion.get(i), task);
        }

    }
    @Test
    void shouldRemoveFirstItemWhenHistoryIsOverloaded() {
        taskManager.getTaskById(0);
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getEpicById(3);
        taskManager.getEpicById(4);
        taskManager.getSubtaskById(5);
        taskManager.getSubtaskById(6);
        taskManager.getSubtaskById(7);
        taskManager.getSubtaskById(8);
        taskManager.createEpic(new Epic("draw", "draw a painting on the wall"));
        taskManager.createSubtask(new Subtask("buy", "buy brushes and paints",Status.IN_PROGRESS, 9));
        taskManager.getEpicById(9);
        taskManager.getSubtaskById(10);
        ArrayList<Task> previousHistoryVersion = new ArrayList<>();
        for (Task task : taskManager.showHistory()){
            previousHistoryVersion.add(task);
        }
        taskManager.createSubtask(new Subtask("choose", "choose a pic",Status.IN_PROGRESS, 9));
        taskManager.getSubtaskById(11);
        for (int i = 0; i < taskManager.showHistory().size() - 2; i++ ){
            Task task1 = taskManager.showHistory().get(i);
            Task task0 = previousHistoryVersion.get(i + 1);
            assertEquals(task0, task1);
        }
    }
}