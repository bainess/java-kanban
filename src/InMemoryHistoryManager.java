import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    List<Task> historyList = new ArrayList<>();

    @Override
    public void addToHistoryList(Task task) {
        if (historyList.size() > 10) {
            historyList.removeFirst();
            historyList.add(task);
        } else {

            historyList.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyList;
    }
}
