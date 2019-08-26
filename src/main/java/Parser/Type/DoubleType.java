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
    public boolean asBoll() {
        return false;
    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public char asChar() {
        return 0;
    }

    @Override
    public void asVoid() {

    }
}
