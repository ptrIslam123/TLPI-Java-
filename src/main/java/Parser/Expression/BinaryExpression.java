package Parser.Expression;

import Parser.Type.Integral.CharType;
import Parser.Type.Integral.DoubleType;
import Parser.Type.Integral.IntegerType;
import Parser.Type.Integral.StringType;
import Parser.Type.Types.Primitive;
import Parser.Type.PrimitiveType;
import Parser.Type.Types.Type;

public class BinaryExpression implements Expression {
    private char operation;
    private Primitive expr1, expr2;

    public void init(char operation, Type expr1, Type expr2) {
        this.operation = operation;
        this.expr1 = expr1.asPrimitive();
        this.expr2 = expr2.asPrimitive();
    }

    @Override
    public Type eval() {
        if(expr1 instanceof IntegerType){
            switch (operation){
                case '*' : return new PrimitiveType(new IntegerType(String.valueOf(expr1.asInt() * expr2.asInt())));
                case '/' : return new PrimitiveType(new IntegerType(String.valueOf(expr1.asInt() / expr2.asInt())));
                case '-' : return new PrimitiveType(new IntegerType(String.valueOf(expr1.asInt() - expr2.asInt())));
               default:
                   return new PrimitiveType(new IntegerType(String.valueOf(expr1.asInt() + expr2.asInt())));
            }
        }
        if(expr1 instanceof DoubleType){
            switch (operation){
                case '*' : return new PrimitiveType(new DoubleType(String.valueOf(expr1.asDouble() * expr2.asDouble())));
                case '/' : return new PrimitiveType(new DoubleType(String.valueOf(expr1.asDouble() / expr2.asDouble())));
                case '-' : return new PrimitiveType(new DoubleType(String.valueOf(expr1.asDouble() - expr2.asDouble())));
                default:
                    return new PrimitiveType(new DoubleType(String.valueOf(expr1.asDouble() + expr2.asDouble())));
            }
        }
        if(expr1 instanceof CharType){
            switch (operation){
                case '*' : return new PrimitiveType(new CharType(String.valueOf(expr1.asChar() * expr2.asChar())));
                case '/' : return new PrimitiveType(new CharType(String.valueOf(expr1.asChar() / expr2.asChar())));
                case '-' : return new PrimitiveType(new CharType(String.valueOf(expr1.asChar() - expr2.asChar())));
                default:
                    return new PrimitiveType(new CharType(String.valueOf(expr1.asChar() + expr2.asChar())));
            }
        }
        if(expr1 instanceof StringType){
            switch (operation){
                case '+' : return new PrimitiveType(new StringType(String.valueOf(expr1.asString() + expr2.asString())));
            }
        }
        throw new RuntimeException("[BinaryExpression] = Unknown operation type: "+operation);
    }
}
