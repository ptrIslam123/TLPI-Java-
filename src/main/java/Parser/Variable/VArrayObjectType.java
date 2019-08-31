package Parser.Variable;

import Parser.DATA_SEGMENT.ObjectType;
import Parser.Type.Type;

import java.util.List;

public class VArrayObjectType implements ObjectType {
    private String name;
    private int capasity;
    private Type[] this_Array = null;

    public VArrayObjectType(final String name, int capasity, final List<Type> array) {
        if(capasity < array.size())throw new RuntimeException("Out of bounds Array: "+name+" :index["+array.size()+"]");
        this.name = name;
        this.capasity = capasity;
        this.this_Array = new Type[capasity];

        init_data(array);
    }
    public VArrayObjectType(final String name, final int capasity) {
        this.name = name;
        this.capasity = capasity;
        this.this_Array = new Type[capasity];
    }
    private void init_data(List<Type> array) {
        for(int i=0; i<array.size(); i++){
            this.this_Array[i] = array.get(i);
        }
    }
    public void setNewValueArray(final int index, final Type newValue){
        this_Array[index] = newValue;
    }
    @Override
    public String getName() {
        return name;
    }

    public int getCapasity() {
        return capasity;
    }

    public Type[] getThis_Array() {
        return this_Array;
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
}
