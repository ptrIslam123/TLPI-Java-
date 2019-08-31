package Parser.DATA_SEGMENT;

import Parser.Type.Type;
import Parser.Variable.VArrayObjectType;
import Parser.Variable.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SteckData {
    private static int visibility;

    public static void setVisibility(int visibility) {
        SteckData.visibility = visibility;
    }
    private static int begin_init_size_steck = 10;

    private static ArrayList<ObjectType> variables;

    static {
        variables = new ArrayList<ObjectType>(begin_init_size_steck);
    }

    public static void newObject(final String name, final Type value){
        int res = isExists(name);
        if(res == -1) {
            variables.add(new Variable(name, new PrimitiveType(value), visibility));
        }else
            callException("Repeated character: ",name);
    }

    public static void newObject(final String name, final Type capasity, final List<Type> init_data){
        int res = isExists(name);
        if(res != -1)throw new RuntimeException("Multiple character definition: " +name);
        even(name,capasity, init_data);
    }



    public static ObjectType getObject(final String name){  /********  ВОЗВРАЩАЕТ ОБЪЕКТ ПО ЕГО ИМЕНИ, ЕСЛИ ОБЪЕКТ НЕ НАЙДЕН ВОЗВРАЩВЕТ NULL ********/
        int res = isExists(name);
        if(res == -1)return null;
        return variables.get(res);
    }



    public static void deleteObject(final int _visibility_){
        ObjectType var = null;
       /* for(int i = 0; i<variables.size(); i++){
           var = variables.get(i);
           if(var.getVisibility() == _visibility_){
               variables.remove(i);
               i--;
           }

       }*/
    }

    private static int isExists(final String name){
        for(int i=0; i<variables.size(); i++){
            //if(variables.get(i).getName().equalsIgnoreCase(name))return i;
            if(variables.get(i).getName().equalsIgnoreCase(name))return i;
        }
        return -1;
    }

    private static void callException(final String error_msg, final String name_o){
        throw new RuntimeException(error_msg+name_o);
    }
/*
    private static void even(final String name, final Type capasity, final List<Type> init_data){
        if(capasity == null && init_data == null)throw new RuntimeException("Error -> Array size not specified: " + name);
        if(capasity == null){
            variables.add(new VArrayObjectType(name, init_data.size(), init_data));
            return;
        }
        if(init_data == null){
            variables.add(new VArrayObjectType(name, capasity.asInt()));
            return;
        }else {
            variables.add(new VArrayObjectType(name, capasity.asInt(), init_data));
            return;
        }
    }*/


    private static void even(final String name, final Type capasity, final List<Type> init_data){
        if(capasity == null && init_data == null)throw new RuntimeException("Error -> Array size not specified: " + name);
        if(capasity == null){
            variables.add(new Variable(name, init_data.size(), init_data, visibility));
            return;
        }
        if(init_data == null){
            variables.add(new Variable(name, capasity.asInt(), visibility));
            return;
        }else {
            variables.add(new VArrayObjectType(name, capasity.asInt(), init_data));
            return;
        }
    }
}
