public class Managers {
    public <T> Manager getDefault() {
        return new InMemoryTaskManager();    }
}
