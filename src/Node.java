public class Node<T extends Task> {
    private final Task task;
    private Node<T> next;
    private Node<T> prev;

    public Node(Node<T> prev, Task task, Node<T> next) {
        this.task = task;
        this.next = next;
        this.prev = prev;
    }

    public Node<T> getNextNode() {
        return next;
    }

    public Node<T> getPrevNode() {
        return prev;
    }

    public Task getTaskFromNode() {
        return task;
    }

    void setNextNode(Node<T> node) {
        next = node;
    }

    void setPrevNode(Node<T> node) {
        prev = node;
    }
}
