package Parser.DATA_SEGMENT;

import Parser.Type.Type;
import java.util.ArrayList;
import java.util.List;

public final class SteckData extends BaseData{
    private static int visibility;
    private static int begin_initialize_size_steck_data = 4;

    private static ArrayList<ObjectType> variables;

    static {
        variables = new ArrayList<ObjectType>(begin_initialize_size_steck_data);
    }

    public static void newObject(final String name, final Type value, final int visibility){
        setVariables(variables);
        push(name,value, visibility);
    }

    public static void newObject(final String name, final Type capasity, final List<Type> init_data, final int visibility){
        setVariables(variables);
        push(name,capasity, init_data, visibility);
    }

    public static void newObject(final String name, final Type capasity_1, final Type capasity_2, final List<ObjArray> init_data, final int visibility){
        setVariables(variables);
        push(name,capasity_1, capasity_2, init_data, visibility);
    }

    public static ObjectType getObject(final String name){
       setVariables(variables);
       return getObj(name);
    }

    public static void deleteObject(final int visibility){
        ObjectType temp = null;
        int i=0;
        while(i<variables.size()){
            temp = variables.get(i);
            if(temp.getVisibility() == visibility){
                variables.remove(i);
                i--;
            }
            i++;
        }
    }
}
