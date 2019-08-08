package Preprocessor.CALL_EXPRESSION;

import Preprocessor.DEFINE.Defines;

import java.util.List;

public class CallDefine implements CallExpression {
    private StringBuilder constExprName;
    private List<Defines> define_table;

    public CallDefine(final StringBuilder constExprName, final List<StringBuilder> in_param, final List<Defines> define_table) {
        this.constExprName = constExprName;
        this.define_table = define_table;
    }

    public StringBuilder call() {
        return null;
    }
}
