package Parser.Type.Integral;

import Parser.Type.Types.Primitive;

public class CharType implements Primitive {
    private String value;

    public CharType(String value) {
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
        return value.charAt(0);
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
