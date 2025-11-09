import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.format.SignStyle;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File storageFile;

    FileBackedTaskManager(String filePath) {
        storageFile = new File(filePath);
    }

    public File getStorageFile() {
        return storageFile;
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    public void removeSubtaskById(int id) {
        super.removeSubtaskById(id);
        save();
    }

    @Override
    public void removeAll() {
        super.removeAll();
        save();
    }

    @Override
    public void editTask(Task task) {
        super.editTask(task);
        save();
    }

    @Override
    public void editEpic(Epic epic) {
        super.editEpic(epic);
            save();
    }

    @Override
    public void editSubtask(Subtask subtask) {
        super.editSubtask(subtask);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
            save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    private void save()  {
        try (FileWriter fw = new FileWriter(storageFile)) {
            fw.write("id, type, name, status, description, start time, duration epic\n");
            List<String> tasksToSave = transferToSave();
            for (String task : tasksToSave) {
                fw.write(task);
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Error saving to file%s%n", e);
        }
    }

    private List<String> transferToSave() {
        List<Task> tasksToSave = getAllTasks();
        List<Epic> epicToSave = getAllEpic();
        List<Subtask> subtaskToSave = getAllSubtasks();
        tasksToSave.addAll(epicToSave);
        tasksToSave.addAll(subtaskToSave);
        List<String> joinedList = new ArrayList<>();
       //    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

        for (Task task : tasksToSave) {
            String time = " ";
            long duration = 0;
            if (task.getStartTime() != null) time = task.getStartTime().toString();
            if (task.getDuration() != null) duration = task.getDuration().toMinutes();
            String type = task.getClass().toString().toUpperCase().substring(6);
            StringBuilder str = new StringBuilder().append(task.getId()).append(", ").append(type).append(", ")
                    .append(task.getTitle())
                    .append(", ").append(task.getStatus()).append(", ")
                    .append(task.getDescription()).append(", ").append(time).append(", ")
                    .append(duration);
            if (task instanceof Subtask) {
                Subtask sub = (Subtask) task;
                str.append(", ").append(sub.getEpicId());
            }
            joinedList.add(str.append("\n").toString());
        }
        return joinedList;
    }

        private void fromStringToTasksArray(String taskInString) {
            System.out.println(taskInString);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
            String[] splitString = taskInString.split(", ");
            Type type = Type.valueOf(splitString[1]);
            LocalDateTime startTime = null;
            if (!splitString[5].isBlank()) startTime = LocalDateTime.parse(splitString[5]);
            Duration duration = null;
            if (Long.parseLong(splitString[6]) != 0) duration = Duration.ofMinutes(Long.parseLong(splitString[6]));
            switch (type) {
                case TASK:
                    Task task = restoreTaskFromString(Integer.parseInt(splitString[0]), splitString[2], splitString[4],
                            Status.valueOf(splitString[3]),
                            startTime,
                            duration);
                    taskList.put(Integer.parseInt(splitString[0]), task);
                    break;
                case EPIC:
                    Epic epic = restoreEpicFromString(Integer.parseInt(splitString[0]), splitString[2], splitString[4],
                            Status.valueOf(splitString[3]),
                            startTime,
                            duration);
                    epicList.put(Integer.parseInt(splitString[0]), epic);
                    break;
                case SUBTASK:
                    Subtask subtask = restoreSubtaskFromString(Integer.parseInt(splitString[0]), splitString[2],
                            splitString[4], Status.valueOf(splitString[3]),
                            startTime,
                            duration,
                            Integer.parseInt(splitString[7]));
                    subtaskList.put(Integer.parseInt(splitString[0]), subtask);
                    getEpicById(Integer.parseInt(splitString[7])).addSubtaskId(Integer.parseInt(splitString[0]));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid class");
            }
        }

        public static FileBackedTaskManager loadFromFile(File file) {
            FileBackedTaskManager manager = new FileBackedTaskManager(file.toString());
            if (file.length() > 0) manager.readFromFile(file);
            return manager;
        }

        private void readFromFile(File file) {
            try (FileReader fr = new FileReader(file)) {
                BufferedReader br = new BufferedReader((fr));
                int iteration = 0;
                String line;
                while ((line = br.readLine()) != null) {
                    if (iteration == 0) {
                        iteration++;
                        continue;
                    }
                    fromStringToTasksArray(line);
                }
                setCounter();
            } catch (IOException e) {
                throw new ManagerSaveException("Failed to read from file", e);
            }
        }

    private void setCounter() {
        int lastId = 0;
        for (int key : taskList.keySet()) {
            if (key > lastId) lastId = key;
        }
        for (int key : epicList.keySet()) {
            if (key > lastId) lastId = key;
        }
        for (int key : subtaskList.keySet()) {
            if (key > lastId) lastId = key;
        }
        count = lastId + 1;

    }

    private Task restoreTaskFromString(int id, String title,
                                       String description,
                                       Status status,
                                       LocalDateTime startTime,
                                       Duration duration) {
        return new Task(id, title, description, status, startTime, duration);
    }

    private Epic restoreEpicFromString(int id, String title,
                                       String description,
                                       Status status,
                                       LocalDateTime startTime,
                                       Duration duration) {
        return new Epic(id, title, description, status, startTime, duration);
    }

    private Subtask restoreSubtaskFromString(int id, String title,
                                             String description,
                                             Status status,
                                             LocalDateTime startTime,
                                             Duration duration,
                                             int epicId) {
        return new Subtask(id, title, description, status, startTime, duration, epicId);
    }
}
