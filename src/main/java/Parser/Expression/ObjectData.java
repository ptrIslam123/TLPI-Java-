package Parser.Expression;

import Lexer.TypeToken;
import Parser.DATA_SEGMENT.DataType;
import Parser.DATA_SEGMENT.SegmentData;
import Parser.DATA_SEGMENT.SteckData;
import Parser.Type.AggregateType;
import Parser.Type.Integral.StringType;
import Parser.Type.PrimitiveType;
import Parser.Type.StructType.ArrayType;
import Parser.Type.StructType.MultiArrayType;
import Parser.Type.StructType.Struct.Struct;
import Parser.Type.StructType.Struct.StructType;
import Parser.Type.Types.Aggregate;
import Parser.Type.Types.Primitive;
import Parser.Type.Types.Type;

import java.lang.reflect.Array;

public class ObjectData {
    /******************************************
     *  МЕТОДЫ ПО ПОЛУЧЕНИЯ ЗНАЧЕНИЙ ОБЪЕКТОВ
     * @param name_obj
     * @return
     ****************************************/
    public Type getObject(final String name_obj){       // OBJECT {VARIABLE | ARRAY | MULTI_ARRAY| STRUCT
        DataType res_obj = searchObject(name_obj);
        Type res = res_obj.getValue();
        if(res instanceof PrimitiveType){
           return new PrimitiveType(res.asPrimitive());
        }
        else if(res instanceof AggregateType){
            return castAggregareType(res.asAggregate());
        }
        else if(res instanceof StructType){
            return castStructType(res);
        }
        return showErrorMessage();
    }

    private Type castStructType(Type res) {
        StructType structType = (StructType) res;
        return structType;
    }

    /**
     * Метод который определяет текущий объект (являеться ли
     * он массивом или двусерный массивов). В зависимости от того какой
     * это объект мы возвращаем двумерный или одномерный массив объект
     * @param aggregate
     * @return
     */

    private Type castAggregareType(Aggregate aggregate){
        if(aggregate instanceof ArrayType){
            Primitive[] primitives = aggregate.asArray();
            ArrayType arrayType = new ArrayType(primitives, primitives.length);
            return new AggregateType(arrayType);
        }
        else if(aggregate instanceof MultiArrayType){
            Primitive[][] primitives = aggregate.asMultiArray();
            int sizeF = primitives.length, sizeS = primitives[0].length;
            MultiArrayType multiArrayType = new MultiArrayType(primitives, sizeF, sizeS);
            return new AggregateType(multiArrayType);
        }
        else showErrorMessage();
        return null;
    }

    public Type getObject(final String name_obj, final Type index){     // ARRAY
        DataType res_obj = searchObject(name_obj);

        Type value = res_obj.getValue();
        Aggregate aggregate = value.asAggregate();
        if(aggregate instanceof MultiArrayType){
            return getArrayType(aggregate.asMultiArray(), index);
        }

        Primitive[] array = value.asAggregate().asArray();
        return new PrimitiveType(array[index.asPrimitive().asInt()]);
    }

    private Type getArrayType(final Primitive[][] primitives, final Type index) {
        int indexValue = index.asPrimitive().asInt();
        Primitive[] primitive = primitives[indexValue];
        ArrayType arrayType = new ArrayType(primitive, primitive.length);
        return new AggregateType(arrayType);
    }

    public Type getObject(final String name_obj, final Type indexFirst, final Type indexSecond){ //MULTI_ARRAY
        DataType res_obj = searchObject(name_obj);
        Primitive[][] value = res_obj.getValue().asAggregate().asMultiArray();
        int indexF = indexFirst.asPrimitive().asInt(), indexS = indexSecond.asPrimitive().asInt();
        return new PrimitiveType(value[indexF][indexS]);
    }
    /** метод возвращающий структуру по его имени **/
    public StructType getStruct(final String nameStruct){
        DataType dataType = searchObject(nameStruct);
        return new StructType(nameStruct, dataType.getValue().asStruct());
    }

    /***************************************************************
     *      МЕТОДЫ ПО ИЗМЕНЕНИЮ (ПРИСВОЕНИЮ НОВЫХ) ЗНАЧЕНИЙ ОБЪЕКТОВ
     * @param name_obj
     * @param newValue
     **************************************************************/
    public void setNewValue(final String name_obj, final Type newValue){
        DataType dataType = searchObject(name_obj);
        dataType.setNewValue(newValue);
    }
    public void setNewValue(final String name_obj, final Type index, final Type newValue){
        DataType dataType = searchObject(name_obj);
        dataType.setNewValue(index, newValue);
    }
    public void setNewValue(final String name_obj, final Type indexFirst, final Type indexSecond, final Type newValue){
        DataType dataType = searchObject(name_obj);
        dataType.setNewValue(indexFirst, indexSecond, newValue);
    }

    /***  метод для получения конкретного типа объекта  ***/
    /*private DataType getObjectType(final String name_obj, final TypeToken type){
      //////////////////////
        throw new RuntimeException("Unknown object: "+name_obj);
    }
    /*** метод для пойска объекта по его имени ***/
    private DataType searchObject(final String name_obj){
        DataType result = SteckData.getObjectData(name_obj);
        if(result != null)return result;
        result = SegmentData.getObjectData(name_obj);
        if(result != null)return result;
        throw new RuntimeException("Unknown object: "+name_obj);
    }

    private Type showErrorMessage(){
        throw new RuntimeException("Unknown token type{getObject}");
    }
}
