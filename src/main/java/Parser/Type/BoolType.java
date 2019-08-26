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
    public boolean asBoll() {
        if(value.equalsIgnoreCase("0"))return false;
        else return true;
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
