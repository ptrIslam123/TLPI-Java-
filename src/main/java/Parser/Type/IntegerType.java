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
    public int asBoll() {
        return 0;
    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public void asVoid() {

    }
}
