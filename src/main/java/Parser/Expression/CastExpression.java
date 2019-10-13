package Parser.Expression;

import Lexer.TypeToken;
import Parser.Type.Integral.StringType;
import Parser.Type.PrimitiveType;
import Parser.Type.Types.Type;

public class CastExpression implements Expression {
    private TypeToken castType;
    private Type value;

    public void init(TypeToken castType, Type value) {
        this.castType = castType;
        this.value = value;
    }

    @Override
    public Type eval() {
        switch (castType){
            case Str:{
                return new PrimitiveType(new StringType(String.valueOf(value.asPrimitive().asInt())));
            }
        }
         return null;
    }
}
