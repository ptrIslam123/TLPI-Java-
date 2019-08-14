package Parser.Library.VARIABLE;

import Parser.Library.Statement;
import Parser.TYPE.Type;


public class VariableDeclaration implements Statement {
    private String variable_name;
    private Type type;
    private VariableTable variableTable;

    public VariableDeclaration(String variable_name, Type type) {
        this.variable_name = variable_name;
        this.type = type;
        this.variableTable = new VariableTable();
    }

    @Override
    public void execute() {
        variableTable.pustVariable(new Variable(variable_name, type));
    }   /** добавляем в таблицу новую переменную**/
}
