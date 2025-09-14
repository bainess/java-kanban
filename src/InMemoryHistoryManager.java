import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    ArrayList<Task> historyList = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (historyList.size() < 11) {
            historyList.add(task);
        } else {
            ArrayList<Task> historyListTemp = new ArrayList<>();
            for (int i = 1; i < historyList.size(); i++) {
                historyListTemp.add(historyList.get(i));
            }
            historyListTemp.add(task);
            historyList = historyListTemp;
        }
    }
    @Override
    public ArrayList<Task> getHistory() {
        return historyList;
    }
}
