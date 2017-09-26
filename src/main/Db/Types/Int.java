package Db.Types;

public class Int implements DbType<Integer> {
    private Integer value;

    public Int() {}

    public Int(Integer val) {
        value = val;
    }

    @Override
    public void fromString(String str) {
        value = Integer.parseInt(str);
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
