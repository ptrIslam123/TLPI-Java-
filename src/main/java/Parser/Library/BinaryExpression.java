package Parser.Library;

import Parser.TYPE.*;

public class BinaryExpression  implements Expresssion{
    private Type expr1, expr2;
    private final char operation;

    public BinaryExpression(final char operation, final Type expr1, final Type expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.operation = operation;
    }

    @Override
    public Type evalExpression() {
        if(expr1 instanceof IntegerType){ /** если тип класса int **/
            switch(operation){
                case '*' : return new IntegerType(expr1.asInteger32() * expr2.asInteger32());
                case '/' : return new IntegerType(expr1.asInteger32() / expr2.asInteger32());
                case '-' : return new IntegerType(expr1.asInteger32() - expr2.asInteger32());
                default:
                    return new IntegerType(expr1.asInteger32() + expr2.asInteger32());
            }
        }
        if(expr1 instanceof DoubleType){ /** если тип класса double **/
            switch(operation){
                case '*' : return new DoubleType(expr1.asDouble64() * expr2.asDouble64());
                case '/' : return new DoubleType(expr1.asDouble64() / expr2.asDouble64());
                case '-' : return new DoubleType(expr1.asDouble64() - expr2.asDouble64());
                default:
                    return new DoubleType(expr1.asDouble64() + expr2.asDouble64());
            }
        }
        if(expr1 instanceof StringType){  /** если тип класса string **/
            switch(operation){
                case '+' : return new StringType(expr1.asString() + expr2.asString());
                default: throw new RuntimeException("Inaccessible Type Operation");
            }
        }
        else throw new RuntimeException("Unknown Type Token");
    }

}
