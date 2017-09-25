package Db.Types;

public class IntIntv implements DbType<Range<Integer>> {
    Range<Integer> value;
    public IntIntv(Integer l, Integer r) {
        value = new Range(l, r);
    }

    @Override
    public void set(Range<Integer> val) {
        value = val;
    }

    @Override
    public Range<Integer>get() {
        return value;
    }
}
