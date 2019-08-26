package Parser.DATA_SEGMENT;

import Parser.Type.Type;

import java.util.List;

public class AggregateType implements ObjectType {
    private final int capasity;
    private Type[] this_Array = null;

    public AggregateType(final String name, int capasity, final List<Type> array) {
        if(capasity < array.size())throw new RuntimeException("Out of bounds Array: "+name+" :index["+array.size()+"]");
        this.capasity = capasity;
        this.this_Array = new Type[capasity];

        init_data(array);
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

    public Type getIndexValue(final int index){
        return this_Array[index];
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
