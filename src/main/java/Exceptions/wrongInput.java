package Exceptions;

public class wrongInput extends Exception
{
    public wrongInput(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return "wrongInput";
    }
}
