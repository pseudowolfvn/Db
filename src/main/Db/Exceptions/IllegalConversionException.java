package Db.Exceptions;

public class IllegalConversionException extends Exception {
    public IllegalConversionException(String type, String str) {
        super("Conversion from: '" + str + "' for type: '" + type + "' is illegal");
    }
}
