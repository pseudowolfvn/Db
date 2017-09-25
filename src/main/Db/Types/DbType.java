package Db.Types;

public interface DbType<T> {
    static boolean fromString(String str);
    void set(T val);
    T get();
}
