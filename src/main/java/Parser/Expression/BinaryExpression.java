package Parser.Expression;

import Parser.Type.*;

public class BinaryExpression implements Expression {
    private char operation;
    private Type expr1, expr2;

    public void init(char operation, Type expr1, Type expr2) {
        this.operation = operation;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public Type eval() {
        if(expr1 instanceof IntegerType){
            switch (operation){
                case '*' : return new IntegerType(String.valueOf(expr1.asInt() * expr2.asInt()));
                case '/' : return new IntegerType(String.valueOf(expr1.asInt() / expr2.asInt()));
                case '-' : return new IntegerType(String.valueOf(expr1.asInt() - expr2.asInt()));
                default :
                    return new IntegerType(String.valueOf(expr1.asInt() + expr2.asInt()));
            }
        }
        if(expr1 instanceof DoubleType){
            switch (operation){
                case '*' : return new DoubleType(String.valueOf(expr1.asDouble() * expr2.asDouble()));
                case '/' : return new DoubleType(String.valueOf(expr1.asDouble() / expr2.asDouble()));
                case '-' : return new DoubleType(String.valueOf(expr1.asDouble() - expr2.asDouble()));
                default :
                    return new DoubleType(String.valueOf(expr1.asDouble() + expr2.asDouble()));
            }
        }
        if(expr1 instanceof CharType){
            switch (operation){
                case '*' : return new CharType(String.valueOf(expr1.asChar() * expr2.asChar()));
                case '/' : return new CharType(String.valueOf(expr1.asChar() / expr2.asInt()));
                case '-' : return new CharType(String.valueOf(expr1.asChar() - expr2.asChar()));
                default :
                    return new CharType(String.valueOf(expr1.asChar() + expr2.asChar()));
            }
        }
        if(expr1 instanceof StringType){
            switch (operation){
                case '+' :
                    return new StringType(String.valueOf(expr1.asString() + expr2.asString()));
                    default:
                        throw new RuntimeException("Unknown operation token: " + operation);
            }
        }
        throw new RuntimeException("Unknown type token: " + expr1);
    }
}
