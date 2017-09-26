package Db.Types;

public class Char implements DbType<Character> {
    Character value;

    public Char(Character val) {
        value = val;
    }

    @Override
    public void fromString(String str) {
        value = str.charAt(0);
    }

    @Override
    public String toString() {
        return Character.toString(value);
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
