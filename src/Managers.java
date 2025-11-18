import java.io.File;
import java.nio.file.Path;

public class Managers {
    public Manager getDefault() {
        return new InMemoryTaskManager();
    }

    public HistoryManager getHistoryManager() {
        return new InMemoryHistoryManager();
    }

    public FileBackedTaskManager getFileBackedManager() {
        File path = new File(Path.of("storageFile.csv").toUri());
        return FileBackedTaskManager.loadFromFile(path);
    }
}

