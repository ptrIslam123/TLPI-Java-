package Parser.DATA_SEGMENT;

import Parser.Type.AggregateType;
import Parser.Type.StructType.ArrayType;
import Parser.Type.StructType.MultiArrayType;
import Parser.Type.StructType.Struct.Struct;
import Parser.Type.StructType.Struct.StructType;
import Parser.Type.Types.Type;
import java.util.ArrayList;

public class DataType {
    private String name;
    private Type value;
    private int visibility;
    /**
     * создание и инициализация
     * структурного типа данных
     * **/
    public DataType(final String name_struct, final String name, final Struct struct, final int visibility){
        value = new StructType(name,struct);
        this.visibility = visibility;
        this.name = name;
    }
    /**
     * создание и инициализация
     * переменной
     * **/
    public DataType(final String name, final Type value, final int visibility) {
        this.name = name;
        this.value = value;
        this.visibility = visibility;
    }
    /**
     * создание и инициализация
     * агрегирующего типа данных(одномерный массив)
     * **/
    public DataType(final String name, final Type capasity, final ArrayList<Type> init_data, final int visibility) {
        this.name = name;
        this.visibility = visibility;
        this.value = new AggregateType(new ArrayType(init_data, capasity.asPrimitive().asInt()));
    }
    /**
     * создание и инициализация
     * агрегирующего типа данных(двухмерный массив)
     * **/
    public DataType(final String name, final Type capasity_1, final Type capasity_2, final ArrayList<ObjectArray> init_data, final int visibility){
        this.name = name;
        this.visibility = visibility;
        this.value = new AggregateType(new MultiArrayType(init_data, capasity_1.asPrimitive().asInt(), capasity_2.asPrimitive().asInt()));
    }

    /*******************************************
     *  УСТАНОВЛЕНИЕ НОВЫХ ЗНАЧЕНИЙ ОБЪЕКТОВ В
     *  РЕЗУЛЬТАТЕ ВЫЧИСЛЕНИЙ
     * @param newValue
     *****************************************/
    public void setNewValue(final Type newValue) {
        this.value = newValue;
    }
    public void setNewValue(final Type index, final Type newValue) {
        ArrayType array = (ArrayType) value.asAggregate();
        array.setNewValue(index, newValue);
    }
    public void setNewValue(final Type indexFirst, final Type indexSecond, final Type newValue) {
        MultiArrayType multiArray = (MultiArrayType) value.asAggregate();
        multiArray.setNewValue(indexFirst, indexSecond, newValue);
    }

     /*********************************************/
    /*******     ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ     *****/
    public String getName() {
        return name;
    }

    public Type getValue() {
        return value;
    }

    public int getVisibility() {
        return visibility;
    }
}
