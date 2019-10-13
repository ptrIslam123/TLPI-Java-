package Parser.Type.Integral;

import Parser.Type.Types.Primitive;

public class VoidType implements Primitive {
    @Override
    public int asInt() {
        return 0;
    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public boolean asBool() {
        return false;
    }

    @Override
    public char asChar() {
        return 0;
    }

    @Override
    public double asDouble() {
        return 0;
    }

    @Override
    public void asVoid() {
        return;
    }
}
