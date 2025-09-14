import java.util.ArrayList;
import java.util.List;

public class HistoryManager {
    ArrayList<Task> historyList = new ArrayList<>();

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
    public ArrayList<Task> getHistory() {
        return historyList;
    }
}
