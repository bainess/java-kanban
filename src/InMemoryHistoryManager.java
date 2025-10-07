import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private Node<Task> start;
    private Node<Task> end;
    int size = 0;
    Map<Integer, Node<Task>> historyListIdHolder= new HashMap<>();

    @Override
    public void addToHistoryList(Task task) {
        boolean containsKey = historyListIdHolder.containsKey(task.getId());
        if (containsKey) {
            removeNode(historyListIdHolder.get(task.getId()));
            historyListIdHolder.remove(task.getId());

        }
        addLast(task);

    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    public void addLast(Task task) {
        final Node<Task> oldEnd = end;
        Node<Task> thisNode = new Node<>(oldEnd, task, null);
        end = thisNode;
        if (oldEnd == null)
            start = thisNode;
        else
            oldEnd.next = thisNode;
        historyListIdHolder.put(task.getId(), thisNode);
        size++;

    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        Node<Task> currHead = start;
        if (currHead == null) return new ArrayList<>();
        while (currHead != null) {
            taskList.add(currHead.task);
            currHead = currHead.next;
        }
        return taskList;
    }

    public void removeNode(Node<Task> node) {
        final Node<Task> prevNode = node.prev;
        final Node<Task> nextNode = node.next;
        if (prevNode != null ) prevNode.next = node.next;
        if (start.task.equals(node.task)) start = nextNode;
        if (nextNode != null) nextNode.prev = node.prev;
        if (end.task.equals(node.task)) end = prevNode;
        historyListIdHolder.remove(node.task.getId());
        size = size - 1;
    }

}
