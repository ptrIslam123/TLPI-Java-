package Parser;

import java.util.List;
import Lexer.Token;
import Lexer.TypeToken;
import Parser.TYPE.DoubleType;
import Parser.TYPE.IntegerType;
import Parser.TYPE.StringType;
import Parser.TYPE.Type;

public class Parser extends BaseParser {

    public Parser(final List<Token> tokens){
        setTokens(tokens);
        setPos(0);
    }

    public void run(){
        /*
        for(Token it : getTokens()){
            System.out.println(it.getType()+" : " + it.getValue());
        }
        System.out.println("========================");
        */
        Type result = additive();
        System.out.println(result.asString());
    }

    private Type additive(){
        Type result = multiplicative();
        while(true){
            if(get(0).getType() == TypeToken.Add){
                next();
                result = eval('+', result, multiplicative());
                continue;
            }
            if(get(0).getType() == TypeToken.Sub){
                next();
                result = eval('-', result, multiplicative());
                continue;
            }
            break;
        }
        return result;
    }
    private Type multiplicative(){
        Type result = primary();
        while(true){
            if(get(0).getType() == TypeToken.Mult){
                next();
                result = eval('*', result, primary());
                continue;
            }
            if(get(0).getType() == TypeToken.Div){
                next();
                result = eval('/', result, primary());
                continue;
            }
            break;
        }
        return result;
    }

    private Type primary(){
        Type temp;
        if(get(0).getType() == TypeToken.NumInt32){
             temp = new IntegerType(get(0).getValue());
            next();
            return temp;
        }
        if(get(0).getType() == TypeToken.NumDouble64){
            temp = new DoubleType(get(0).getValue());
            next();
            return temp;
        }
        if(get(0).getType() == TypeToken.Str){
            temp = new StringType(get(0).getValue());
            next();
            return temp;
        }
        throw new RuntimeException("Unknow Token");
    }

    private Type eval(char operation, Type expr1, Type expr2){
        return new BinaryExpression(operation, expr1, expr2).eval();
    }
}
