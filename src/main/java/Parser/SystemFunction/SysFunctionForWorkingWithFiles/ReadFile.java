package Parser.SystemFunction.SysFunctionForWorkingWithFiles;

import Parser.SystemFunction.SysFuncInterface.BaseInterface;
import Parser.SystemFunction.SysFuncInterface.Function;
import Parser.Type.Integral.StringType;
import Parser.Type.PrimitiveType;
import Parser.Type.Types.Primitive;
import Parser.Type.Types.Type;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile extends BaseInterface implements Function {
    private Primitive fileName;

    @Override
    public void setInputParams(ArrayList<Type> args) {
        verifyInputParams(args.size() == 1, "readf");
        fileName = getPrimitive(args.get(0));
    }


    @Override
    public Type executeBody() {
        String fName = fileName.asString();
        String data = readFile(fName);
        return new PrimitiveType(new StringType(data));
    }

    private String readFile(final String file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String fileData = br.readLine();
            br.close();
            return fileData;
        }catch (IOException e){
           showErrorMsg("");
        }
        return null;
    }
}
