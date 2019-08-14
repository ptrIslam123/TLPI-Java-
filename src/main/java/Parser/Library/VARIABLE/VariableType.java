package Parser.Library.VARIABLE;

import Parser.Library.Expresssion;
import Parser.TYPE.Type;

public class VariableType implements Expresssion {
    private String name;
    private VariableTable variableTable;
    public VariableType(final String name){
        this.name = name;
        this.variableTable = new VariableTable();
    }

    @Override
    public Type evalExpression() {  /** необходимо вернуть значение переменной по ее символьному имени**/
        return variableTable.getVariable(name).getVariableValue(); /** возвращаем класс типа в котором храниться значение переменной**/
    }
}
