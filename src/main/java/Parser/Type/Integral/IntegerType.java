package Parser.Type.Integral;

import Parser.Type.Types.Primitive;

public class IntegerType implements Primitive {
    private String value;

    public IntegerType(String value) {
        this.value = value;
    }

    @Override
    public int asInt() {
        return Integer.parseInt(value);
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
