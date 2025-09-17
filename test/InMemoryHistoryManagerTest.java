import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class InMemoryHistoryManagerTest {
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
    void shouldPreservePreviousTaskVersionWhenNewTaskAdded() {
        for (int i = 0; i < 9; i++) {
            if (taskManager.getTaskById(i) != null) {
                taskManager.getTaskById(i);
            } else if (taskManager.getTaskById(i) == null && taskManager.getEpicById(i) != null) {
                taskManager.getEpicById(i);
            } else {
                taskManager.getSubtaskById(i);
            }
        }
        ArrayList<Task> previousHitoryVersion = new ArrayList<>();
        for (Task task : taskManager.showHistory()) {
            previousHitoryVersion.add(task);
        }
        taskManager.createEpic(new Epic("draw", "draw a painting on the wall"));
        for (int i = 0; i < taskManager.showHistory().size() - 2; i++) {
            Task task = taskManager.showHistory().get(i);
            assertEquals(previousHitoryVersion.get(i), task);
        }

    }

    @Test
    void shouldRemoveFirstItemWhenHistoryIsOverloaded() {
        taskManager.createEpic(new Epic("draw", "draw a painting on the wall"));
        taskManager.createSubtask(new Subtask("buy", "buy brushes and paints", Status.IN_PROGRESS, 9));
        for (int i = 0; i < 11; i++) {
            if (taskManager.getTaskById(i) != null) {
                taskManager.getTaskById(i);
            } else if (taskManager.getTaskById(i) == null && taskManager.getEpicById(i) != null) {
                taskManager.getEpicById(i);
            } else {
                taskManager.getSubtaskById(i);
            }
        }
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