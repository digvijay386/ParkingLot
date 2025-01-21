package model.exceptions;

public class InvalidTicketException extends IllegalArgumentException {
    public InvalidTicketException(String message) {
        super(message);
    }
}
