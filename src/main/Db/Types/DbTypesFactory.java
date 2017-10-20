package Db.Types;

import Db.Exceptions.IllegalConversionException;
import Db.Exceptions.UnsupportedTypeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbTypesFactory {

    public static DbType dbTypeFromString(String type, String str)
            throws UnsupportedTypeException, IllegalConversionException {
        DbType value = null;
        try
        {
            Class<?> cls = Class.forName("Db.Types." + type);
            value = (DbType) cls.newInstance();
            Method fromString = cls.getMethod("fromString", String.class);
            fromString.invoke(value, str);
        }catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            throw new UnsupportedTypeException(type);
        } catch (InvocationTargetException e) {
            throw new IllegalConversionException(type, str);
        }
        return value;
    }

    public static List<String> supportedTypeNames() {
        return Arrays.asList("Char", "Int", "IntIntv", "RealIntv");
    }

}
