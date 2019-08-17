package Parser.Type;

public class DoubleType implements Type {
    private String value;

    public DoubleType(String value) {
        this.value = value;
    }

    @Override
    public int asInt() {
        return 0;
    }

    @Override
    public double asDouble() {
        return Double.parseDouble(value);
    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public int asBool() {
        return 0;
    }
}
