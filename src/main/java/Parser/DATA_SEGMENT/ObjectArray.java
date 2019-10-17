package Parser.DATA_SEGMENT;

import Parser.Type.Types.Type;

import java.util.ArrayList;

public class ObjectArray {
    private ArrayList<Type> array;

    public ObjectArray() {
        this.array = new ArrayList<>();
    }

    public void add(final Type value){
        array.add(value);
    }

    public ArrayList<Type> getArray() {
        return array;
    }
    public int getLengthArray(){ return array.size(); }
}
