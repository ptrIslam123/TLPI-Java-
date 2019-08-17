package Parser.Type;

import Lexer.TypeToken;

public class IntegerType implements Type {
    private String value;

    public IntegerType(String value) {
        this.value = value;
    }

    @Override
    public int asInt() {
        return Integer.parseInt(value);
    }

    @Override
    public double asDouble() {
        return 0;
    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public int asBool() {
        return 0;
    }
}
