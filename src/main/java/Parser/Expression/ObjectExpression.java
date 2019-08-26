package Parser.Expression;

import Parser.DATA_SEGMENT.AggregateType;
import Parser.DATA_SEGMENT.ObjectType;
import Parser.DATA_SEGMENT.SegmentData;
import Parser.DATA_SEGMENT.SteckData;
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
    /**
    public ObjectType getObject(final String name){
         ObjectType temp = null;
         temp = SteckData.getObject(name);
         if(temp != null)return temp;
         else SegmentData.getObject(name);
         return null;
    }
    **/
    private ObjectType getObjectSegmentData() {
        return SegmentData.getObject(name_obj);
    }

    private ObjectType getObjectSteckData() {
        return null;
    }
}
