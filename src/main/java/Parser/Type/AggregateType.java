package Parser.Type;

import Parser.Type.StructType.Struct.Struct;
import Parser.Type.Types.Aggregate;
import Parser.Type.Types.Primitive;
import Parser.Type.Types.Type;

public class AggregateType implements Type {
    private Aggregate value;

    public AggregateType(Aggregate value) {
        this.value = value;
    }

    @Override
    public Primitive asPrimitive() {
        return null;
    }

    @Override
    public Aggregate asAggregate() {
        return value;
    }

    @Override
    public Struct asStruct() {
        return null;
    }
}
