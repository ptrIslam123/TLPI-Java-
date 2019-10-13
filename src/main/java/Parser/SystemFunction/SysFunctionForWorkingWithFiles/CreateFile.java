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

public class CreateFile extends BaseInterface implements SysFunction {
    private Primitive fileName;
    @Override
    public void setInputParams(ArrayList<Type> args) {
        verifyInputParams(args.size() == 1, "createf");
        fileName = getPrimitive(args.get(0));
    }

    @Override
    public Type executeBody() {
        String fName = fileName.asString();
        File newFile = new File(fName);
        try {
            newFile.createNewFile();
        }catch (IOException e){
            showErrorMsg(e.getMessage());
        }
        return new PrimitiveType(new BoolType("1"));
    }

    private PrimitiveType retResult(final String res){
        return new PrimitiveType(new BoolType(res));
    }
}
