import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private Node<Task> start;
    private Node<Task> end;
    private Map<Integer, Node<Task>> historyListIdHolder = new HashMap<>();

    @Override
    public void addToHistoryList(Task task) {
        remove(task.getId());
        addLast(task);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        if (historyListIdHolder.containsKey(id)) {
            Node<Task> node = historyListIdHolder.get(id);
            removeNode(node);
        }
    }

    public void addLast(Task task) {
        final Node<Task> oldEnd = end;
        Node<Task> thisNode = new Node<>(oldEnd, task, null);
        end = thisNode;
        if (oldEnd == null)
            start = thisNode;
        else
            oldEnd.setNextNode(thisNode);
        historyListIdHolder.put(task.getId(), thisNode);
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        Node<Task> currHead = start;
        if (currHead == null) return new ArrayList<>();
        while (currHead != null) {
            taskList.add(currHead.getTaskFromNode());
            currHead = currHead.getNextNode();
        }
        return taskList;
    }

    public void removeNode(Node<Task> node) {
        final Node<Task> prevNode = node.getPrevNode();
        final Node<Task> nextNode = node.getNextNode();
        if (prevNode != null) prevNode.setNextNode(node.getNextNode());
        if (start.getTaskFromNode().equals(node.getTaskFromNode())) start = nextNode;
        if (nextNode != null) nextNode.setPrevNode(node.getPrevNode());
        if (end.getTaskFromNode().equals(node.getTaskFromNode())) end = prevNode;
        historyListIdHolder.remove(node.getTaskFromNode().getId());
    }


}
