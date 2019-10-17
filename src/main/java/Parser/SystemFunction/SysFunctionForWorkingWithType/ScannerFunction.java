package Parser.SystemFunction.SysFunctionForWorkingWithType;

import Parser.SystemFunction.SysFuncInterface.BaseInterface;
import Parser.SystemFunction.SysFuncInterface.Function;
import Parser.Type.Integral.*;
import Parser.Type.PrimitiveType;
import Parser.Type.Types.Primitive;
import Parser.Type.Types.Type;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ScannerFunction extends BaseInterface implements Function {
    private Primitive valueType;
    @Override
    public void setInputParams(ArrayList<Type> args) {
        verifyInputParams(args.size() == 1, "scanner");
        valueType = getPrimitive(args.get(0));
    }

    @Override
    public Type executeBody() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String value = scanner.nextLine();
        String type = asString(valueType);
        return createValueType(value, type);
    }

    private Type createValueType(final String value, final String type) {
        switch (type){
            case "%s" : return new PrimitiveType(new StringType(value));
            case "%d" : return new PrimitiveType(new IntegerType(value));
            case "%f" : return new PrimitiveType(new DoubleType(value));
            case "%c" : return new PrimitiveType(new CharType(value));
            case "%b" : return new PrimitiveType(new BoolType(value));
            default:
                showErrorMsg("Scanner error: ");
        }
        return null;
    }
}
