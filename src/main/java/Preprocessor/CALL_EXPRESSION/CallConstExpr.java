package Preprocessor.CALL_EXPRESSION;

import Preprocessor.CONST.Const;
import java.util.List;

public class CallConstExpr implements CallExpression {
    private StringBuilder constExprName;
    private List<Const> const_table;

    public CallConstExpr(StringBuilder constExprName, final List<Const> const_table) {
        this.constExprName = constExprName;
        this.const_table = const_table;
    }

    @Override
    public StringBuilder call() {
        Const constExpe = getValue();
        return constExpe.getConstValue();
    }

    private Const getValue(){
        Const temp = null;
        for(int i=0; i<const_table.size(); i++){
            temp = const_table.get(i);
            if(equalsStr(temp.getConstName(), constExprName) == true)return temp;
        }
        throw new RuntimeException("NO DEFINITION FOUND: "+constExprName);
    }
    private boolean equalsStr(final StringBuilder expr1, final StringBuilder expr2){
        String ex1 = expr1.toString();
        String ex2 = expr2.toString();
        if(ex1.equalsIgnoreCase(ex2))return true;
        else return false;
    }
}
