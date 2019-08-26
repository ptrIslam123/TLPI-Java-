package Parser.Type;

public class VoidType implements Type {
    public VoidType() {}

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
        return 0;
    }

    @Override
    public void asVoid() {

    }
}
