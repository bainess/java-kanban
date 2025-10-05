import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    HistoryLinkedList<Task> historyList = new HistoryLinkedList<>();
    Map<Integer, Task> historyListIdHolder= new HashMap<>();

    @Override
    public void addToHistoryList(Task task) {
        if (historyList.getSize() > 10) {
            historyListIdHolder.remove(task.getId());
            historyList.removeFirst();
            historyList.addLast(task);
            historyListIdHolder.put(task.getId(), task);
        } else {

            historyList.addLast(task);
        }
    }

    @Override
    public HistoryLinkedList<Task> getHistory() {
        return historyList;
    }

    static class HistoryLinkedList<Task> {
        public Node<Task> start;
        public Node<Task> end;
        int size = 0;

        public void addLast(Task task) {
            final Node<Task> oldEnd = end;
            Node<Task> thisNode = new Node<>(oldEnd, task, null);
            end = thisNode;
            if (oldEnd == null)
                start = thisNode;
            else
                oldEnd.next = thisNode;
            size++;
        }

        public int getSize(){
            return size;
        }

        public ArrayList<Task> getTasks() {
            ArrayList<Task> taskList = new ArrayList<>();
            Node<Task> currHead = start;
            if (currHead == null) throw new NoSuchElementException();
            while (currHead != null) {
                taskList.add(currHead.task);
                currHead = currHead.next;
            }
            return taskList;
        }
        public void removeNode(Node<Task> node) {
            final Node<Task> prevNode = node.prev;
            final Node<Task> nextNode = node.next;
            prevNode.next = node.next;
            nextNode.prev = node.prev;
        }

        public void removeFirst() {
            final Node<Task> nextNode = start.next;
            nextNode.prev = null;
            start = nextNode;
        }

    }
}
