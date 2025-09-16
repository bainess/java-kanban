import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Managers man = new Managers();
        InMemoryTaskManager taskManager = man.getDefault();
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

        taskManager.createEpic(new Epic("draw", "draw a painting on the wall"));
        taskManager.createSubtask(new Subtask("buy", "buy brushes and paints",Status.IN_PROGRESS, 9));

        System.out.println( "Print 1: " + taskManager.getTaskById(0));
        System.out.println( "Print 2: " + taskManager.getTaskById(1));
        System.out.println( "Print 3: " + taskManager.getTaskById(2));
        System.out.println( "Print 4: " + taskManager.getEpicById(3));
        System.out.println( "Print 5: " + taskManager.getEpicById(4));
        System.out.println( "Print 6: " + taskManager.getSubtaskById(5));
        System.out.println( "Print 7: " + taskManager.getSubtaskById(6));
        System.out.println( "Print 8: " + taskManager.getSubtaskById(7));
        System.out.println( "Print 9: " + taskManager.getSubtaskById(8));
        System.out.println( "Print 10: " + taskManager.getEpicById(9));
        System.out.println( "Test: " + taskManager.showHistory());
        System.out.println( "Print 1: " + taskManager.getAllEpic());
        System.out.println( "Print 1: " + taskManager.getSubtaskById(10));
        System.out.println( "Test: " + taskManager.showHistory());

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
//        System.out.println("All tasks: " + taskManager.getAllTasks());
//        System.out.println("All epics: " + taskManager.getAllEpic());
//        System.out.println("All subtasks: " + taskManager.getAllSubtasksByEpic(4));
    }
}
