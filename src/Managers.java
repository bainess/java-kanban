import java.io.File;

public class Managers {
    public Manager getDefault() {
        return new InMemoryTaskManager();
    }

    public HistoryManager getHistoryManager() {
        return new InMemoryHistoryManager();
    }

    public FileBackedTaskManager getFileBackedManager() {
        File path = new File("storageFile.csv");
        return FileBackedTaskManager.loadFromFile(path);
    }
}

