package Parser.Expression;

import Parser.DATA_SEGMENT.*;
import Parser.Type.Type;

/**
 *
 * ДАННЫЙ КЛАСС ПРЕДНАЗНАЧЕН ДЛЯ ПОЛУЧЕНИЙ ОБЪЕКТА ДАННЫХ.
 *ПОЙСК ОБЪЕКТА ОСУЩЕСТВЛЯЕТЬСЯ ПО ЕГО ИМЕНИИ ПОЙСК
 * БУДЕТ В НАЧАЛЕ ОСУЩЕСТВЛЯТЬСЯ НА СТЕКЕ, ЗАТЕМ ЕСЛИ ОБЪЕКТ НЕ БЫЛ ТАМ НАЙДЕН ИЩЕТЬСЯ
 * В СЕГМЕНТЕ ДАННЫХ (СРЕДИ ГЛОБАЛЬНЫХ ДАННЫХ)
 *
 */

public class ObjectExpression implements Expression {
    private String name_obj;
    private int index_1, index_2;
    private byte variable = 0, array = 0, multi_array = 0;

    public void setObject(final String name_obj) {
        this.name_obj = name_obj;
        this.variable = 1;
        this.array = 0;
        this.multi_array = 0;
    }
    public void setObject(final String name_obj, final int index) {
        this.name_obj = name_obj;
        this.variable = 0;
        this.array = 1;
        this.multi_array = 0;
        this.index_1 = index;
    }

    public void setObject(final String name_obj, final int index_1, final int index_2) {
        this.name_obj = name_obj;
        this.variable = 0;
        this.array = 0;
        this.multi_array = 1;
        this.index_1 = index_1;
        this.index_2 = index_2;
    }

    @Override
    public Type eval() {
        ObjectType result = null;
        result = getObjectSteckData();
        if(result != null){
            return ret_object_value(result);
        }
        result = getObjectSegmentData();
        return ret_object_value(result);
    }

    private Type ret_object_value(ObjectType result) {
        if(variable == 1){
            return result.asPrimative();
        }else if(array == 1){
            return result.asAggregate()[index_1];
        }else {
            return result.asMultiAggregate()[index_1][index_2];
        }
    }

    public void setNewValueObject(final String name, final Type newValue){
        ObjectType obj_temp = null;
        obj_temp = SteckData.getObject(name);
        if(obj_temp != null){

        }
        obj_temp = SegmentData.getObject(name);
        if(obj_temp == null)throw new RuntimeException("Unknown user object type: "+name);

    }

    public void setNewValueObject(final String name, final int index, final Type newValue){
        ObjectType obj_temp = null;
        obj_temp = SteckData.getObject(name);
        if(obj_temp != null){
            obj_temp.asAggregate()[index] = newValue;
            return;
        }
        obj_temp = SegmentData.getObject(name);
        obj_temp.asAggregate()[index] = newValue;
    }

    public void setNewValueObject(final String name, final int index_1, final int index_2, final Type newValue){

    }

    public Type getValueObject(final String name){
        ObjectType temp = null;
        temp = SteckData.getObject(name);
        if(temp != null){
            return null;
        }
        temp = SegmentData.getObject(name);
        if(temp != null){
            return returnGetObject(temp,0,0);
        }
        throw new RuntimeException("Unknown object type: " + name);
    }

    private Type returnGetObject(ObjectType temp, final int index_1, final int index_2) {
        if(temp instanceof PrimitiveType){
            return temp.asPrimative();
        }
        if(temp instanceof AggregateType){
            return temp.asAggregate()[index_1];
        }
        if(temp instanceof MultiAggregateType){
            return temp.asMultiAggregate()[index_1][index_2];
        }
        throw new RuntimeException("Error type: " + temp);
    }

    private ObjectType getObjectSegmentData() {
        return SegmentData.getObject(name_obj);
    }

    private ObjectType getObjectSteckData() {
        return null;
    }
}
