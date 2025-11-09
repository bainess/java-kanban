import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        Managers managers = new Managers();
    //    Manager managerMemory = managers.getFileBackedManager();
      //  managerMemory.createTask(new Task("save", "save to file", Status.NEW, LocalDateTime.of(2025, 11, 5, 10, 00), Duration.ofMinutes(45) ));
     //   Epic epic1 = new Epic("nibble", "nibble ab apple");
   //     managerMemory.createEpic(epic1);
//        managerMemory.createSubtask(new Subtask("cut", "cut an apple into pieces", Status.IN_PROGRESS, LocalDateTime.of(2025, 11, 5, 11, 00), Duration.ofMinutes(45), epic1.getId() ));
     //   managerMemory.createTask(new Task("download", "download from file", Status.NEW, LocalDateTime.of(2025, 11, 5, 10, 00), Duration.ofMinutes(45) ));

//        managers.getDefault().createTask(task);
     //   System.out.println(managerMemory.getAllTasks());
     //   System.out.println(managerMemory.getAllEpic());
     //   System.out.println(managerMemory.getAllSubtasks());
//        System.out.println(managerMemory.getTaskById(0));

        File file = new File("storageFile.csv");
       FileBackedTaskManager.loadFromFile(file);
        FileBackedTaskManager.loadFromFile(file).createTask(new Task("save", "save to file", Status.NEW, LocalDateTime.of(2025, 11, 5, 10, 00), Duration.ofMinutes(45) ));
        FileBackedTaskManager.loadFromFile(file).createTask(new Task("clean", "clean the table", Status.NEW));
      FileBackedTaskManager.loadFromFile(file).createTask(new Task("download", "download from file", Status.NEW, LocalDateTime.of(2025, 11, 5, 11, 00), Duration.ofMinutes(45) ));
        FileBackedTaskManager.loadFromFile(file).createEpic(new Epic("nibble", "nibble ab apple"));
        FileBackedTaskManager.loadFromFile(file).createSubtask(new Subtask("cut", "cut an apple into pieces", Status.IN_PROGRESS,LocalDateTime.of(2025, 11, 5, 12, 00), Duration.ofMinutes(45),  3));
       FileBackedTaskManager.loadFromFile(file).createTask(new Task("gnaw", "gnaw at the tree", Status.IN_PROGRESS));
      //      Epic epic = FileBackedTaskManager.loadFromFile(file).getEpicById(3);
    //        epic.setDescription("DESCRIPTION");
     //       FileBackedTaskManager.loadFromFile(file).editEpic(epic);
     //       FileBackedTaskManager.loadFromFile(file).removeAll();
    //    System.out.println("created tasks:, %-20s");
     //   System.out.println("All tasks: " + FileBackedTaskManager.loadFromFile(file).getAllTasks());
    //    System.out.println("All epics: " + FileBackedTaskManager.loadFromFile(file).getAllEpic());
      //  System.out.println("All subtasks: " + FileBackedTaskManager.loadFromFile(file).getAllSubtasks());


//        FileBackedTaskManager.loadFromFile(new File("storageFile.csv"));
//        FileBackedTaskManager.loadFromFile(file).createTask(new Task("feed", "feed the cat", Status.NEW ));
//        FileBackedTaskManager.loadFromFile(new File("storageFile.csv"));
//        Managers manager = new Managers();
//        FileBackedTaskManager.loadFromFile(new File("storageFile.csv"));
//        manager.getFileBackedManager().getAllTasks();

//        Epic epic = manager.getFileBackedManager().getEpicById(2);
//        System.out.println("get epic: " + epic);
//        epic.setDescription("new description");
//        manager.getFileBackedManager().editEpic(epic);
//        System.out.println("edit epic: " + epic);
//        System.out.println();
//        System.out.println(FileBackedTaskManager.loadFromFile(file).getEpicById(2));
//        System.out.println("FromBackedManager: " + FileBackedTaskManager.loadFromFile(new File("storageFile.csv")).getEpicById(2));
//        FileBackedTaskManager.loadFromFile(new File("storageFile.csv"));
      //  backedManager.getAllEpic();
     //   backedManager.getAllTasks();
      //  backedManager.getAllSubtasks();

           //backedManager.save();

      //  Managers man = new Managers();
       // Manager taskManager = man.getDefault();
     //   InMemoryTaskManager taskManager = new InMemoryTaskManager();
//        taskManager.createTask(new Task("do the dishes", "after the party", Status.NEW, LocalDateTime.of(2025, 11, 5, 10, 00), Duration.ofMinutes(45)));
//        taskManager.createTask(new Task("do hwk", "math, biology", Status.IN_PROGRESS, LocalDateTime.of(2025, 11, 5, 10, 50), Duration.ofMinutes(15)));
//        taskManager.createTask(new Task("wallpaper", "in the hallway", Status.DONE, LocalDateTime.of(2025, 11, 5, 10, 25), Duration.ofMinutes(20)));
//        taskManager.createTask(new Task("close", "close the door", Status.NEW, LocalDateTime.of(2025, 11, 5, 9, 50), Duration.ofMinutes(45)));
//        taskManager.createTask(new Task("pam", "pampam", Status.NEW, LocalDateTime.of(2025, 11, 5, 14, 50), Duration.ofMinutes(45)));
//        taskManager.createTask(new Task("close", "close the door", Status.NEW, LocalDateTime.of(2025, 11, 5, 6, 00), Duration.ofMinutes(500)));
//        taskManager.createTask(new Task("close", "close the door", Status.NEW, LocalDateTime.of(2025, 11, 6, 14, 30), Duration.ofMinutes(45)));
      //  Epic epic1 = new Epic("sweep", "sweep the floor");
     //  taskManager.createEpic(epic1);
     //   taskManager.createSubtask(new Subtask("broom", "buy the broom", Status.DONE, LocalDateTime.of(2025, 11, 5, 11, 46), Duration.ofMinutes(7), epic1.getId()));
    //    taskManager.createSubtask(new Subtask("get the dustpan", "", Status.DONE, LocalDateTime.of(2025, 11, 5, 11, 00), Duration.ofMinutes(40), epic1.getId()));
      //  Epic epic2 = new Epic("cook dinner", "");

     //   Subtask subtask1 = new Subtask("broom", "buy the broom", Status.NEW, LocalDateTime.of(2025, 11, 5, 10, 45), Duration.ofMinutes(7), 3);
     //   Subtask subtask2 = new Subtask("get the dustpan", "", Status.NEW, LocalDateTime.of(2025, 11, 5, 11, 00), Duration.ofMinutes(40), 3);
     //   Subtask subtask3 = new Subtask("buy veggies", "tomatoes, mushrooms", Status.DONE, LocalDateTime.of(2025, 11, 5, 11, 30), Duration.ofMinutes(20), 4);
      //  Subtask subtask4 = new Subtask("cut ingredients", "dice, slice", Status.DONE, LocalDateTime.of(2025, 11, 5, 12, 00), Duration.ofMinutes(0), 4);

       // taskManager.createEpic(epic1);
      //  taskManager.createEpic(epic2);



      //  taskManager.createSubtask(subtask1);
     //   taskManager.createSubtask(subtask2);
      //  taskManager.createSubtask(subtask3);
       // taskManager.createSubtask(subtask4);

        //System.out.println(taskManager.getPrioritizedTasks());
      //  System.out.println(taskManager.getAllEpic());


//        InMemoryHistoryManager history = new InMemoryHistoryManager();
//
//        taskManager.createEpic(new Epic("draw", "draw a painting on the wall"));
//        taskManager.createSubtask(new Subtask("buy", "buy brushes and paints",Status.IN_PROGRESS, 9));
//        taskManager.createSubtask(new Subtask("choose", "choose a pic to paint",Status.IN_PROGRESS, 9));
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

//        Task task = new Task("finish", "complete the task finally");
//        task.setId(0);
//        taskManager.editTask(task);
//        System.out.println( "Test: " + taskManager.getTaskById(0));

//        ArrayList<Subtask> subtasks2 = new ArrayList<>();
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
//       System.out.println("Epic 1: " + epic2.subtaskIds);
//        System.out.println("Epic 1: " + epic2.subtaskIds);
//        System.out.println("Epic 2: " + epic2.subtaskIds);
//        System.out.println("All tasks: " + FileBackedTaskManager.loadFromFile(file).getAllTasks());
//        System.out.println("All epics: " + FileBackedTaskManager.loadFromFile(file).getAllEpic());
//        System.out.println("All subtasks: " + FileBackedTaskManager.loadFromFile(file).getAllSubtasksByEpic(3));

        // Test getPrioritized()
    }
}
