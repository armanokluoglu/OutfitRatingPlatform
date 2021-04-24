package model.exception;

public class UserAlreadyException extends Exception {
    public UserAlreadyException() {
    }

    public UserAlreadyException(String message) {
        super(message);
    }
}
