package Db.Types;

import java.io.Serializable;

public interface DbType<T> extends Serializable {

    void fromString(String str);

    String toString();

    void set(T val);

    T get();
}
