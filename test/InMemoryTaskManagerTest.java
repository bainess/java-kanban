import org.junit.jupiter.api.Assertions;
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
        Epic epic1 = new Epic("sweep", "sweep the floor");
        taskManager.createEpic(epic1);
        Epic epic2 = new Epic("cook dinner", "");
        taskManager.createEpic(epic2);

        taskManager.createSubtask(new Subtask("broom", "buy the broom", Status.NEW, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30), epic1.getId()));
        taskManager.createSubtask(new Subtask("get the dustpan", "", Status.NEW, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30), epic1.getId()));
        taskManager.createSubtask(new Subtask("buy veggies", "tomatoes, mushrooms", Status.DONE, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30),  epic2.getId()));
        taskManager.createSubtask(new Subtask("cut ingredients", "dice, slice", Status.DONE, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30), epic2.getId()));
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
    void shouldReturnStatusNew() {
        taskManager.removeAll();
        Epic epic = new Epic("do", "do hwk");
        taskManager.createEpic(epic);
        Subtask subtask1 = new Subtask("read biology", "write biology", Status.NEW, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30), epic.getId());
        Subtask subtask2 = new Subtask("read math", "write math", Status.NEW, LocalDateTime.of(2022, 11, 4, 15, 45), Duration.ofMinutes(30), epic.getId());
        Subtask subtask3 = new Subtask("read science", "write science", Status.NEW, LocalDateTime.of(2022, 11, 4, 15, 45), Duration.ofMinutes(30), epic.getId());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);
        Assertions.assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    void shoulsReturnStatusInProgress() {
        taskManager.removeAll();
        Epic epic = new Epic("do", "do hwk");
        taskManager.createEpic(epic);
        Subtask subtask1 = new Subtask("read biology", "write biology", Status.NEW, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30), epic.getId());
        Subtask subtask2 = new Subtask("read math", "write math", Status.IN_PROGRESS, LocalDateTime.of(2022, 11, 4, 15, 45), Duration.ofMinutes(30), epic.getId());
        Subtask subtask3 = new Subtask("read science", "write science", Status.NEW, LocalDateTime.of(2022, 11, 4, 15, 45), Duration.ofMinutes(30), epic.getId());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);
        Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    void shoulsReturnStatusDone() {
        taskManager.removeAll();
        Epic epic = new Epic("do", "do hwk");
        taskManager.createEpic(epic);
        Subtask subtask1 = new Subtask("read biology", "write biology", Status.DONE, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30), epic.getId());
        Subtask subtask2 = new Subtask("read math", "write math", Status.DONE, LocalDateTime.of(2022, 11, 4, 15, 45), Duration.ofMinutes(30), epic.getId());
        Subtask subtask3 = new Subtask("read science", "write science", Status.DONE, LocalDateTime.of(2022, 11, 4, 15, 45), Duration.ofMinutes(30), epic.getId());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);
        Assertions.assertEquals(Status.DONE, epic.getStatus());
    }

        @Test
        void shoulsReturnStatusINProgressWhenNEWandDone() {
            taskManager.removeAll();
            Epic epic = new Epic("do", "do hwk");
            taskManager.createEpic(epic);
            Subtask subtask1 = new Subtask("read biology", "write biology", Status.NEW, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30), epic.getId());
            Subtask subtask2 = new Subtask("read math", "write math", Status.DONE, LocalDateTime.of(2022, 11, 4, 15, 45), Duration.ofMinutes(30), epic.getId());
            Subtask subtask3 = new Subtask("read science", "write science", Status.DONE, LocalDateTime.of(2022, 11, 4, 15, 45), Duration.ofMinutes(30), epic.getId());
            taskManager.createSubtask(subtask1);
            taskManager.createSubtask(subtask2);
            taskManager.createSubtask(subtask3);
            Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

        @Test
    void shouldReturnTasksPrioritised() {
        taskManager.removeAll();
        taskManager.createTask(new Task("do the dishes", "after the party", Status.NEW, LocalDateTime.of(2022, 11, 4, 20, 45), Duration.ofMinutes(30)));
        taskManager.createTask(new Task("do hwk", "math, biology", Status.IN_PROGRESS, LocalDateTime.of(2022, 11, 4, 14, 45), Duration.ofMinutes(30)));
        taskManager.createTask(new Task("wallpaper", "in the hallway", Status.DONE, LocalDateTime.of(2022, 11, 4, 9, 45), Duration.ofMinutes(30)));
        Assertions.assertEquals(taskManager.getTaskById(2), taskManager.getPrioritizedTasks().getFirst());
    }
}