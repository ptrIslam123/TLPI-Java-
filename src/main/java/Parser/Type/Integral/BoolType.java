package Parser.Type.Integral;

import Parser.Type.Types.Primitive;

public class BoolType implements Primitive {
    private String value;

    public BoolType(String value) {
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
        if(value.equalsIgnoreCase("0"))return false;
        else return true;
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
