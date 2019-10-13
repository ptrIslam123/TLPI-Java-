package Parser.DATA_SEGMENT;

import Parser.Type.StructType.Struct.Struct;
import Parser.Type.Types.Type;
import java.util.ArrayList;

public final class SegmentData extends BaseData{
    private static int begin_initialize_size_segment_data = 4;
    private static ArrayList<DataType> segmentData;
    static {
        segmentData = new ArrayList<>(begin_initialize_size_segment_data);
    }

    /**
     *  КЛАСС ОТВЕЧАЮЩИЙ ЗА АЛОКАЦИЮ ОБЪЕКТОВ В СЕГМЕНТЕ ДАННЫХ
     *  ИСПОЛЬЗУЕТ МЕТОДЫ NEWOBJECT ДЛЯ ВЫДЕЛЕНИЯ ПАМЯТИ
     *
     * @param name
     * @param value
     * @param visibility
     */
    public static void newObject(final String name, final Type value, final int visibility){
        setObjectTable(segmentData);
        push(name, value, visibility);
    }

    public static void newObject(final String name, final Type capasity, final ArrayList<Type> init_data, final int visibility){
        setObjectTable(segmentData);
        push(name, capasity, init_data, visibility);
    }

    public static void newObject(final String name, final Type capasity_1, final Type capasity_2, final ArrayList<ObjectArray> init_data, final int visibility){
        setObjectTable(segmentData);
        push(name, capasity_1, capasity_2, init_data, visibility);
    }

    public static void newObject(final String name_struct, final String name, final Struct struct, final int visibility){
        setObjectTable(segmentData);
        push(name_struct, name, struct, visibility);
    }

    public static DataType getObjectData(final String name){
        return getObject(segmentData, name);
    }

    public static ArrayList<DataType> getSegmentData() {
        return segmentData;
    }
}
