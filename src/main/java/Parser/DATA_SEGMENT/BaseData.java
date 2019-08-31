package Parser.DATA_SEGMENT;

import Parser.Type.Type;
import java.util.ArrayList;
import java.util.List;

public class BaseData {
    private static ArrayList<ObjectType> variables;

    public static void setVariables(ArrayList<ObjectType> variables) {
        BaseData.variables = variables;
    }

    protected static void push(final String name, final Type value, final int visibility){
        int res = isExists(name);
        if(res != -1)throwException("Multiple character",name);
        variables.add(new PrimitiveType(name, value, visibility));
    }

    protected static void push(final String name, final Type capasity, final List<Type> init_data, final int visibility){
        int res = isExists(name);
        if(res != -1)throwException("Multiple character",name);
        even(name, capasity, init_data, visibility);
    }

    protected static void push(final String name, final Type capasity_1, final Type capasity_2, final List<ObjArray> init_data, final int visibility){
        int res = isExists(name);
        if(res != -1)throwException("Multiple character",name);
        even(name, capasity_1, capasity_2, init_data, visibility);
    }





    public static ObjectType getObj(final String name){
        ObjectType iterator = null;
        for(int i=0; i<variables.size(); i++){
            iterator = variables.get(i);
            if(iterator.getName().equalsIgnoreCase(name))return iterator;
        }
        return null;
    }



    protected static void even(final String name, final Type capasity, final List<Type> init_data, final int visibility){
        if(capasity == null && init_data == null)throw new RuntimeException("Error -> Array size not specified: " + name);
        if(capasity == null){
            variables.add(new AggregateType(name, init_data.size(), init_data, visibility));
            return;
        }
        if(init_data == null){
            variables.add(new AggregateType(name, capasity.asInt(), visibility));
            return;
        }else {
            variables.add(new AggregateType(name, capasity.asInt(), init_data, visibility));
            return;
        }
    }
    protected static void even(final String name, final Type capasity1, final Type capasity2, final List<ObjArray> init_data, final int visibility){
        if(capasity1 == null) throw new RuntimeException("Incorrect Array initialization: "+name);
        if(capasity2 != null && init_data != null){
            variables.add(new MultiAggregateType(name, capasity1.asInt(), capasity2.asInt(), init_data, visibility));
            return;
        }
        if(capasity2 == null && init_data != null){
            variables.add(new MultiAggregateType(name, capasity1.asInt(), init_data.size(), init_data, visibility));
            return;
        }
        if(capasity2 != null && init_data == null){
            variables.add(new MultiAggregateType(name, capasity1.asInt(), capasity2.asInt(), visibility));
        }
        else throw new RuntimeException("Incorrect Array initialization: "+name);
    }

    protected static int isExists(final String name){
        for(int i=0; i<variables.size(); i++){
            if(variables.get(i).getName().equalsIgnoreCase(name))return i;
        }
        return -1;
    }
    protected static void throwException(final String except_msg, final String name_obj){
        throw new RuntimeException(except_msg+": "+name_obj);
    }
}
