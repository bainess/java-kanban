import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Managers {
    public Manager getDefault() {
        return new InMemoryTaskManager();
    }

    public HistoryManager getHistoryManager() {
        return new InMemoryHistoryManager();
    }

    public FileBackedTaskManager getFileBackedManager() {
        File path =new File("storageFile.csv");
        return FileBackedTaskManager.loadFromFile(path);
    }
}

