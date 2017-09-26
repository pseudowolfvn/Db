package Db.Exceptions;

public class UnsupportedTypeException extends Exception{
    public UnsupportedTypeException(String type) {
        super("This type : '" + type + "' is unsupported");
    }
}
