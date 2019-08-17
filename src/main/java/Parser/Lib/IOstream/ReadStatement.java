package Parser.Lib.IOstream;

import Parser.Lib.Statement;
import Parser.Type.Type;

public class ReadStatement implements Statement {
    private Type expression;

    public void stream(Type expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        System.out.println(expression.asString());
    }
}
