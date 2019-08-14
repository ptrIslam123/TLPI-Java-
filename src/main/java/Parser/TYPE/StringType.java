package Parser.TYPE;

public class StringType implements Type {
    private String value;

    public StringType(String value) {
        this.value = value;
    }

    @Override
    public int asInteger32() {
        return 0;
    }

    @Override
    public double asDouble64() {
        return 0;
    }

    @Override
    public String asString() {
        return this.value;
    }

    @Override
    public boolean asBool() {
        return false;
    }
}
