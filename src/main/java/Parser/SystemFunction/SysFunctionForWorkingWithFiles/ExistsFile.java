package Parser.SystemFunction.SysFunctionForWorkingWithFiles;

import Parser.SystemFunction.SysFuncInterface.BaseInterface;
import Parser.SystemFunction.SysFuncInterface.SysFunction;
import Parser.Type.Integral.BoolType;
import Parser.Type.PrimitiveType;
import Parser.Type.Types.Primitive;
import Parser.Type.Types.Type;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ExistsFile extends BaseInterface implements SysFunction {
    private Primitive fileName;
    @Override
    public void setInputParams(ArrayList<Type> args) {
        verifyInputParams(args.size() == 1, "existsf");
        fileName = getPrimitive(args.get(0));
    }

    @Override
    public Type executeBody() throws IOException {
        File file = new File(fileName.asString());
        return new PrimitiveType(new BoolType(getResultExistsMethod(file)));
    }

    private String getResultExistsMethod(File file) {
        if(file.exists()) return "1";
        else return "0";
    }
}
