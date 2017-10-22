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
        try {
            String sep = "..";
            int leftEnd = str.indexOf(sep) - 1;
            while (Character.isWhitespace(str.charAt(leftEnd)))
                --leftEnd;
            String left = str.substring(0, leftEnd + 1);
            int rightStart = str.indexOf(sep) + sep.length();
            while (Character.isWhitespace(str.charAt(rightStart)))
                ++rightStart;
            String right = str.substring(rightStart);
            value.setLeft(Integer.parseInt(left));
            value.setRight(Integer.parseInt(right));
        }
        catch (Exception ex) {
            value.setLeft(0);
            value.setRight(0);
        }
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
