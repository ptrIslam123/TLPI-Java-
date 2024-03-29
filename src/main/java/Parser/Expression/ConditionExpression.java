package Parser.Expression;

import Lexer.TypeToken;
import Parser.Type.Integral.*;
import Parser.Type.Types.Primitive;
import Parser.Type.PrimitiveType;
import Parser.Type.Types.Type;

public class ConditionExpression implements Expression {
    private TypeToken operation;
    private Primitive expr1, expr2;

    public void init(TypeToken operation, Type expr1, Type expr2) {
        this.operation = operation;
        this.expr1 = expr1.asPrimitive();
        this.expr2 = expr2.asPrimitive();
    }

    @Override
    public Type eval() {
        if(expr1 instanceof IntegerType){
            switch (operation){
                case Less: {
                    if(expr1.asInt() < expr2.asInt())return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case LessEq: {
                    if(expr1.asInt() <= expr2.asInt())return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case More: {
                    if(expr1.asInt() > expr2.asInt())return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case MoreEq: {
                    if(expr1.asInt() >= expr2.asInt())return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case Eq: {
                    if(expr1.asInt() == expr2.asInt())return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case NoEq: {
                    if(expr1.asInt() != expr2.asInt())return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
            }
        }
        if(expr1 instanceof DoubleType){
            switch (operation){
                case Less: {
                    if(expr1.asDouble() < expr2.asDouble())return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case LessEq: {
                    if(expr1.asDouble() <= expr2.asDouble())return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case More: {
                    if(expr1.asDouble() > expr2.asDouble())return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case MoreEq: {
                    if(expr1.asDouble() >= expr2.asDouble())return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case Eq: {
                    if(expr1.asDouble() == expr2.asDouble())return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case NoEq: {
                    if(expr1.asDouble() != expr2.asDouble())return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
            }
        }
        if(expr1 instanceof StringType){
            int len1, len2;
            len1 = expr1.asString().length();
            len2 = expr2.asString().length();

            switch (operation){
                case Less: {
                    if(len1 < len2)return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case LessEq: {
                    if(len1 <= len2)return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case More: {
                    if(len1 > len2)return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case MoreEq: {
                    if(len1 >= len2)return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case Eq: {
                    if(len1 == len2)return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case NoEq: {
                    if(len1 != len2)return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
            }
        }
        if(expr1 instanceof CharType){
            switch (operation){
                case Less: {
                    if(expr1.asChar() < expr2.asChar())return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case LessEq: {
                    if(expr1.asChar() <= expr2.asChar())return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case More: {
                    if(expr1.asChar() > expr2.asChar())return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case MoreEq: {
                    if(expr1.asChar() >= expr2.asChar())return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case Eq: {
                    if(expr1.asChar() == expr2.asChar())return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
                case NoEq: {
                    if(expr1.asChar() != expr2.asChar())return new PrimitiveType(new BoolType("1"));
                    else return new PrimitiveType(new BoolType("0"));
                }
            }
        }
        throw new RuntimeException("[ConditionExpression] = Unknown operation type: "+operation);
    }
}
