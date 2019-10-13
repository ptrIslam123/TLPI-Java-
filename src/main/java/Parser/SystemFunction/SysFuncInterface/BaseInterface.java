package Parser.SystemFunction.SysFuncInterface;

import Lexer.TypeToken;
import Parser.Type.AggregateType;
import Parser.Type.Integral.StringType;
import Parser.Type.PrimitiveType;
import Parser.Type.StructType.ArrayType;
import Parser.Type.Types.Aggregate;
import Parser.Type.Types.Primitive;
import Parser.Type.Types.Type;

public class BaseInterface {
    protected static Primitive getPrimitive(Type expr){
        if(expr instanceof PrimitiveType){
            return expr.asPrimitive();
        }
        showErrorMsg("unknown cast type");
        return null;
    }

    protected static Aggregate getArrayType(final Type expr){
        if(expr instanceof Aggregate){
            Aggregate aggregate = expr.asAggregate();
            return aggregate;
        }
        showErrorMsg("error cast type(string) "+expr);
        return null;
    }
    protected String asString(final Primitive expr){
        if(expr instanceof StringType){
            return expr.asString();
        }
        showErrorMsg("error cast type(string) "+expr);
        return null;
    }
    protected int asInt(final Primitive expr){
        if(expr instanceof StringType){
            return expr.asInt();
        }
        showErrorMsg("error cast type(integer) "+expr);
        return 0;
    }
    protected double asDouble(final Primitive expr){
        if(expr instanceof StringType){
            return expr.asDouble();
        }
        showErrorMsg("error cast type(double) "+expr);
        return 0.;
    }
    protected char asChar(final Primitive expr){
        if(expr instanceof StringType){
            return expr.asChar();
        }
        showErrorMsg("error cast type(char) "+expr);
        return 0;
    }
    protected boolean asBool(final Primitive expr){
        if(expr instanceof StringType){
            return expr.asBool();
        }
        showErrorMsg("error cast type(bool) "+expr);
        return false;
    }

    protected static Aggregate getAggregate(Type expr){
        if(expr instanceof AggregateType){
            return expr.asAggregate();
        }
        showErrorMsg("unknown cast type");
        return null;
    }
    protected static void verifyInputParams(final boolean expr, final String nameFunc){
        if(!expr)showErrorMsg("undefined function: " + nameFunc);
    }
    protected static void showErrorMsg(final String msg){
        throw new RuntimeException(msg);
    }
}
