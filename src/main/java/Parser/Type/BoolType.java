package Parser.Type;

public class BoolType implements Type {
    private String value;

    public BoolType(String value) {
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
    public int asBoll() {
        return Integer.parseInt(value);
    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public void asVoid() {

    }
}
