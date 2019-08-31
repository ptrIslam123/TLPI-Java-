package Parser.Variable;

import Parser.DATA_SEGMENT.AggregateType;
import Parser.DATA_SEGMENT.ObjectType;
import Parser.Type.Type;

import java.util.List;

public class Variable implements ObjectType{
    private String name;
    private int visibility;
    private ObjectType value;

    private int capasity_1, capasity_2;

    public Variable(final String name, final ObjectType value, final int visibility) {
        this.name = name;
        this.value = value;
        this.visibility = visibility;
    }
  /****/
    public Variable(final String name, final int capasity, final List<Type> init_data, final int visibility) {
        this.name = name;
        this.visibility = visibility;
        this.capasity_1 = capasity;
        AggregateType this_Array = (AggregateType) value;
        this_Array.init(name,capasity_1, init_data);
    }

    public Variable(String name, final int capasity, int visibility) {
        this.name = name;
        this.visibility = visibility;
        AggregateType this_Array = (AggregateType) value;
        this_Array.init(name,capasity_1, visibility);
    }

    public int getVisibility() {
        return visibility;
    }

    private void init_data(List<Type> array) {


    }
    /*****/

    public ObjectType getValue() {
        return value;
    }
    @Override
    public String getName() {
        return name;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public void setValue(ObjectType value) {
        this.value = value;
    }

    @Override
    public Type asPrimative() {
        return value.asPrimative();
    }

    @Override
    public Type[] asAggregate() {
        return value.asAggregate();
    }

    @Override
    public Type[][] asMultiAggregate() {
        return value.asMultiAggregate();
    }
}
