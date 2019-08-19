package Parser.Variable;

import Parser.Lib.Statement.Statement;
import Parser.Type.Type;

public final class VariableDeclare implements Statement {
    private String name;
    private Type value;

    public void decalre(String name, Type value) {
        this.name = name;
        this.value = value;
        eval();
    }

    @Override
    public void eval() {
        VariableTable.addVariable(this.name, this.value);
    }
}
