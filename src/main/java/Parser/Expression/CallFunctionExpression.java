package Parser.Expression;

import Parser.Type.Type;
import Lexer.*;
import SEMANTICS_ANALYSIS.Function;
import SEMANTICS_ANALYSIS.FunctionTable;

import java.util.ArrayList;

public class CallFunctionExpression implements Expression {
    private String name;
    private ArrayList<Token> in_param;

    public void init(String name, ArrayList<Token> in_param) {
        this.name = name;
        this.in_param = in_param;
    }

    @Override
    public Type eval() {
        Function function = getFunction(name,in_param.size());

        return null;
    }

    private Function getFunction(final String name, final int size_in_param){
        return FunctionTable.getFunction(name, size_in_param);
    }
}
