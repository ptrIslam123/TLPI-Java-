package Parser.Library;

import Lexer.TypeToken;
import Parser.BaseParser;
import Parser.Library.VARIABLE.VariableExpression;
import Parser.TYPE.*;
import Lexer.Token;
import java.util.List;

public class ReciveTypeExpression extends BaseParser {
    public ReciveTypeExpression(final List<Token> tokens, final int position){
        setTokens(tokens);
        setPos(position);
    }

    public Type getTypeExpression() {
        Type type = null;
        if(get(0).getType() == TypeToken.Word){
            String name = get(0).getValue();
            next();
            return new VariableExpression(name).evalExpression(); /** получаем значение переменной по ее символьному идентификатору**/
        }
        if(get(0).getType() == TypeToken.NumInt32){
            type =  new IntegerType(get(0).getValue());
            next();
            return type;
        }
        if(get(0).getType() == TypeToken.NumDouble64){
            type = new DoubleType(get(0).getValue());
            next();
            return type;
        }
        if(get(0).getType() == TypeToken.Str){
            type = new StringType(get(0).getValue());
            next();
            return type;
        }
        if(get(0).getType() == TypeToken.Bool){
            type = new BoolType(get(0).getValue());
            next();
            return type;
        }
        throw new RuntimeException("Unknown Type Expression");
    }
    public int getPosEnd(){ return getPos(); }
}