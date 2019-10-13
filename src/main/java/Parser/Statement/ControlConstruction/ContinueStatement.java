package Parser.Statement.ControlConstruction;

import Parser.Statement.Statement;

public class ContinueStatement extends RuntimeException implements Statement {
    @Override
    public void execute() {
        throw this;
    }
}
