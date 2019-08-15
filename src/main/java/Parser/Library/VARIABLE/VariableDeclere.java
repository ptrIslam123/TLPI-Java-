package Parser.Library.VARIABLE;

import Parser.Library.Statement;
import Parser.TYPE.Type;

public class VariableDeclere implements Statement {
    private String name;
    private Type value;

    public VariableDeclere(String name, Type value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void execute() {
        VariableTable.putVariable(name, value);
    }
}
