import controllers.Manager;
import controllers.Managers;
import exceptions.SubtaskCreationException;
import exceptions.TaskCreationException;
import controllers.model.Epic;
import controllers.util.Status;
import controllers.model.Subtask;
import controllers.model.Task;

import java.time.Duration;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws TaskCreationException, SubtaskCreationException {
        Managers managers = new Managers();
        Manager managerMemory = managers.getFileBackedManager();
        managerMemory.createTask(new Task("save", "save to file", Status.NEW, LocalDateTime.of(2025, 11, 5, 10, 00), Duration.ofMinutes(45)));
        Epic epic1 = new Epic("nibble", "nibble ab apple");
        managerMemory.createEpic(epic1);
        managerMemory.createSubtask(new Subtask("cut", "cut an apple into pieces", Status.IN_PROGRESS, LocalDateTime.of(2025, 11, 5, 11, 00), Duration.ofMinutes(45), epic1.getId()));
        managerMemory.createTask(new Task("download", "download from file", Status.NEW, LocalDateTime.of(2025, 11, 5, 10, 00), Duration.ofMinutes(45)));

//        managers.getDefault().createTask(task);
        //   System.out.println(managerMemory.getAllTasks());
        //   System.out.println(managerMemory.getAllEpic());
        //   System.out.println(managerMemory.getAllSubtasks());
//        System.out.println(managerMemory.getTaskById(0));

//        File file = new File("storageFile.csv");
//       controllers.FileBackedTaskManager.loadFromFile(file);
//        controllers.FileBackedTaskManager.loadFromFile(file).createTask(new model.Task("save", "save to file", model.Status.NEW, LocalDateTime.of(2025, 11, 5, 10, 00), Duration.ofMinutes(45) ));
//        controllers.FileBackedTaskManager.loadFromFile(file).createTask(new model.Task("clean", "clean the table", model.Status.NEW));
//      controllers.FileBackedTaskManager.loadFromFile(file).createTask(new model.Task("download", "download from file", model.Status.NEW, LocalDateTime.of(2025, 11, 5, 11, 00), Duration.ofMinutes(45) ));
//      model.Epic epic4 = new model.Epic("nibble", "nibble ab apple");
//      controllers.FileBackedTaskManager.loadFromFile(file).createEpic(epic4);
//        controllers.FileBackedTaskManager.loadFromFile(file).createSubtask(new model.Subtask("cut", "cut an apple into pieces", model.Status.IN_PROGRESS,LocalDateTime.of(2025, 11, 5, 12, 00), Duration.ofMinutes(45),  epic4.getId()));
//        controllers.FileBackedTaskManager.loadFromFile(file).createSubtask(new model.Subtask("put", "put on the plate", model.Status.IN_PROGRESS,LocalDateTime.of(2025, 11, 5, 13, 00), Duration.ofMinutes(45),  epic4.getId()));
//       controllers.FileBackedTaskManager.loadFromFile(file).createTask(new model.Task("gnaw", "gnaw at the tree", model.Status.IN_PROGRESS));
//      // controllers.FileBackedTaskManager.loadFromFile(file).removeTaskById(0);
        //      model.Epic epic = controllers.FileBackedTaskManager.loadFromFile(file).getEpicById(3);
        //        epic.setDescription("DESCRIPTION");
        //       controllers.FileBackedTaskManager.loadFromFile(file).editEpic(epic);
        //       controllers.FileBackedTaskManager.loadFromFile(file).removeAll();
        //    System.out.println("created tasks:, %-20s");
        ///  System.out.println("All tasks: " + controllers.FileBackedTaskManager.loadFromFile(file).getAllTasks());
        //    System.out.println("All epics: " + controllers.FileBackedTaskManager.loadFromFile(file).getAllEpic());
        //    System.out.println("All subtasks: " + controllers.FileBackedTaskManager.loadFromFile(file).getAllSubtasksByEpic(epic4.getId()));


//        controllers.FileBackedTaskManager.loadFromFile(new File("storageFile.csv"));
//        controllers.FileBackedTaskManager.loadFromFile(file).createTask(new model.Task("feed", "feed the cat", model.Status.NEW ));
//        controllers.FileBackedTaskManager.loadFromFile(new File("storageFile.csv"));
//        controllers.Managers manager = new controllers.Managers();
//        controllers.FileBackedTaskManager.loadFromFile(new File("storageFile.csv"));
//        manager.getFileBackedManager().getAllTasks();

//        model.Epic epic = manager.getFileBackedManager().getEpicById(2);
//        System.out.println("get epic: " + epic);
//        epic.setDescription("new description");
//        manager.getFileBackedManager().editEpic(epic);
//        System.out.println("edit epic: " + epic);
//        System.out.println();
//        System.out.println(controllers.FileBackedTaskManager.loadFromFile(file).getEpicById(2));
//        System.out.println("FromBackedManager: " + controllers.FileBackedTaskManager.loadFromFile(new File("storageFile.csv")).getEpicById(2));
//        controllers.FileBackedTaskManager.loadFromFile(new File("storageFile.csv"));
        //  backedManager.getAllEpic();
        //   backedManager.getAllTasks();
        //  backedManager.getAllSubtasks();

        //backedManager.save();

        //  controllers.Managers man = new controllers.Managers();
        // controllers.Manager taskManager = man.getDefault();
        //   controllers.InMemoryTaskManager taskManager = new controllers.InMemoryTaskManager();
//        taskManager.createTask(new model.Task("do the dishes", "after the party", model.Status.NEW, LocalDateTime.of(2025, 11, 5, 10, 00), Duration.ofMinutes(45)));
//        taskManager.createTask(new model.Task("do hwk", "math, biology", model.Status.IN_PROGRESS, LocalDateTime.of(2025, 11, 5, 10, 50), Duration.ofMinutes(15)));
//        taskManager.createTask(new model.Task("wallpaper", "in the hallway", model.Status.DONE, LocalDateTime.of(2025, 11, 5, 10, 25), Duration.ofMinutes(20)));
//        taskManager.createTask(new model.Task("close", "close the door", model.Status.NEW, LocalDateTime.of(2025, 11, 5, 9, 50), Duration.ofMinutes(45)));
//        taskManager.createTask(new model.Task("pam", "pampam", model.Status.NEW, LocalDateTime.of(2025, 11, 5, 14, 50), Duration.ofMinutes(45)));
//        taskManager.createTask(new model.Task("close", "close the door", model.Status.NEW, LocalDateTime.of(2025, 11, 5, 6, 00), Duration.ofMinutes(500)));
//        taskManager.createTask(new model.Task("close", "close the door", model.Status.NEW, LocalDateTime.of(2025, 11, 6, 14, 30), Duration.ofMinutes(45)));
        //  model.Epic epic1 = new model.Epic("sweep", "sweep the floor");
        //  taskManager.createEpic(epic1);
        //   taskManager.createSubtask(new model.Subtask("broom", "buy the broom", model.Status.DONE, LocalDateTime.of(2025, 11, 5, 11, 46), Duration.ofMinutes(7), epic1.getId()));
        //    taskManager.createSubtask(new model.Subtask("get the dustpan", "", model.Status.DONE, LocalDateTime.of(2025, 11, 5, 11, 00), Duration.ofMinutes(40), epic1.getId()));
        //  model.Epic epic2 = new model.Epic("cook dinner", "");

        //   model.Subtask subtask1 = new model.Subtask("broom", "buy the broom", model.Status.NEW, LocalDateTime.of(2025, 11, 5, 10, 45), Duration.ofMinutes(7), 3);
        //   model.Subtask subtask2 = new model.Subtask("get the dustpan", "", model.Status.NEW, LocalDateTime.of(2025, 11, 5, 11, 00), Duration.ofMinutes(40), 3);
        //   model.Subtask subtask3 = new model.Subtask("buy veggies", "tomatoes, mushrooms", model.Status.DONE, LocalDateTime.of(2025, 11, 5, 11, 30), Duration.ofMinutes(20), 4);
        //  model.Subtask subtask4 = new model.Subtask("cut ingredients", "dice, slice", model.Status.DONE, LocalDateTime.of(2025, 11, 5, 12, 00), Duration.ofMinutes(0), 4);

        // taskManager.createEpic(epic1);
        //  taskManager.createEpic(epic2);



        //  taskManager.createSubtask(subtask1);
        //   taskManager.createSubtask(subtask2);
        //  taskManager.createSubtask(subtask3);
        // taskManager.createSubtask(subtask4);

        //System.out.println(taskManager.getPrioritizedTasks());
        //  System.out.println(taskManager.getAllEpic());


//        controllers.InMemoryHistoryManager history = new controllers.InMemoryHistoryManager();
//
//        taskManager.createEpic(new model.Epic("draw", "draw a painting on the wall"));
//        taskManager.createSubtask(new model.Subtask("buy", "buy brushes and paints",model.Status.IN_PROGRESS, 9));
//        taskManager.createSubtask(new model.Subtask("choose", "choose a pic to paint",model.Status.IN_PROGRESS, 9));
//        taskManager.getTaskById(2);
//        taskManager.getTaskById(0);
//        taskManager.getTaskById(1);
//        taskManager.getEpicById(3);
//        taskManager.getEpicById(4);
//        taskManager.getSubtaskById(5);
//        taskManager.getSubtaskById(6);
//        taskManager.getSubtaskById(7);
//        taskManager.getSubtaskById(8);
//        taskManager.getEpicById(9);
//        System.out.println( "History Test 1: " + taskManager.showHistory());
//        taskManager.getSubtaskById(10);
//        System.out.println( "History Test 1.5: " + taskManager.showHistory());
//        taskManager.getTaskById(2);
//        System.out.println( "History Test 2: " + taskManager.showHistory());
////        System.out.println( "History Test 3 : " + taskManager.getSubtaskById(11));
//
////       System.out.println( "Test 2: " + taskManager.showHistory());
//        taskManager.getEpicById(3);
//        taskManager.getTaskById(2);
//        System.out.println( "History Test 2.5: " + taskManager.showHistory());
//
//        taskManager.getEpicById(3);
//        taskManager.getTaskById(1);
//        taskManager.getTaskById(1);
//        System.out.println("History Test 4: " + taskManager.showHistory());

//        model.Task task = new model.Task("finish", "complete the task finally");
//        task.setId(0);
//        taskManager.editTask(task);
//        System.out.println( "Test: " + taskManager.getTaskById(0));

//        ArrayList<model.Subtask> subtasks2 = new ArrayList<>();
//        taskManager.createSubtask(subtask3);
//        taskManager.createSubtask(subtask4);
//
//        taskManager.createEpic(epic2);
//        System.out.println("Get by id " + taskManager.getTaskById(2));
//      //  System.out.println( "Print 2: " + taskManager.getAllEpic());
//       // System.out.println("Print 3 " + taskManager.getAllSubtasks());
//        System.out.println(taskManager.getEpicById(3).toString());
//      //  taskManager.removeTaskById(4);
//       // System.out.println("Print 33 " + taskManager.getAllSubtasks());
//        //taskManager.removeSubtaskById(5);
//        System.out.println(taskManager.getSubtaskById(5).toString());
//        System.out.println("Print 333 " + taskManager.getAllSubtasks());
//        System.out.println("History: " + taskManager.showHistory());



        // taskManager.removeAll();
        //  System.out.println("Print 4 " + taskManager.getAllSubtasks());
//       System.out.println(subtask3.getEpicId());
//     //   System.out.println(subtask3.getEpicId());
//       System.out.println("model.Epic 1: " + epic2.subtaskIds);
//        System.out.println("model.Epic 1: " + epic2.subtaskIds);
//        System.out.println("model.Epic 2: " + epic2.subtaskIds);
//        System.out.println("All tasks: " + controllers.FileBackedTaskManager.loadFromFile(file).getAllTasks());
//        System.out.println("All epics: " + controllers.FileBackedTaskManager.loadFromFile(file).getAllEpic());
//        System.out.println("All subtasks: " + controllers.FileBackedTaskManager.loadFromFile(file).getAllSubtasksByEpic(3));

        // Test getPrioritized()
    }
}
