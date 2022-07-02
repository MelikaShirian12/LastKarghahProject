package Exceptions;

public class wrongPostID extends wrongInput
{
    public wrongPostID() {
        super("wrong id: there are no posts with this id in your account");
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
