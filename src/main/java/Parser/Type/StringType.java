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
    public boolean asBoll() {
        return false;
    }

    @Override
    public String asString() {
        return value;
    }

    @Override
    public char asChar() {
        return 0;
    }

    @Override
    public void asVoid() {

    }
}
