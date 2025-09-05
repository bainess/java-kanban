public class Subtask extends Task {
    protected int epicId;

    public Subtask(String title, String description, Status status, int epicId) {
        super(title, description, status);
        this.epicId = epicId;
    }


    public int getEpicId(){
        return this.epicId;
    }

    @Override
    public String toString () {
        return "Subtask " + this.getId()  + " " + this.title + " "  + this.description + " "  + this.status;

    }
}
