package Parser.SystemFunction.SysFuncInterface;

import Parser.Type.ParserClasses.Parser;
import Parser.Type.Types.Type;
import SemanticsAnalyzer.Functions.DefFunction;

import java.util.ArrayList;

public class UserFunction extends Parser implements Function {
    private DefFunction defFunction;
    private Type resultValue;

    public UserFunction(final DefFunction defFunction, final Type resultValueExecuteFunc) {
        this.defFunction = defFunction;
        this.resultValue = resultValueExecuteFunc;
    }

    @Override
    public void setInputParams(ArrayList<Type> args) {}

    @Override
    public Type executeBody() {
        return resultValue;
    }

}
