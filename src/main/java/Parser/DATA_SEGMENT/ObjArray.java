package Parser.DATA_SEGMENT;

import Parser.Type.Type;

import java.util.ArrayList;
import java.util.List;

public class ObjArray {
    private List<Type> array;

    public ObjArray() {
        this.array = new ArrayList<Type>();
    }

    public void add(final Type element){
        array.add(element);
    }

    public List<Type> getArray() {
        return array;
    }

    public int getSize(){ return array.size(); }
}
