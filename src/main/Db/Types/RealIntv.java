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
}
