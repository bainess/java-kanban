public class Managers {
    public Manager getDefault() {
        return new InMemoryTaskManager();
    }

    public HistoryManager getHistoryManager() {
        return new InMemoryHistoryManager();
    }
}

