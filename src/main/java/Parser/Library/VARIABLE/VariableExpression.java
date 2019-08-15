package Parser.Library.VARIABLE;

import Parser.Library.Expresssion;
import Parser.TYPE.Type;

public class VariableExpression implements Expresssion {
    private String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Type evalExpression() {
        //if(!VariableTable.isExists(name))throw new RuntimeException("Variable does not exists");
        //return VariableTable.getValue(name);
        return VariableTable.getValue(name);
    }
}

/**
 *  @Override
 *     public Value eval() {
 *         if (!Variables.isExists(name)) throw new RuntimeException("Constant does not exists");
 *         return Variables.get(name);
 *     }
 **/