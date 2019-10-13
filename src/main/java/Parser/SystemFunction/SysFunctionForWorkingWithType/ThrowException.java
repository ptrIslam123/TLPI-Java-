package Parser.SystemFunction.SysFunctionForWorkingWithType;

import Parser.SystemFunction.SysFuncInterface.BaseInterface;
import Parser.SystemFunction.SysFuncInterface.SysFunction;
import Parser.Type.Types.Primitive;
import Parser.Type.Types.Type;
import java.io.IOException;
import java.util.ArrayList;

public class ThrowException extends BaseInterface implements SysFunction {
    private Primitive msgError;
    @Override
    public void setInputParams(ArrayList<Type> args) {
        verifyInputParams(args.size() == 1, "throw_exception");
        msgError = getPrimitive(args.get(0));
    }

    @Override
    public Type executeBody() throws IOException {
        throw new RuntimeException(msgError.asString());
    }
}
