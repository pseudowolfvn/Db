package Db.Types;

public class RealIntv implements DbType<Range<Double>> {

    Range<Double> value;

    public RealIntv(Double l, Double r) {
        value = new Range<Double>(l, r);
    }

    @Override
    public void set(Range<Double> val) {
        value = val;
    }

    @Override
    public Range<Double> get() {
        return value;
    }

    @Override
    public void fromString(String str) {
        String left = str.substring(0, str.indexOf('.'));
        String right = str.substring(str.lastIndexOf('.'));
        value.setLeft(Double.parseDouble(left));
        value.setRight(Double.parseDouble(right));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
