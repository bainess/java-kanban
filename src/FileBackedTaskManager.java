import java.io.*;
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
            fw.write("id, type, name, status, description, epic\n");
            List<String> tasksToSave = transferToSave();
            for (String task : tasksToSave) {
                fw.write(task);
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Error saving to file%s%n", e);
        }
    }

    private List<String> transferToSave() {
        List<Task>  tasksToSave = getAllTasks();
        List<? extends Task> epicToSave = getAllEpic();
        List<? extends Task> subtaskToSave = getAllSubtasks();
        tasksToSave.addAll(epicToSave);
        tasksToSave.addAll(subtaskToSave);
        List<String> joinedList = new ArrayList<>();


        for (Task task : tasksToSave) {
            String type = task.getClass().toString().toUpperCase().substring(6);
            StringBuilder str = new StringBuilder().append(task.getId()).append(", ").append(type).append(", ")
                    .append(task.getTitle())
                    .append(", ").append(task.getStatus()).append(", ")
                    .append(task.getDescription());

            if (task instanceof Subtask) {
                Subtask sub = (Subtask) task;
                str.append(", ").append(sub.getEpicId());
            }
            joinedList.add(str.append("\n").toString());
        }
        return joinedList;
    }

        private void fromStringToTasksArray(String taskInString) {
            String[] splitString = taskInString.split(", ");
            Type type = Type.valueOf(splitString[1]);
            switch (type) {
                case TASK:
                    Task task = restoreTaskFromString(Integer.parseInt(splitString[0]), splitString[2], splitString[4],
                            Status.valueOf(splitString[3]));
                    taskList.put(Integer.parseInt(splitString[0]), task);
                    break;
                case EPIC:
                    Epic epic = restoreEpicFromString(Integer.parseInt(splitString[0]), splitString[2], splitString[4], Status.valueOf(splitString[3]));
                   epicList.put(Integer.parseInt(splitString[0]), epic);
                    break;
                case SUBTASK:
                    Subtask subtask = restoreSubaskFromString(Integer.parseInt(splitString[0]), splitString[2],
                            splitString[4], Status.valueOf(splitString[3]), Integer.parseInt(splitString[5]));
                    subtaskList.put(Integer.parseInt(splitString[0]), subtask);
                    getEpicById(Integer.parseInt(splitString[5])).addSubtaskId(Integer.parseInt(splitString[0]));

                    break;
                default:
                    throw new ClassCastException("Invalid class");
            }
        }
        static FileBackedTaskManager loadFromFile(File file) {
            FileBackedTaskManager fileBackedManager = new FileBackedTaskManager("storageFile.csv");
            fileBackedManager.readFromFile("storageFile.csv");
            return fileBackedManager;
        }

        public void readFromFile(String filename) {
            try (FileReader fr = new FileReader(storageFile)) {
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
            } catch (IOException e) {
                throw new ManagerSaveException("Failed to read from file", e);
            }

        }

    private Task restoreTaskFromString(int id, String title, String description, Status status) {
        return new Task(id, title, description, status);
    }

    private Epic restoreEpicFromString(int id, String title, String description, Status status) {
        return new Epic(id, title, description, status);
    }

    private Subtask restoreSubaskFromString(int id, String title, String description, Status status,int epicId) {
        return new Subtask(id, title, description, status, epicId);
    }
}
