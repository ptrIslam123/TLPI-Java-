package Parser.Type;


import Parser.Type.StructType.Struct.Struct;
import Parser.Type.Types.Aggregate;
import Parser.Type.Types.Primitive;
import Parser.Type.Types.Type;

public class PrimitiveType implements Type {
    private Primitive value;

    public PrimitiveType(Primitive value) {
        this.value = value;
    }

    public Primitive getValue() {
        return value;
    }

    @Override
    public Primitive asPrimitive() {
        return value;
    }

    @Override
    public Aggregate asAggregate() {
        return null;
    }

    @Override
    public Struct asStruct() {
        return null;
    }
}
