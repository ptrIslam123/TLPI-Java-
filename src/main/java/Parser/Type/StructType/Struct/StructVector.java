package Parser.Type.StructType.Struct;

import java.util.ArrayList;

public class StructVector {
    private static int begin_size_structs = 10;
    private static ArrayList<Struct> structs;
    static {
        structs = new ArrayList<>(begin_size_structs);
    }

    public static void declareStruct(final String name, final StructBody body){
        addStruct(name, body);
    }

    public static Struct getStruct(final String name_struct){
        Struct it = null;
        for(int i=0; i<structs.size(); i++){
            it = structs.get(i);
            if(it.getName().equalsIgnoreCase(name_struct))return it;
        }
        return null;
    }

    private static void addStruct(String name, StructBody body) {
        structs.add(new Struct(name, body));
    }

    public static void setBegin_size_structs(int begin_size_structs) {
        StructVector.begin_size_structs = begin_size_structs;
    }

    public static int getBegin_size_structs() {
        return begin_size_structs;
    }

    public static ArrayList<Struct> getStructs() {
        return structs;
    }
}
