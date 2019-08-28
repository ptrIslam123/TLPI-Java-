package Parser.DATA_SEGMENT;

import Parser.Type.Type;

import java.util.List;

public class MultiAggregateType implements ObjectType {
    private final int capasity1, capasity2;
    private Type[][] this_Array = null;

    public MultiAggregateType(int capasity1, int capasity2, final List<ObjArray> array) {
        this.capasity1 = capasity1;
        this.capasity2 = capasity2;
        this.this_Array = new Type[capasity1][capasity2];
        init_data(array);
    }

    public MultiAggregateType(int capasity1, int capasity2) {
        this.capasity1 = capasity1;
        this.capasity2 = capasity2;
        this.this_Array = new Type[capasity1][capasity2];
    }

    private void init_data(List<ObjArray> array) {
        ObjArray temp = null;
        for(int i=0; i<array.size(); i++){
            temp = array.get(i);
            for(int j=0; j<temp.getSize(); j++){
                this.this_Array[i][j] = temp.getArray().get(j);
            }
        }
    }

    public void setNewValueMultiArray(final int index_1, final int index_2, final Type newValue){
        this_Array[index_1][index_2] = newValue;
    }

    @Override
    public Type asPrimative() {
        return null;
    }

    @Override
    public Type[] asAggregate() {
        return null;
    }

    @Override
    public Type[][] asMultiAggregate() {
        return this_Array;
    }
}
