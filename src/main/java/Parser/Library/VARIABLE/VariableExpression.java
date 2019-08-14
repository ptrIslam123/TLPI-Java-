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
        if(VariableStatement.isExists(name)) throw new RuntimeException("Variable does not exists");
        return VariableStatement.getValueVariable(name);
    }
}

/**
 * if (!Variables.isExists(name)) throw new RuntimeException("Constant does not exists");
 * return Variables.get(name);
 *
 *
 *  public static boolean isExists(String key) {
 *      return variables.containsKey(key);
 *  }
 **/