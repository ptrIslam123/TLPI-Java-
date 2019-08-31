package Parser.DATA_SEGMENT;

import Parser.Type.Type;

import java.util.List;

public class AggregateType implements ObjectType {
    private String name; /****/
    private int capasity;
    private Type[] this_Array = null;

    public AggregateType(final String name, int capasity, final List<Type> array) {
        if(capasity < array.size())throw new RuntimeException("Out of bounds Array: "+name+" :index["+array.size()+"]");
        this.capasity = capasity;
        this.this_Array = new Type[capasity];

        init_data(array);
    }

    public void setNewValueArray(final int index, final Type newValue){
        this_Array[index] = newValue;
    }

    private void init_data(List<Type> array) {
        for(int i=0; i<array.size(); i++){
            this.this_Array[i] = array.get(i);
        }
    }

    public AggregateType(final int capasity) {
        this.capasity = capasity;
        this.this_Array = new Type[capasity];
    }
    /****/
    public AggregateType(final String name, final int capasity, final int visibility) {
        this.capasity = capasity;
        this.name = name;
        this.this_Array = new Type[capasity];
    }
    /****/
/**
    public Type getIndexValue(final int index){
        return this_Array[index];
    }
**/
    @Override
    public Type asPrimative() {
        return null;
    }

    @Override
    public Type[] asAggregate() {
        return this_Array;
    }

    @Override
    public Type[][] asMultiAggregate() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }


    public void init(final String name, int _capasity_, final List<Type> array) {
        if(capasity < array.size())throw new RuntimeException("Out of bounds Array: "+name+" :index["+array.size()+"]");
        capasity = _capasity_;
        this.this_Array = new Type[capasity];
        init_data(array);
    }
}
