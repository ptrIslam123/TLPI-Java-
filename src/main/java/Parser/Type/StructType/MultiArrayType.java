package Parser.Type.StructType;

import Parser.DATA_SEGMENT.ObjectArray;
import Parser.Type.PrimitiveType;
import Parser.Type.Types.Aggregate;
import Parser.Type.Types.Primitive;
import Parser.Type.Types.Type;

import java.util.ArrayList;

public class MultiArrayType implements Aggregate {
    private Primitive[][] array;
    private int capasity_1, capasity_2;

    public MultiArrayType(ArrayList<ObjectArray> array, int capasity_1, int capasity_2) {
        this.capasity_1 = capasity_1;
        this.capasity_2 = capasity_2;
        init(array);
    }
    public MultiArrayType(Primitive[][] array, int capasity_1, int capasity_2) {
        this.capasity_1 = capasity_1;
        this.capasity_2 = capasity_2;
        init(array);
    }

    private void init(ArrayList<ObjectArray> init_data) {
        this.array = new Primitive[capasity_1][capasity_2];
        if(init_data == null){
            return;
        }
        ObjectArray it = null;
        for(int i=0; i<init_data.size(); i++){
           it = init_data.get(i);
           for(int j=0; j<it.getArray().size(); j++){
               array[i][j] = it.getArray().get(j).asPrimitive();
           }
        }
    }

    private void init(Primitive[][] init_data) {
        this.array = new Primitive[capasity_1][capasity_2];
        if(init_data == null){
            return;
        }
       for(int i=0; i<init_data.length; i++){
           for(int j=0; j<init_data[i].length; j++){
               this.array[i][j] = init_data[i][j];
           }
       }
    }

    public Type getValueIndex(final Type indexFirst, final Type indexSecond){
        int numberIndexFirst, numberIndexSecond;
        numberIndexFirst = indexFirst.asPrimitive().asInt();
        numberIndexSecond = indexSecond.asPrimitive().asInt();
        return new PrimitiveType(array[numberIndexFirst][numberIndexSecond]);
    }

    public void setNewValue(final Type indexFirst, final Type indexSecond, final Type newValue){
        int indexF = indexFirst.asPrimitive().asInt(), indexS = indexSecond.asPrimitive().asInt();
        this.array[indexF][indexS] = newValue.asPrimitive();
    }

    public int getCapasityFirst() {
        return capasity_1;
    }

    public int getCapasitySecond() {
        return capasity_2;
    }

    @Override
    public Primitive[] asArray() {
        return null;
    }

    @Override
    public Primitive[][] asMultiArray() {
        return array;
    }
}
