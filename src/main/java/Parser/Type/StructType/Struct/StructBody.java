package Parser.Type.StructType.Struct;

import Parser.DATA_SEGMENT.BaseData;
import Parser.DATA_SEGMENT.DataType;
import Parser.DATA_SEGMENT.ObjectArray;
import Parser.Type.Types.Type;
import com.sun.deploy.security.ValidationState;

import java.util.ArrayList;

public class StructBody extends BaseData {
    private int begin_initialize_size_struct_fields = 10;
    private ArrayList<DataType> struct_fileds;

    public StructBody(){
        this.struct_fileds = new ArrayList<>(begin_initialize_size_struct_fields);
    }

    public void put(final String name_struct, final String name, final Struct struct, final int visibility){
        setObjectTable(struct_fileds);
        push(name_struct, name, struct, visibility);
    }

    public void put(final String name, final Type value, final int visibility){
        setObjectTable(struct_fileds);
        push(name, value, visibility);
    }

    public void put(final String name, final Type capasity, final ArrayList<Type> init_data, final int visibility){
        setObjectTable(struct_fileds);
        push(name, capasity, init_data, visibility);
    }

    public void put(final String name, final Type capasity_1, final Type capasity_2, final ArrayList<ObjectArray> init_data, final int visibility){
        setObjectTable(struct_fileds);
        push(name, capasity_1, capasity_2, init_data, visibility);
    }


    public void setValue(final String name, final Type newValue){
        DataType iterator = null;
        for(int i=0; i<struct_fileds.size(); i++){
            iterator = struct_fileds.get(i);
            if(iterator.getName().equalsIgnoreCase(name))
                iterator.setNewValue(newValue);
        }
    }

    public void setValue(final String name, final Type indexFirst,  final Type newValue){
        DataType iterator = null;
        for(int i=0; i<struct_fileds.size(); i++){
            iterator = struct_fileds.get(i);
            if(iterator.getName().equalsIgnoreCase(name))
                iterator.setNewValue(indexFirst, newValue);
        }
    }

    public void setValue(final String name, final Type indexFirst, final Type indexSecond,  final Type newValue){
        DataType iterator = null;
        for(int i=0; i<struct_fileds.size(); i++){
            iterator = struct_fileds.get(i);
            if(iterator.getName().equalsIgnoreCase(name))
                iterator.setNewValue(indexFirst, indexSecond, newValue);
        }
    }


    public int getBegin_initialize_size_struct_fields() {
        return begin_initialize_size_struct_fields;
    }

    public ArrayList<DataType> getStruct_fileds() {
        return struct_fileds;
    }

    public void setBegin_initialize_size_struct_fields(int begin_initialize_size_struct_fields) {
        this.begin_initialize_size_struct_fields = begin_initialize_size_struct_fields;
    }
    public DataType getValue(final String name){
        DataType iterator = null;
        for(int i=0; i<struct_fileds.size(); i++){
            iterator = struct_fileds.get(i);
            if(iterator.getName().equalsIgnoreCase(name))return iterator;
        }
        return null;
    }
}
