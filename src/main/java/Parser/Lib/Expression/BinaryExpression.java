package Parser.Lib.Expression;

import Parser.Type.IntegerType;
import Parser.Type.StringType;
import Parser.Type.Type;

public class BinaryExpression implements Expression{
    private char operation;
    private Type expr1, expr2;

    public void init(char operation, Type expr1, Type expr2) {
        this.operation = operation;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public Type evalExpr() {
        if(expr1 instanceof IntegerType){
            switch (operation){
                case '*' : return new IntegerType(String.valueOf(expr1.asInt() * expr2.asInt()));
                case '/' : return new IntegerType(String.valueOf(expr1.asInt() / expr2.asInt()));
                case '-' : return new IntegerType(String.valueOf(expr1.asInt() - expr2.asInt()));
                default:
                    return new IntegerType(String.valueOf(expr1.asInt() + expr2.asInt()));
            }
        }
        if(expr1 instanceof StringType){
            switch (operation){
                case '+' : return new StringType(expr1.asString() + expr2.asString());
                default:
                    throw new RuntimeException("Unknown token type");
            }
        }
        throw new RuntimeException("Unknown operation type");
    }
}
