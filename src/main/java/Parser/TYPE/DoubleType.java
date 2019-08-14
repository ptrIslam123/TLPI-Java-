package Parser.TYPE;

public class DoubleType implements Type {
    private String value;

    public DoubleType(final String value) {
        this.value = value;
    }
    public DoubleType(final double value) {
        this.value = String.valueOf(value);
    }

    @Override
    public int asInteger32() {
        return 0;
    }

    @Override
    public double asDouble64() {
        return Double.parseDouble(value);
    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public boolean asBool() {
        return false;
    }
}
