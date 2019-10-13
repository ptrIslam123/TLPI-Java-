package Parser.Type.Integral;

import Parser.Type.Types.Primitive;

public class DoubleType implements Primitive {
    private String value;

    public DoubleType(String value) {
        this.value = value;
    }

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
        return Double.parseDouble(value);
    }

    @Override
    public void asVoid() {
        return;
    }
}
