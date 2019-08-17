package Parser.Type;

public class StringType implements Type {
    private String value;

    public StringType(String value) {
        this.value = value;
    }

    @Override
    public int asInt() {
        return 0;
    }

    @Override
    public double asDouble() {
        return 0;
    }

    @Override
    public String asString() {
        return value;
    }

    @Override
    public int asBool() {
        return 0;
    }
}
