package Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Lexer.Token;
import Lexer.TypeToken;
import Parser.Library.BinaryExpression;
import Parser.Library.ReciveTypeExpression;
import Parser.Library.Statement;
import Parser.Library.VARIABLE.VariableDeclere;
import Parser.TYPE.*;

import javax.swing.plaf.nimbus.State;


public class Parser extends BaseParser {

    public Parser(final List<Token> tokens){
        setTokens(tokens);
        setPos(0);
    }

    public void run(){
        List<Statement> statements = new ArrayList<Statement>();
        while(cond()){
            statements.add(statement());
        }

        for(Statement it :statements){
            it.execute();
        }
    }

    private Statement statement(){
        if(get(0).getType() == TypeToken.Word && get(1).getType() == TypeToken.Equals){ /** WORD = Expression **/
            String name = get(0).getValue(); // variable_name
            next(2);
            return new VariableDeclere(name, expression());
        }
        throw new RuntimeException("Unknown Type Expression");
    }
    private Type expression(){
        return additive();
    }
    private Type additive(){
        Type result = multiplicative();
        while(true){
            if(get(0).getType() == TypeToken.Add){
                next();
                result = eval('+',result, multiplicative());
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

    private Type eval(final char operation, final Type expr1, final Type expr2){
        return new BinaryExpression(operation,expr1,expr2).evalExpression();
    } /** посчитать выражение **/
    private Type primary(){
        if(get(0).getType() == TypeToken.Lparen){ /** парсинг  выражений в скобках **/
            next();
            Type value = expression();
            equals_type(TypeToken.Rparen);
            return value;
        }
        ReciveTypeExpression temp = new ReciveTypeExpression(getTokens(), getPos());
        Type temp_type = temp.getTypeExpression();
        setPos(temp.getPosEnd());
        return temp_type;
    }   /** получить примитив в виде типа данных **/

    private boolean equals_type(final TypeToken type){
        if(get(0).getType() == type){
            next();
            return true;
        }
        else throw new RuntimeException("Unknown Type Expression");
    } /** проверка текущего токена **/
}
