public class Task {
    String title;
    String description;
    int id;
    Status status;

    Task(String title, String description, Status status){
        this.title = title;
        this.description = description;
        this.status = status;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Task task)) return false;

        return id == task.id && title.equals(task.title) && description.equals(task.description) && status == task.status;
    }

    private void setId (){
        id = hashCode();
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + id;
        result = 31 * result + status.hashCode();
        return result;
    }


}
