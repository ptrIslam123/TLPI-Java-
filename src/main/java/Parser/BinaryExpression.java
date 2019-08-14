package Parser;

import Parser.TYPE.DoubleType;
import Parser.TYPE.IntegerType;
import Parser.TYPE.StringType;
import Parser.TYPE.Type;

public class BinaryExpression {
    private Type expr1, expr2;
    private char operation;

    public BinaryExpression(char operation, Type expr1, Type expr2){
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.operation = operation;
    }

    public Type eval(){
        if(expr1 instanceof IntegerType){
            switch(operation){
                case '*': return new IntegerType(expr1.asInteger32() * expr2.asInteger32());
                case '/' : return new IntegerType(expr1.asInteger32() / expr2.asInteger32());
                case '-' : return new IntegerType(expr1.asInteger32() - expr2.asInteger32());
                default:
                    return new IntegerType(expr1.asInteger32() + expr2.asInteger32());
            }
        }
        if(expr1 instanceof DoubleType){
            switch(operation){
                case '*' : return new DoubleType(expr1.asDouble64() * expr2.asDouble64());
                case '/' : return new DoubleType(expr1.asDouble64() / expr2.asDouble64());
                case '-' : return new DoubleType(expr1.asDouble64() - expr2.asDouble64());
                default:
                    return new DoubleType(expr1.asDouble64() + expr2.asDouble64());
            }

        }
        if(expr1 instanceof StringType){
            switch(operation){
                case '+' : return new StringType(expr1.asString() + expr2.asString());
                default: throw new RuntimeException("Error");
            }
        }
        else throw new RuntimeException("Error");
    }
}
