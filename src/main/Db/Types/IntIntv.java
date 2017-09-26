package Db.Types;

public class IntIntv implements DbType<Range<Integer>> {
    private Range<Integer> value;

    public IntIntv() {
        value = new Range<>();
    }

    public IntIntv(Integer l, Integer r) {
        value = new Range(l, r);
    }

    @Override
    public void fromString(String str) {
        String sep = "..";
        String left = str.substring(0, str.indexOf(sep));
        String right = str.substring(str.indexOf(sep) + sep.length());
        value.setLeft(Integer.parseInt(left));
        value.setRight(Integer.parseInt(right));
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public void set(Range<Integer> val) {
        value = val;
    }

    @Override
    public Range<Integer> get() {
        return value;
    }
}
