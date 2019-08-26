package Parser.DATA_SEGMENT;

import Parser.Type.Type;

public class PrimitiveType implements ObjectType {
    private Type value;

    public PrimitiveType(Type value) {
        this.value = value;
    }

    @Override
    public Type asPrimative() {
        return value;
    }

    @Override
    public Type[] asAggregate() {
        return null;
    }

    @Override
    public Type[][] asMultiAggregate() {
        return null;
    }
}
