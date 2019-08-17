package Parser.Variable;

import Parser.Lib.Statement;;
import Parser.Type.Type;

public final class VariableDeclare implements Statement {
    private String name;
    private Type value;

    public VariableDeclare(String name, Type value) {
        this.name = name;
        this.value = value;
        execute();
    }

    @Override
    public void execute() {
        VariableTable.addVariable(this.name, this.value);
    }
}
