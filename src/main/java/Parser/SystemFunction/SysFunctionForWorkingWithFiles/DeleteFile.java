package Parser.SystemFunction.SysFunctionForWorkingWithFiles;

import Parser.SystemFunction.SysFuncInterface.BaseInterface;
import Parser.SystemFunction.SysFuncInterface.SysFunction;
import Parser.Type.Integral.BoolType;
import Parser.Type.PrimitiveType;
import Parser.Type.Types.Primitive;
import Parser.Type.Types.Type;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class DeleteFile extends BaseInterface implements SysFunction {
    private Primitive fileName;
    @Override
    public void setInputParams(ArrayList<Type> args) {
       verifyInputParams(args.size() == 1, "deletef");
       fileName = getPrimitive(args.get(0));
    }

    @Override
    public Type executeBody() {
        File file = new File(fileName.asString());
        if(file.exists()){
            boolean resDelete = file.delete();
            return retResult(removeFileREsult(resDelete));
        }else
            showErrorMsg("file not found: "+file);
        return retResult("0");
    }

    private PrimitiveType retResult(final String res){
        return new PrimitiveType(new BoolType(res));
    }
    private String removeFileREsult(final boolean res){
        if(res)return "1";
        return "0";
    }
}
