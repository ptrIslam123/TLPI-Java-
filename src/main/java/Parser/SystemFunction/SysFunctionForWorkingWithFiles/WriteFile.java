package Parser.SystemFunction.SysFunctionForWorkingWithFiles;

import Parser.SystemFunction.SysFuncInterface.BaseInterface;
import Parser.SystemFunction.SysFuncInterface.SysFunction;
import Parser.Type.Integral.BoolType;
import Parser.Type.PrimitiveType;
import Parser.Type.Types.Primitive;
import Parser.Type.Types.Type;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WriteFile extends BaseInterface implements SysFunction {
    private Primitive fileName;
    private Type readData;
    @Override
    public void setInputParams(ArrayList<Type> args) {
        verifyInputParams(args.size() == 2, "writef");
        fileName = getPrimitive(args.get(0));
        readData = args.get(1);
    }

    @Override
    public Type executeBody() throws FileNotFoundException {
        String fName = fileName.asString();
        Primitive data = getPrimitive(readData);
       return writeFileData(fName, data);
    }

    private Type writeFileData(final String fName, final Primitive data) throws FileNotFoundException {
      File file = new File(fName);
      if(!file.exists()){
          return retResult("0");
      }else {
          write(file, data);
          return retResult("1");
      }
    }

    private void write(final File file, final Primitive data) throws FileNotFoundException {
        PrintWriter out  = new PrintWriter(file.getAbsoluteFile());
        out.print(data.asString());
        out.close();
    }

    private PrimitiveType retResult(final String res){
        return new PrimitiveType(new BoolType(res));
    }
}
