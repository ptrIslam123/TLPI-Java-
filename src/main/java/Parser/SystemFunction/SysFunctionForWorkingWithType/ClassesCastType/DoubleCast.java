package Parser.SystemFunction.SysFunctionForWorkingWithType.ClassesCastType;

import Lexer.TypeToken;
import Parser.SystemFunction.SysFuncInterface.SysFunction;
import Parser.Type.Types.Type;

import java.util.ArrayList;

public class DoubleCast extends BaseClassCastType implements SysFunction {
    private Type valueCast;
    @Override
    public void setInputParams(ArrayList<Type> args) {
        valueCast = args.get(0);
    }

    @Override
    public Type executeBody() {
        return dynamicCastType(TypeToken.Double_cast, valueCast);
    }
}
