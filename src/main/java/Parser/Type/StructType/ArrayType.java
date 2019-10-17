package Parser.Type.StructType;

import Parser.Type.Integral.IntegerType;
import Parser.Type.PrimitiveType;
import Parser.Type.Types.Aggregate;
import Parser.Type.Types.Primitive;
import Parser.Type.Types.Type;

import java.util.ArrayList;

public class ArrayType implements Aggregate {
    private Primitive[] array;
    private int capasity;

    public ArrayType(ArrayList<Type> array, int capasity) {
        this.capasity = capasity;
        init(array);
    }
    public ArrayType(Primitive[] array, int capasity) {
        this.capasity = capasity;
        init(array);
    }

    private void init(ArrayList<Type> init_data) {
        if(init_data == null){
            array = new Primitive[capasity];
            return;
        }
        this.array = new Primitive[capasity];
        for(int i=0; i<init_data.size(); i++){
            array[i] = init_data.get(i).asPrimitive();
        }
    }
    private void init(Primitive[] init_data) {
        if(init_data == null){
            array = new Primitive[capasity];
            return;
        }
        this.array = new Primitive[capasity];
        for(int i=0; i<init_data.length; i++){
            array[i] = init_data[i];
        }
    }

    public Type getValueIndex(final Type index){
        int numberData = index.asPrimitive().asInt();
        return new PrimitiveType(array[numberData]);
    }
    public void setNewValue(final Type index, final Type newValue) {
        this.array[index.asPrimitive().asInt()] = newValue.asPrimitive();
    }

    public Type getLength(){
        return new PrimitiveType(new IntegerType(String.valueOf(array.length)));
    }
    @Override
    public Primitive[] asArray() {
        return array;
    }

    @Override
    public Primitive[][] asMultiArray() {
        return null;
    }

    @Override
    public Type getLengthArray() {
        for(int i=0; i<array.length; i++){
            if(array[i] == null){
                return new PrimitiveType(new IntegerType(String.valueOf(i)));
            }
        }
      return new PrimitiveType(new IntegerType(String.valueOf(capasity)));

    }
    /** проверка выхода за границы массива **/
   /** private void verification_size_capasity(final int capasity_1, final int capasity_2){
        if(capasity_1 < capasity_2)throw new RuntimeException("Out of bounds Array");
    }**/
}
