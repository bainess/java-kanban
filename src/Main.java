import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Manager taskManager = new Manager();
        Task task1 = new Task("do the dishes", "after the party", Status.NEW);
        Task task2 = new Task("do hwk", "math, biology", Status.IN_PROGRESS);
        Task task3 = new Task("wallpaper", "in the hallway", Status.DONE);
        Epic epic1 = new Epic("sweep", "sweep the floor", 3);
        Epic epic2 = new Epic("cook dinner", "", 4);

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



        System.out.println( "Print 1: " + taskManager.getAllTasks());

        ArrayList<Integer> ids2 = new ArrayList<>();

        ArrayList<Subtask> subtasks2 = new ArrayList<>();
        taskManager.createSubtask(subtask3);
        taskManager.createSubtask(subtask4);

        taskManager.createEpic(epic2);
        System.out.println("Get by id " + taskManager.getTaskById(3));
        System.out.println( "Print 2: " + taskManager.getAllEpic());
        System.out.println("Print 3 " + taskManager.getAllSubtasks());
        System.out.println(taskManager.getEpicById(3).toString());
        taskManager.removeTaskById(4);
        System.out.println("Print 33 " + taskManager.getAllSubtasks());
        taskManager.removeSubtaskById(5);
        System.out.println(taskManager.getEpicById(3).toString());
        System.out.println("Print 333 " + taskManager.getAllSubtasks());

      // taskManager.removeAll();
     //  System.out.println("Print 4 " + taskManager.getAllSubtasks());
     //  System.out.println(subtask3.getEpicId());
     //   System.out.println(subtask3.getEpicId());
//        System.out.println("Epic 1: " + epic2.subtaskIds);
        System.out.println("Epic 2: " + epic2.subtaskIds);
        // taskManager.removeAnyTaskById(5);
        System.out.println("All tasks: " + taskManager.getAllTasks());
        System.out.println("All epics: " + taskManager.getAllEpic());
        System.out.println("All subtasks: " + taskManager.getAllSubtasksByEpic(4));
    }
}
