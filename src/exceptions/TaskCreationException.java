package exceptions;

public class TaskCreationException extends RuntimeException {
    public TaskCreationException(String timeSlotHasBeenOccupied) {
        super(timeSlotHasBeenOccupied);
    }
}
