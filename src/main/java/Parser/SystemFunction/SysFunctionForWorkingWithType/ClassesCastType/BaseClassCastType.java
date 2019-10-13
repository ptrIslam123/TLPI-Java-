package Parser.SystemFunction.SysFunctionForWorkingWithType.ClassesCastType;

import Lexer.TypeToken;
import Parser.SystemFunction.SysFuncInterface.BaseInterface;
import Parser.Type.Integral.*;
import Parser.Type.PrimitiveType;
import Parser.Type.Types.Primitive;
import Parser.Type.Types.Type;

public class BaseClassCastType extends BaseInterface {

    protected static Type dynamicCastType(final TypeToken castT, final Type valueExpr){
        return castType(castT, getPrimitive(valueExpr));
    }

    private static Type castType(final TypeToken castT, final Primitive pmv){
        if(pmv instanceof IntegerType){
            return createValueCastType(castT, pmv.asInt());
        }
        if(pmv instanceof DoubleType){
            return createValueCastType(castT, pmv.asDouble());
        }
        if(pmv instanceof StringType){
            return createValueCastType(castT, pmv.asString());
        }
        if(pmv instanceof BoolType){
            return createValueCastType(castT, pmv.asBool());
        }
        if(pmv instanceof CharType){
            return createValueCastType(castT, pmv.asChar());
        }
        showErrorMsg("unknown cast type");
        return null;
    }

    private static Type createValueCastType(final TypeToken castT, final int expr) {
        switch (castT){
            case Str_cast:
                return new PrimitiveType(new StringType(String.valueOf(expr)));
            case Int_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            case Double_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            case Char_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            case Boll_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            default:
                showErrorMsg("unknown token type cast: "+castT);
        }
        return null;
    }

    private static Type createValueCastType(final TypeToken castT, final double expr) {
        switch (castT){
            case Str_cast:
                return new PrimitiveType(new StringType(String.valueOf(expr)));
            case Int_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            case Double_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            case Char_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            case Boll_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            default:
                showErrorMsg("unknown token type cast: "+castT);
        }
        return null;
    }
    private static Type createValueCastType(final TypeToken castT, final boolean expr) {
        switch (castT){
            case Str_cast:
                return new PrimitiveType(new StringType(String.valueOf(expr)));
            case Int_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            case Double_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            case Char_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            case Boll_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            default:
                showErrorMsg("unknown token type cast: "+castT);
        }
        return null;
    }
    private static Type createValueCastType(final TypeToken castT, final char expr) {
        switch (castT){
            case Str_cast:
                return new PrimitiveType(new StringType(String.valueOf(expr)));
            case Int_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            case Double_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            case Char_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            case Boll_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            default:
                showErrorMsg("unknown token type cast: "+castT);
        }
        return null;
    }
    private static Type createValueCastType(final TypeToken castT, final String expr) {
        switch (castT){
            case Str_cast:
                return new PrimitiveType(new StringType(String.valueOf(expr)));
            case Int_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            case Double_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            case Char_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            case Boll_cast:
                return new PrimitiveType(new IntegerType(String.valueOf(expr)));
            default:
                showErrorMsg("unknown token type cast: "+castT);
        }
        return null;
    }

}
