package Db.Types;

import java.security.spec.ECField;

public class Int implements DbType<Integer> {
    private Integer value;

    public Int() {}

    public Int(Integer val) {
        value = val;
    }

    @Override
    public void fromString(String str) {
        try {
            value = Integer.parseInt(str);
        }
        catch (Exception ex) {
            value = 0;
        }
    }

    @Override
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
