package Db.Types;

public interface DbType<T> {

    void fromString(String str);

    String toString();

    void set(T val);

    T get();
}
