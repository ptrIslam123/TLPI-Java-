package Parser.Type;

public class CharType implements Type {
    private String value;

    public CharType(String value) {
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
        return null;
    }

    @Override
    public char asChar() {
        return value.charAt(0);
    }

    @Override
    public void asVoid() {

    }
}
