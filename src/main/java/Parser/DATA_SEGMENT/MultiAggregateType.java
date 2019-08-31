package Parser.DATA_SEGMENT;

import Parser.Type.Type;

import java.util.List;

public class MultiAggregateType implements ObjectType {
    private String name;
    private int capasity_1, capasity_2;
    private int visibility;
    private Type[][] this_Array;

    public MultiAggregateType(final String name, final int capasity_1, final int capasity_2, final List<ObjArray> init_data, final int visibility) {
        this.name = name;
        this.capasity_1 = capasity_1;
        this.capasity_2 = capasity_2;
        this.visibility = visibility;
        init_data(init_data);
    }

    public MultiAggregateType(final String name, final int capasity_1, final int capasity_2, final int visibility) {
        this.name = name;
        this.capasity_1 = capasity_1;
        this.capasity_2 = capasity_2;
        this.visibility = visibility;
        this_Array = new Type[capasity_1][capasity_2];
    }
    private void init_data(List<ObjArray> array) {
        this_Array = new Type[capasity_1][capasity_2];
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
    public int getVisibility() {
        return visibility;
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

    @Override
    public String getName() {
        return name;
    }
}
