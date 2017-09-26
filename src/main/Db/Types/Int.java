package Db.Types;

public class Int implements DbType<Integer> {
    Integer value;

    public Int(Integer val) {
        value = val;
    }

    @Override
    public void fromString(String str) {
        value = Integer.getInteger(str);
    }

    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public void set(Integer val) {
        value = val;
    }

    @Override
    public Integer get() {
        return value;
    }
}
