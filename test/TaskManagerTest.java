import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

abstract class TaskManagerTest<T extends Manager> {
    protected T taskManager;

    @BeforeEach
    void BeforeEach() {
        taskManager.removeAll();
    }

    @Test
    void shouldReturnTaskNoStartTime() {
        Task task = new Task("kick", "kick a ball", Status.NEW);
        taskManager.createTask(task);
        Assertions.assertEquals(1, taskManager.getAllTasks().size());
    }

    @Test
    void shouldReturnTask() {
        Task task = new Task("kick", "kick a ball", Status.NEW, LocalDateTime.of(2025, 11, 12, 11, 00), Duration.ofMinutes(10));
        taskManager.createTask(task);
        Assertions.assertEquals(1, taskManager.getAllTasks().size());
    }

    @Test
    void shouldAddEpic() {
        Epic epic1 = new Epic("brush", "brush teeth");
        taskManager.createEpic(epic1);
        Assertions.assertEquals(1, taskManager.getAllEpic().size());
    }

    @Test
    void shouldAddSubtaskNoStartTime() {
        Epic epic = new Epic("set", "set the table");
        Subtask subtask = new Subtask("make", "make tea", Status.NEW, epic.getId());
        Assertions.assertEquals(1, taskManager.getAllSubtasks().size());
    }

    @Test
    void shouldAddSubtask() {
        Epic epic = new Epic("set", "set the table");
        Subtask subtask = new Subtask("make", "make tea", Status.NEW, LocalDateTime.of(2025, 11, 12, 11, 00), Duration.ofMinutes(10), epic.getId());
        Assertions.assertEquals(1, taskManager.getAllSubtasks().size());
    }

    @Test
    void shouldEditTask() {
        Task task = new Task("kick", "kick a ball", Status.NEW, LocalDateTime.of(2025, 11, 12, 11, 00), Duration.ofMinutes(10));
        taskManager.createTask(task);
        int id = taskManager.getTaskById(task.getId()).getId();
        task.setDescription("throw a bet");
        task.setStatus(Status.IN_PROGRESS);
        task.setTitle("throw");
        taskManager.editTask(task);
        Assertions.assertEquals("throw a bet", taskManager.getTaskById(id).getDescription());
        Assertions.assertEquals("throw", taskManager.getTaskById(id).getTitle());
        Assertions.assertEquals(Status.IN_PROGRESS, taskManager.getTaskById(id).getStatus());
        Assertions.assertEquals(id, taskManager.getTaskById(id).getId());
    }

    @Test
    void shouldEditEpic() {
        Epic epic = new Epic("read", "read a book");
        taskManager.createEpic(epic);
        int id = taskManager.getEpicById(epic.getId()).getId();
        epic.setTitle("write");
        epic.setDescription("write a poem");
        Assertions.assertEquals("write", taskManager.getEpicById(id).getTitle());
        Assertions.assertEquals("write a poem", taskManager.getEpicById(id).getDescription());
    }

    @Test
    void shouldEditSubtask() {
        Epic epic = new Epic("read", "read a book");
        Subtask subtask = new Subtask("read ch1", "Read about dragons" , Status.NEW, LocalDateTime.of(2025, 11, 12, 11, 00), Duration.ofMinutes(10), epic.getId());
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask);
        int id = taskManager.getSubtaskById(subtask.getId()).getId();
        subtask.setTitle("read ch2");
        subtask.setDescription("read about snakes");
        subtask.setStatus(Status.IN_PROGRESS);
        Assertions.assertEquals("read ch2", taskManager.getSubtaskById(id).getTitle());
        Assertions.assertEquals("read about snakes", taskManager.getSubtaskById(id).getDescription());
        Assertions.assertEquals(Status.IN_PROGRESS, taskManager.getSubtaskById(id).getStatus());
    }

        @Test
    void shouldRemoveTasks() {
        Task task = new Task("kick", "kick a ball", Status.NEW);
        taskManager.createTask(task);
        Task task2 = new Task("kick", "kick a ball", Status.NEW, LocalDateTime.of(2025, 11, 12, 11, 00), Duration.ofMinutes(10));
        taskManager.createTask(task);
        Assertions.assertEquals(2, taskManager.getAllTasks().size());
        taskManager.removeAllTasks();
        Assertions.assertEquals(0, taskManager.getAllTasks().size());
    }
    @Test
    void shouldRemoveEpics() {
        Epic epic1 = new Epic("brush", "brush teeth");
        taskManager.createEpic(epic1);
        Epic epic = new Epic("read", "read a book");
        taskManager.createEpic(epic);
        Assertions.assertEquals(2, taskManager.getAllEpic().size());
        taskManager.removeAllTasks();
        Assertions.assertEquals(0, taskManager.getAllEpic().size());
    }

    @Test
    void shouldRemoveSubtasks() {
        Epic epic = new Epic("read", "read a book");
        Subtask subtask = new Subtask("read ch1", "Read about dragons" , Status.NEW, LocalDateTime.of(2025, 11, 12, 11, 00), Duration.ofMinutes(10), epic.getId());
        taskManager.createEpic(epic);
        Subtask subtask2 = new Subtask("read ch2", "Read about dragonflies" , Status.NEW, LocalDateTime.of(2025, 11, 12, 11, 00), Duration.ofMinutes(10), epic.getId());
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask);
        taskManager.createSubtask(subtask2);
        Assertions.assertEquals(2, taskManager.getAllTasks().size());
        taskManager.removeAllTasks();
        Assertions.assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    void shouldNotAddTaskIfTimeOverlapsAtTheStart() {
        Task task = new Task("kick", "kick a ball", Status.NEW, LocalDateTime.of(2025, 11, 12, 11, 00), Duration.ofMinutes(10));
        taskManager.createTask(task);
        Task task2 = new Task("throw", "throw a ball", Status.NEW, LocalDateTime.of(2025, 11, 12, 10, 55), Duration.ofMinutes(10));
        taskManager.createTask(task);
        Assertions.assertEquals(1, taskManager.getAllTasks().size());
    }

    @Test
    void shouldNotAddTaskIfTimeOverlapsAtTheEnd() {
        Task task = new Task("kick", "kick a ball", Status.NEW, LocalDateTime.of(2025, 11, 12, 11, 0), Duration.ofMinutes(10));
        taskManager.createTask(task);
        Task task2 = new Task("kick", "kick a ball", Status.NEW, LocalDateTime.of(2025, 11, 12, 11, 5), Duration.ofMinutes(10));
        taskManager.createTask(task);
        Assertions.assertEquals(1, taskManager.getAllTasks().size());
    }

    @Test
    void shouldNotAddTaskIfTimeOverlapsAtInTheMIddle() {
        Task task = new Task("kick", "kick a ball", Status.NEW, LocalDateTime.of(2025, 11, 12, 11, 00), Duration.ofMinutes(30));
        taskManager.createTask(task);
        Task task2 = new Task("kick", "kick a ball", Status.NEW, LocalDateTime.of(2025, 11, 12, 11, 10), Duration.ofMinutes(10));
        taskManager.createTask(task);
        Assertions.assertEquals(1, taskManager.getAllTasks().size());
    }

    @Test
    void shouldNotAddTaskIfTimeOverlaps() {
        Task task = new Task("kick", "kick a ball", Status.NEW, LocalDateTime.of(2025, 11, 12, 11, 0), Duration.ofMinutes(10));
        taskManager.createTask(task);
        Task task2 = new Task("kick", "kick a ball", Status.NEW, LocalDateTime.of(2025, 11, 12, 10, 50), Duration.ofMinutes(50));
        taskManager.createTask(task);

    }
//    @Test
//    void should() {
//
//    }

}