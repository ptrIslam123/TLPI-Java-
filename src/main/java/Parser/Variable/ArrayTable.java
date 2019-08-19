package Parser.Variable;

import Parser.Type.IntegerType;
import Parser.Type.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayTable {
    private static Map<String, Type[]> array_table;
    static {
        array_table = new HashMap<String, Type[]>();
    }

    public static void addArray(final String name, final Type capasity, final List<Type> initialize_data){
        Type[] array = new Type[capasity.asInt()];
        putDataArray(array, initialize_data);
        array_table.put(name, array);
    }   /**     добовление массива в таблицу и инициализация    **/

    public static void addArray(final String name, final List<Type> initialize_data){
        Type[] array = new Type[initialize_data.size()];
        putDataArray(array, initialize_data);
        array_table.put(name, array);
    }   /**     добовление массива в таблицу и инициализация без явного указания размера   **/

    public static void addArray(final String name, final Type capasity){
       Type[] array = new Type[capasity.asInt()];
        array_table.put(name, array);
    }   /** инициализируем массив определенной размерности без начальной инициализаций данных **/
    public static Type getDataIndex(final String name, final Type index){
        if(array_table.containsKey(name) == false)throw new RuntimeException("Undefined Array: "+name);
        int get_index = index.asInt();
        Type[] array = array_table.get(name);
        if(get_index > array.length)throw new RuntimeException("ArrayIndexOutOfBoundsException: "+get_index+" | ArrayName: " + name);
        return array[get_index];
    }   /**     получение элемента массива по его индексу   **/

    private static void putDataArray(final Type[] array, final List<Type> initialize){
        for(int i=0; i<initialize.size(); i++){
            array[i] = initialize.get(i);
        }
    }

    public static void setArrayValue(final String name,final Type index, final Type new_value){
        if(array_table.containsKey(name) == false)throw new RuntimeException("Undefined Array: "+name);
        int get_index = index.asInt();
        Type[] array = array_table.get(name);
        if(get_index > array.length)throw new RuntimeException("ArrayIndexOutOfBoundsException: "+get_index+" | ArrayName: " + name);
        array[get_index] = new_value;
    }

    public static IntegerType getSize(final String name){
        if(array_table.containsKey(name) == false)throw new RuntimeException("Undefined Array: "+name);
        return new IntegerType(String.valueOf(array_table.get(name).length));
    }
    public static Map<String, Type[]> getArrayTable(){ return array_table; }
}
