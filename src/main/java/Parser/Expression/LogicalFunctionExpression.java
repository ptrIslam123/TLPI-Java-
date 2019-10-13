package Parser.Expression;

import Lexer.TypeToken;
import Parser.Type.Integral.BoolType;
import Parser.Type.PrimitiveType;
import Parser.Type.Types.Type;

public class LogicalFunctionExpression implements Expression {
    private TypeToken operator;
    private boolean exprFirst, exprSecond;

    public void init(TypeToken operator, Type exprFirst, Type exprSecond) {
        this.operator = operator;
        this.exprFirst = exprFirst.asPrimitive().asBool();
        this.exprSecond = exprSecond.asPrimitive().asBool();
    }

    @Override
    public Type eval() {
        switch (operator){
            case And:{
                if(exprFirst && exprSecond)return new PrimitiveType(new BoolType("1"));
                else return new PrimitiveType(new BoolType("0"));
            }
            case Or:{
                if(exprFirst || exprSecond)return new PrimitiveType(new BoolType("1"));
                else return new PrimitiveType(new BoolType("0"));
            }
            default:
                throw new RuntimeException("Unknown logical operator: "+operator);
        }
    }
}
