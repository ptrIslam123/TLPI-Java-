package Parser.SystemFunction.SysFunctionForWorkingWithType;

import Parser.SystemFunction.SysFuncInterface.BaseInterface;
import Parser.SystemFunction.SysFuncInterface.SysFunction;
import Parser.SystemFunction.SysFunctionForWorkingWithType.ClassesCastType.BaseClassCastType;
import Parser.Type.AggregateType;
import Parser.Type.Integral.IntegerType;
import Parser.Type.PrimitiveType;
import Parser.Type.StructType.ArrayType;
import Parser.Type.Types.Aggregate;
import Parser.Type.Types.Primitive;
import Parser.Type.Types.Type;

import java.io.IOException;
import java.util.ArrayList;

public class LengthArray extends BaseInterface implements SysFunction{
    private Aggregate array;
    @Override
    public void setInputParams(ArrayList<Type> args) {
        verifyInputParams(args.size() == 1, "length");
        array = getAggregate(args.get(0));
    }

    @Override
    public Type executeBody() throws IOException {
        Primitive[] primitives = array.asArray();
        String length = String.valueOf(primitives.length);
        return new PrimitiveType(new IntegerType(length));
    }
}
