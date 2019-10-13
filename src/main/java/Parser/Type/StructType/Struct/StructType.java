package Parser.Type.StructType.Struct;

import Parser.Type.StructType.ArrayType;
import Parser.Type.StructType.MultiArrayType;
import Parser.Type.Types.Aggregate;
import Parser.Type.Types.Primitive;
import Parser.Type.Types.Type;

public class StructType implements Type {
    private Struct value;
    private String name;

    public StructType(final String name, final Struct value) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Type getValueField(final String name){
        StructBody structBody = value.getBody();
        return structBody.getValue(name).getValue();
    }

    public Type getValueField(final String name, final Type index){
        StructBody structBody = value.getBody();
        Type object  = structBody.getValue(name).getValue();
        Primitive[] primitives = object.asAggregate().asArray();
        ArrayType arrayType = new ArrayType(primitives, primitives.length);
        return arrayType.getValueIndex(index);
    }

    public Type getValueField(final String name, final Type indexFirst, final Type indexSecond){
        StructBody structBody = value.getBody();
        Type object  = structBody.getValue(name).getValue();
        Primitive[][] primitives = object.asAggregate().asMultiArray();
        MultiArrayType multiArrayType = new MultiArrayType(primitives, primitives.length, primitives[0].length);
        return multiArrayType.getValueIndex(indexFirst, indexSecond);
    }


    public void setValueField(final String name, final Type newValue){
        StructBody structBody = value.getBody();
        structBody.setValue(name, newValue);
    }

    public void setValueField(final String name, final Type indexFirst, final Type newValue){
        StructBody structBody = value.getBody();
        structBody.setValue(name, indexFirst, newValue);
    }

    public void setValueField(final String name, final Type indexFirst, final Type indexSecond,  final Type newValue){
        StructBody structBody = value.getBody();
        structBody.setValue(name, indexFirst, indexSecond, newValue);
    }

    @Override
    public Primitive asPrimitive() {
        return null;
    }

    @Override
    public Aggregate asAggregate() {
        return null;
    }

    @Override
    public Struct asStruct() {
        return value;
    }
}
