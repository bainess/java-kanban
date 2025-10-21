import java.io.*;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager{
    private final File storageFile = new File("storageFile.csv");


    @Override
    public void createTask(Task task) {
        super.createTask(task);
        try {
            save();
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Cannot save to file");
            } catch (ManagerSaveException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        try {
            save();
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Cannot save to file");
            } catch (ManagerSaveException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void createSubtask(Subtask subtask){
        super.createSubtask(subtask);
        try {
            save();
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Cannot save to file");
            } catch (ManagerSaveException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        try {
            save();
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Cannot save to file");
            } catch (ManagerSaveException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        try {
            save();
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Cannot save to file");
            } catch (ManagerSaveException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void removeSubtaskById(int id) {
        super.removeSubtaskById(id);
        try {
            save();
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Cannot save to file");
            } catch (ManagerSaveException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void removeAll() {
        super.removeAll();
        try {
            save();
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Cannot save to file");
            } catch (ManagerSaveException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void editTask(Task task) {
        super.editTask(task);
        try {
            save();
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Cannot save to file");
            } catch (ManagerSaveException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void editEpic(Epic epic) {
        super.editEpic(epic);
        try {
            save();
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Cannot save to file");
            } catch (ManagerSaveException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void editSubtask(Subtask subtask) {
        super.editSubtask(subtask);
        try {
            save();
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Cannot save to file");
            } catch (ManagerSaveException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        try {
            save();
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Cannot save to file");
            } catch (ManagerSaveException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        try {
            save();
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Cannot save to file");
            } catch (ManagerSaveException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        try {
            save();
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Cannot save to file");
            } catch (ManagerSaveException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void save() throws IOException {
        try (FileWriter fw = new FileWriter(storageFile)) {
            fw.write("id, type, name, status, description, epic\n");
            List<String> tasksToSave = transferToSave();
            for (String task : tasksToSave) {
                fw.write(task);
            }

        } catch (ManagerSaveException e) {
            System.out.printf("Error saving to file%s%n", e.getMessage());
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

        private void fromStringToTasksArray  (String taskInString) {
            String[] splitString = taskInString.split(", ");
            Type type = Type.valueOf(splitString[1]);
            switch (type) {
                case TASK:
                    Task task = new Task(splitString[2], splitString[4], Status.valueOf(splitString[3]));
                    createTask(task);
                    break;
                case EPIC:
                    Epic epic = new Epic(splitString[2], splitString[4]);
                    createEpic(epic);
                    break;
                case SUBTASK:
                    Subtask subtask = new Subtask(splitString[2], splitString[4], Status.valueOf(splitString[3]), Integer.parseInt(splitString[5]));
                    createSubtask(subtask);
                    break;
                default:
                    throw new ClassCastException("Invalid class");
            }
        }

        public void readFromFile(String filename) {
            try (FileReader fr = new FileReader(storageFile)) {
                BufferedReader br = new BufferedReader((fr));
                int iteration = 0;
                String line;
                while ((line = br.readLine()) != null) {
                    if(iteration == 0) {
                        iteration++;
                        continue;
                    }
                    fromStringToTasksArray(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    private enum Type {
        TASK,
        EPIC,
        SUBTASK
    }
}
