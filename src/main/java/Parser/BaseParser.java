package Parser;

import java.util.List;
import Lexer.BaseToken;
import Lexer.Token;
import Lexer.TypeToken;
import jdk.nashorn.internal.parser.TokenType;

/** Базовый класс парсер в котором находяться
 *  базовые вспомогательные методы**/
public class BaseParser {
    private Token EOF = new BaseToken(TypeToken.EOF);
    private List<Token> tokens;
    private int pos, size_token;


    protected void setTokens(final List<Token> tokens){
        this.tokens = tokens;
        this.size_token = tokens.size();
    }
    protected void setPos(final int position){ this.pos = position; }
    protected int getPos(){ return pos; }
    protected void next(){ pos++; }
    protected void next(final int shift_position){ pos+=shift_position; }

    protected Token get( final int position_relative){
        int position = pos + position_relative;
        if(position >= size_token)return EOF;
        return tokens.get(position);
    }
    protected boolean cond(){
        if(get(0) != EOF)return true;
        else return false;
    }
    protected List<Token> getTokens(){ return this.tokens; }
}
