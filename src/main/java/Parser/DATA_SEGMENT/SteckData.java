package Parser.DATA_SEGMENT;

import Parser.Type.StructType.Struct.Struct;
import Parser.Type.Types.Type;

import java.util.ArrayList;

public final class SteckData extends BaseData{
    /*** НАЧАЛЬНЫ ЙРАЗМЕР СТЕКА ПОД ОБЪЕКТЫ ***/
    private static int begin_initialize_size_steck_data = 256;
    private static ArrayList<DataType> steckData;
    static {
        steckData = new ArrayList<>(begin_initialize_size_steck_data);
    }

    /**
     * КЛАСС ОТВЕЧАЮЩИЙ ЗА АЛОКАЦИЮ ОБЪЕКТОВ НА СТЕКЕ
     * ИСПОЛЬЗУЕТ МЕТОДЫ NEWOBJECT ДЛЯ ВЫДЕЛЕНИЯ ПАМЯТИ
     *
     * @param name
     * @param value
     * @param visibility
     */
    public static void newObject(final String name, final Type value, final int visibility){
        setObjectTable(steckData);
        push(name,value, visibility);
    }

    public static void newObject(final String name, final Type capasity, final ArrayList<Type> init_data, final int visibility){
        setObjectTable(steckData);
        push(name,capasity, init_data, visibility);
    }

    public static void newObject(final String name, final Type capasity_1, final Type capasity_2, final ArrayList<ObjectArray> init_data, final int visibility){
        setObjectTable(steckData);
        push(name,capasity_1, capasity_2, init_data, visibility);
    }

    public static void newObject(final String name_struct, final String name, final Struct struct, final int visibility){
        setObjectTable(steckData);
        push(name_struct, name, struct, visibility);
    }


    public static DataType getObjectData(final String name){
       return getObject(steckData, name);
    }

    public static void deleteObject(final int visibility){
       DataType temp = null;
       int i=0;
       while(i<steckData.size()){
           temp = steckData.get(i);
           if(temp.getVisibility() == visibility){
               steckData.remove(i);
               i--;
           }
           i++;
       }
    }

    public static ArrayList<DataType> getSteckData() {
        return steckData;
    }
}
