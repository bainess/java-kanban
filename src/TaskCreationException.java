public class TaskCreationException extends Exception {
    public TaskCreationException(String timeSlotHasBeenOccupied) {
        super(timeSlotHasBeenOccupied);
    }
}
