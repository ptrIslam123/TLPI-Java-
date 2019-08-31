package Parser.DATA_SEGMENT;

import Parser.Type.Type;

import java.util.List;

public class AggregateType implements ObjectType {
    private String name;
    private int capasity;
    private int visibility;
    private Type[] this_Array = null;

    public AggregateType(final String name, final int capasity, final List<Type> init_data, final int visibility) {
        this.name = name;
        this.capasity = capasity;
        this.visibility = visibility;
        init_data(init_data);
    }

    public AggregateType(final String name, final int capasity, final int visibility) {
        this.name = name;
        this.capasity = capasity;
        this.visibility = visibility;
        this_Array = new Type[capasity];
    }

    private void init_data(List<Type> array) {
        this_Array = new Type[capasity];
        for(int i=0; i<array.size(); i++){
            this_Array[i] = array.get(i);
        }
    }
    public void setNewValueArray(final int index, final Type newValue){
        this_Array[index] = newValue;
    }
    @Override
    public int getVisibility() {
        return visibility;
    }

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
        return name;
    }
}
