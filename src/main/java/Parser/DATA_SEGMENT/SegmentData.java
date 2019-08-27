package Parser.DATA_SEGMENT;

import Parser.Type.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SegmentData {
    private static Map<String, ObjectType> variables;
    static {
        variables = new HashMap<String, ObjectType>();
    }
    /***   ИНИЦИАЛИЗАЦИЯ ОБЪЕКТОВ В СЕГМЕНТЕ ДАННЫХ ПО ИХ АТРИБУТАМ     ***/
    public static void newObject(final String name, Type value){    /**  CREATE VARIABLE **/
        if(variables.containsKey(name) != false)throw new RuntimeException("Multiple character definition: " +name);
        variables.put(name, new PrimitiveType(value));
    }

    public static void newObject(final String name, final Type capasity, final List<Type> init_data){   /**  CREATE ARRAY **/
        if(variables.containsKey(name) != false)throw new RuntimeException("Multiple character definition: " +name);
        even(name,capasity, init_data);
    }

    public static void newObject(final String name, final Type capasity1,final Type capasity2, final List<ObjArray> init_data){   /**  CREATE MULTI_ARRAY **/
        if(variables.containsKey(name) != false)throw new RuntimeException("Multiple character definition: " +name);
        even(name, capasity1, capasity1, init_data);
    }


    /***   ПОЛУЧЕНИЕ ОБЪЕКТОВ ИЗ СЕГМЕНТА ДАННЫХ ПО ИХ АТРИБУТАМ     ***/
    public static Type getPrimitive(final String name){  /**   GET VALUE VARIABLE**/
        isExists(name);
        return  variables.get(name).asPrimative();
    }

    public static Type getAggregate(final String name, final int index){    /**   GET VALUE ARRAY**/
        isExists(name);
        return variables.get(name).asAggregate()[index];
    }

    public static Type getPrimitive(final String name, final int index1, final int index2){   /**   GET VALUE MULTI_ARRAY**/
        isExists(name);
        return  variables.get(name).asMultiAggregate()[index1][index2];
    }


    public static ObjectType getObject(final String name){
        isExists(name);
        return variables.get(name);
    }


    private static void isExists(final String name){
        if(variables.containsKey(name) == false)throw new RuntimeException("Unknown object: "+name);
        return;
    }
    public static Map<String, ObjectType> getTable(){ return variables; }

    private static void even(final String name, final Type capasity, final List<Type> init_data){
        if(capasity == null && init_data == null)throw new RuntimeException("Error -> Array size not specified: " + name);
        if(capasity == null){
            variables.put(name, new AggregateType(name, init_data.size(), init_data));
            return;
        }
        if(init_data == null){
            variables.put(name, new AggregateType(capasity.asInt()));
            return;
        }else {
            variables.put(name, new AggregateType(name, capasity.asInt(), init_data));
            return;
        }
    }
    private static void even(final String name, final Type capasity1, final Type capasity2, final List<ObjArray> init_data){
     if(capasity1 == null) throw new RuntimeException("Incorrect Array initialization: "+name);
     if(capasity2 != null && init_data != null){
         variables.put(name, new MultiAggregateType(capasity1.asInt(), capasity2.asInt(), init_data));
         return;
     }
     if(capasity2 == null && init_data != null){
         variables.put(name, new MultiAggregateType(capasity1.asInt(), capasity2.asInt()));
         return;
     }
     if(capasity2 == null && init_data != null){
         variables.put(name, new MultiAggregateType(capasity1.asInt(), init_data.size(), init_data));
         return;
     }
     if(capasity2 != null && init_data == null){
         variables.put(name, new MultiAggregateType(capasity1.asInt(), capasity2.asInt()));
     }
     else throw new RuntimeException("Incorrect Array initialization: "+name);
    }
}
