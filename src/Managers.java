public class Managers {
    public InMemoryTaskManager getDefault() {
        return new InMemoryTaskManager();    }
    public InMemoryHistoryManager getHistoryManager() {
        return new InMemoryHistoryManager();
    }
}

