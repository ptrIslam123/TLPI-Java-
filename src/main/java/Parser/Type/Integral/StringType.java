package Parser.Type.Integral;

import Parser.Type.Types.Primitive;

public class StringType implements Primitive {
    private String value;

    public StringType(String value) {
        this.value = value;
    }

    @Override
    public int asInt() {
        return 0;
    }

    @Override
    public String asString() {
        return value;
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
