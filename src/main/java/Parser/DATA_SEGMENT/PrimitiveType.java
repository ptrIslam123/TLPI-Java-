package Parser.DATA_SEGMENT;

import Parser.Type.Type;

public class PrimitiveType implements ObjectType {
   private String name;
   private Type value;
   private int visibility;

    public PrimitiveType(String name, Type value, int visibility) {
        this.name = name;
        this.value = value;
        this.visibility = visibility;
    }

    public void setValue(Type value) {
        this.value = value;
    }

    public int getVisibility() {
        return visibility;
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

    @Override
    public String getName() {
        return name;
    }
}
