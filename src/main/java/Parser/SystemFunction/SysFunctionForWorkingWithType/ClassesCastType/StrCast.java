package Parser.SystemFunction.SysFunctionForWorkingWithType.ClassesCastType;

import Lexer.TypeToken;
import Parser.SystemFunction.SysFuncInterface.Function;
import Parser.Type.Types.Type;
import java.util.ArrayList;

public class StrCast extends BaseClassCastType implements Function {
    private Type valueCast;

    @Override
    public void setInputParams(ArrayList<Type> args) {
        valueCast = args.get(0);
    }

    @Override
    public Type executeBody() {
        return dynamicCastType(TypeToken.Str_cast, valueCast);
    }
}
