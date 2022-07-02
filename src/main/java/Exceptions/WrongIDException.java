package Exceptions;

public class WrongIDException extends Exception {
    public WrongIDException() {
        super("Wrong id: this id doesnt exist");
    }
}
