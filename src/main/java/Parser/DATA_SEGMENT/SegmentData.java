package Parser.DATA_SEGMENT;

import Parser.Type.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SegmentData extends BaseData{
    private static int begin_initialize_size_segment_data = 4;
    private static ArrayList<ObjectType> variables;

    static {
        variables = new ArrayList<ObjectType>(begin_initialize_size_segment_data);

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
        return null;
    }


}
