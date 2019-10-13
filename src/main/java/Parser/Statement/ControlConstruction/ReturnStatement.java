package Parser.Statement.ControlConstruction;

import Parser.Statement.Statement;
import Parser.Type.Types.Type;

public class ReturnStatement extends RuntimeException implements Statement {
    private Type retValue;

    public Type getRetValue() {
        return retValue;
    }

    public void setRetValue(Type retValue) {
        this.retValue = retValue;
    }

    @Override
    public void execute() {
        throw this;
    }
}
