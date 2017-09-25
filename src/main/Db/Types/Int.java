package Db.Types;

public class Int implements DbType<Integer> {
    Integer value;
    public Int(Integer val) {
        value = val;
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
