package Parser.SystemFunction.SysFunctionForWorkingWithType;

import Parser.SystemFunction.SysFuncInterface.BaseInterface;
import Parser.SystemFunction.SysFuncInterface.Function;
import Parser.Type.Types.Aggregate;
import Parser.Type.Types.Type;
import java.io.IOException;
import java.util.ArrayList;

public class LengthArray extends BaseInterface implements Function {
    private Aggregate array;
    @Override
    public void setInputParams(ArrayList<Type> args) {
        verifyInputParams(args.size() == 1, "length");
        array = getAggregate(args.get(0));
        /*if(array != null)return;
        array = getMultiArrayType(args.get(0));
        if(array == null)showErrorMsg("not true, the signature of the function parameters: length()");
        */
    }

    @Override
    public Type executeBody() throws IOException {
        return array.getLengthArray();
    }
}
