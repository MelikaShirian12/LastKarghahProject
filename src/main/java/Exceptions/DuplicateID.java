package Exceptions;

import java.util.InputMismatchException;

public class DuplicateID extends InputMismatchException
{
    public DuplicateID()
    {
        super("duplicate id !");
    }

    @Override
    public String toString() {
        return "someone else has already chosen this id .";
    }
}
