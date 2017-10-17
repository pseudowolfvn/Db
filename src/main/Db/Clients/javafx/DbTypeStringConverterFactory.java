package Db.Clients.javafx;

import Db.Exceptions.IllegalConversionException;
import Db.Exceptions.UnsupportedTypeException;
import Db.Types.DbType;
import javafx.util.StringConverter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DbTypeStringConverterFactory {
    public static StringConverter<DbType> stringConverterFromType(String type) {
        return new StringConverter<DbType>() {
            @Override
            public String toString(DbType object) {
                if (object != null)
                    return object.toString();
                else return "undefined";
            }

            @Override
            public DbType fromString(String string) {
                DbType value = null;
                try
                {
                    Class<?> cls = Class.forName("Db.Types." + type);
                    value = (DbType) cls.newInstance();
                    Method fromString = cls.getMethod("fromString", String.class);
                    fromString.invoke(value, string);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return value;
            }
        };
    }
}
