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
