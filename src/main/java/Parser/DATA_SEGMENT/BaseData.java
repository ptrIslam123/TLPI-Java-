package Parser.DATA_SEGMENT;

import Parser.Type.Integral.IntegerType;
import Parser.Type.PrimitiveType;
import Parser.Type.StructType.Struct.Struct;
import Parser.Type.Types.Type;

import java.util.ArrayList;

public class BaseData {
   private static ArrayList<DataType> objectTable;

    public static void setObjectTable(ArrayList<DataType> objectTable) {
        BaseData.objectTable = objectTable;
    }
    /**
     *  Методы по добавлению объектов на стек
     * @param name
     * @param value
     * @param visibility
     */
    protected static void push(final String name, final Type value, final int visibility){
        int res  = isExists(name);
        if(res != -1)throwException("Multiple character definition",name);
        addDataType(name, value, visibility);
    }

    protected static void push(final String name, Type capasity, ArrayList<Type> init_data, final int visibility){
        int res  = isExists(name);
        if(res != -1)throwException("Multiple character definition",name);
        addDataType(name, capasity, init_data, visibility);
    }
    protected static void push(final String name, Type capasity_1, Type capasity_2, ArrayList<ObjectArray> init_data, final int visibility){
        int res  = isExists(name);
        if(res != -1)throwException("Multiple character definition",name);
        addDataType(name, capasity_1, capasity_2, init_data, visibility);
    }

    /******************************************************
     * ПУШИМ СТРУКТУРНЫЙ ТИП ДАННЫХ И ЕЕ ОБЛАСТЬ ВИИДМОСТИ
     *
     * @param struct
     * @param visibility
     *****************************************************/
    protected static void push(final String name_struct, final String name, final Struct struct, final int visibility){
        int res = isExists(name);
        if(res != -1)throwException("Multiple character definition",name);
        addDataType(name_struct, name, struct, visibility);
    }

    /**
     *  Метод по получению объекта из стека по символьной информацийи
     * @param
     * @return
     */
    protected static DataType getObject(final ArrayList<DataType> arrayData, final String name_obj){
        DataType iterator = null;
        for(int i=0; i<arrayData.size(); i++){
            iterator = arrayData.get(i);
            if(iterator.getName().equalsIgnoreCase(name_obj))return iterator;
        }
        return null;
    }


    /**
     *  Набор вспомогательных методов
     * @param name
     * @return
     */
    protected static int isExists(final String name){
        for(int i=0; i<objectTable.size(); i++){
            if(objectTable.get(i).getName().equalsIgnoreCase(name))return i;
        }
        return -1;
    }
    private static void throwException(final String except_msg, final String name_obj){
        throw new RuntimeException(except_msg+": "+name_obj);
    }

    private static void addDataType(final String name_struct, final String name, final Struct struct, final int visibility){
        objectTable.add(new DataType(name_struct, name, struct, visibility));
    }
    private static void addDataType(final String name, final Type value, final int visibility){
        objectTable.add(new DataType(name, value, visibility));
    }
    private static void addDataType(final String name, Type capasity, ArrayList<Type> init_data, final int visibility){
        if(capasity == null && init_data == null)
            showMessageError("");
        else if(capasity == null){
            capasity = new PrimitiveType(new IntegerType(String.valueOf(init_data.size())));
        }
        objectTable.add(new DataType(name, capasity, init_data, visibility));
    }

    private static void addDataType(final String name, Type capasity_1, Type capasity_2, ArrayList<ObjectArray> init_data, final int visibility){
        if(capasity_1 == null)
            showMessageError("");
        else if(capasity_1 == null && init_data == null)
            showMessageError("");
        else if(capasity_2 == null){
            int length = init_data.get(0).getArray().size();
            capasity_2 = new PrimitiveType(new IntegerType(String.valueOf(length)));
        }
        objectTable.add(new DataType(name, capasity_1, capasity_2, init_data, visibility));
    }

    private static void showMessageError(String msg) {
        throw new RuntimeException(msg);
    }
}
