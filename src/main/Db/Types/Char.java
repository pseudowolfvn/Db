package Db.Types;

public class Char implements DbType<Character> {
    Character value;

    public Char(Character val) {
        value = val;
    }

    @Override
    public void set(Character val) {
        value = val;
    }

    @Override
    public Character get() {
        return value;
    }
}
