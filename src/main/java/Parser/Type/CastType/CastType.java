package Parser.Type.CastType;

import Lexer.TypeToken;
import Parser.Lib.Expression;
import Parser.Type.*;

public final class CastType implements Expression {
    private Type value;
    private TypeToken cast_type;

    public CastType(final TypeToken cast_type, final Type value) {
        this.value = value;
        this.cast_type = cast_type;
    }

    @Override
    public Type evalExpression() {
       if(value instanceof IntegerType){
           switch (cast_type){
               case Str: return new StringType(String.valueOf(value.asInt()));
               case NumDouble64: return new DoubleType(String.valueOf(value.asInt()));
               case Bool: {
                   int temp_val = value.asInt();
                   if(temp_val > 0)return new BoolType("1");
                   else return new BoolType("0");
               }
               default: throw new RuntimeException("Error Cast Type: " + cast_type);
           }
       }
       if(value instanceof DoubleType){
           switch (cast_type){
               case Str: return new StringType(String.valueOf(value.asDouble()));
               case NumInt32: return new IntegerType(String.valueOf(value.asDouble()));
               case Bool: {
                   double temp_val = value.asInt();
                   if(temp_val > 0)return new BoolType("1");
                   else return new BoolType("0");
               }
               default: throw new RuntimeException("Error Cast Type: " + cast_type);
           }
       }
       if(value instanceof StringType){
           switch (cast_type){
               case NumInt32: return new IntegerType((value.asString()));
               case NumDouble64: return new DoubleType((value.asString()));
               case Bool: {
                   int temp_val = Integer.parseInt(value.asString());
                   if(temp_val > 0)return new BoolType("1");
                   else return new BoolType("0");
               }
               default: throw new RuntimeException("Error Cast Type: " + cast_type);
           }
       }
       if(value instanceof BoolType){
           switch (cast_type){
               case Str: return new StringType(String.valueOf(value.asBool()));
               case NumDouble64: return new DoubleType(String.valueOf(value.asBool()));
               case NumInt32: return new IntegerType(String.valueOf(value.asBool()));
               default: throw new RuntimeException("Error Cast Type: " + cast_type);
           }
       }
       throw new RuntimeException("Error Cast Type: " + cast_type);
    }
}
