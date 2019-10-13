package Parser.Statement.ControlConstruction;

import Parser.Statement.Statement;

public class BreakStatement extends RuntimeException implements Statement {
    @Override
    public void execute() {
        throw this;
    }
}
