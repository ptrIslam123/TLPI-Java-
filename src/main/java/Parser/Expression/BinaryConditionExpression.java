package Parser.Expression;

import Lexer.TypeToken;
import Parser.Type.Type;

public class BinaryConditionExpression implements Expression {
    private TypeToken operation;
    private Type expr1, expr2;

    public void init(TypeToken operation, Type expr1, Type expr2) {
        this.operation = operation;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public Type eval() {
        return null;
    }
}
