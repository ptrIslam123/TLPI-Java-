package Parser.SystemFunction.SysFunctionForWorkingWithThreads;

import Parser.SystemFunction.SysFuncInterface.BaseInterface;
import Parser.SystemFunction.SysFuncInterface.Function;
import Parser.Type.Types.Primitive;
import Parser.Type.Types.Type;
import java.io.IOException;
import java.util.ArrayList;

public class CreateThreadFunction extends BaseInterface implements Function {
    private ThreadFunction threadFunc;
    @Override
    public void setInputParams(ArrayList<Type> args) {
        Primitive nameFunc = getPrimitive(args.get(0));
        args.remove(0);
        threadFunc = new ThreadFunction(asString(nameFunc), args);
    }

    @Override
    public Type executeBody() throws IOException {
        Thread newThread = new Thread(threadFunc);
        newThread.start();
        ////// error!!!
        return threadFunc.getResultExecuteFuncBody();
    }
}
