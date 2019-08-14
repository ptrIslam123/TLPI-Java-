package Parser.Library.VARIABLE;

import Parser.Library.Statement;
import Parser.TYPE.Type;

public class VariableDeclare implements Statement {
    private String name;
    private Type value;

    public VariableDeclare(String name, Type value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void execute() {
        VariableStatement.putValueVariable(name, value);
    }
}
