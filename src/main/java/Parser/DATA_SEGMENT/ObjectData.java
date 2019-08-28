package Parser.DATA_SEGMENT;

import Parser.Type.Type;

import java.security.PublicKey;

public class ObjectData {

    /******************************************  МЕТОДЫ ПО УСТАНОВКЕ НОВЫХ ЗНАЧЕНИЙ ОБЪЕКТОВ *******************************************************/
    public void setValueObject(final String name, final Type newValue){     /** УСТАНОВИТЬ ЗАНЧЕНИЕ ПЕРЕМЕННОЙ **/
        ObjectType object = SegmentData.getObject(name);
        if(object != null){
            ((PrimitiveType) object).setValue(newValue);
            return;
        }
        throw new RuntimeException("Unknown object type: "+name);
    }

    public void setValueObject(final String name, final int index, final Type newValue){    /** УСТАНОВИИТЬ ЗАНЧЕНИЕ МАССИВА ПО ИНДЕКСУ **/
        AggregateType object = (AggregateType) isExists_if_true_getObj(name);
        object.setNewValueArray(index, newValue);
    }

    public void setValueObject(final String name, final int index_1, final int index_2, final Type newValue){    /** УСТАНОВИТЬ ЗАНЧЕНИЕ ДВУМЕРНОГО МАССИВА ПО ИНДЕКСАМ **/
        MultiAggregateType object = (MultiAggregateType) isExists_if_true_getObj(name);
        object.setNewValueMultiArray(index_1, index_2, newValue);
    }



    /******************************************  МЕТОДЫ ПО ПОЛУЧЕНИЙ ЗНАЧЕНИЙ ОБЪЕКТОВ *******************************************************/
    public Type getObject(final String name){  /** ВЕРНУТЬ ЗАНЧЕНИЕ ПЕРЕМЕННОЙ **/
       ObjectType object = isExists_if_true_getObj(name);
       return object.asPrimative();
    }

    public Type getObject(final String name, final int index) {     /** ВЕРНУТЬ ЗАНЧЕНИЕ МАССИВА ПО ИНДЕКСУ **/
        return isExists_if_true_getObj(name).asAggregate()[index];
    }

    public Type getObject(final String name, final int index_1, final int index_2){     /** ВЕРНУТЬ ЗАНЧЕНИЕ ДВУМЕРНОГО МАССИВА ПО ИНДЕКСАМ **/
        return isExists_if_true_getObj(name).asMultiAggregate()[index_1][index_2];
    }




    private ObjectType isExists_if_true_getObj(final String name){  /** ВЕРНУТЬ ОБЪЕКТ ЕСЛИ ОН ЕСТЬ, ЕСЛИ НЕТ ВЫБРОСИТЬ ИСКЛЮЧЕНИЕ **/
        ObjectType object = SteckData.getObject(name);
        if(object != null)return object;
        object = SegmentData.getObject(name);
        if(object != null)return object;
        throw new RuntimeException("Unknown object type: "+name);
    }
}
