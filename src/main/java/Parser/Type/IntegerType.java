package Parser.Type;

public final class IntegerType implements Type {
    private String value;

    public IntegerType(String value) {
        this.value = value;
    }

    @Override
    public int asInt() {
        return Integer.parseInt(value);
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
