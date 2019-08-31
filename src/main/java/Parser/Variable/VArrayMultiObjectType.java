package Parser.Variable;

import Parser.DATA_SEGMENT.ObjectType;
import Parser.Type.Type;

public class VArrayMultiObjectType implements ObjectType {
    private String name;
    private int capasity_1, capasity_2;
    private Type[][] this_Array;

    public VArrayMultiObjectType(String name, int capasity_1, int capasity_2, Type[][] array) {
        this.name = name;
        this.capasity_1 = capasity_1;
        this.capasity_2 = capasity_2;
        init_data(array);
    }

    private void init_data(Type[][] array) {
        this_Array = new Type[capasity_1][capasity_2];
        for(int i=0; i<array.length; i++){
            ////////////////////////////////////////////
        }
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
        return new Type[0][];
    }

    @Override
    public String getName() {
        return name;
    }
}
