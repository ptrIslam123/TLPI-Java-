package SemanticsAnalyzer.TokenAnalyzer;

import Lexer.BaseToken;
import Lexer.Token;
import Lexer.TypeToken;
import java.util.List;

public class BaseInterface {
    private  List<Token> tokens;
    private  int pos, length;
    private  Token EOF = new BaseToken(TypeToken.EOF);

    public  void setTokens(final List<Token> tokens) {
        this.tokens = tokens;
        length = tokens.size();
        pos = 0;
    }

    protected  Token get(final int position_relative){
        int position = pos + position_relative;
        if(position >= length)return EOF;
        return tokens.get(position);
    }

    protected String getValue(final int position){
        return get(position).getValue();
    }

    protected void removeToken(){
        tokens.remove(pos);
    }

    protected void deleteNodeIfEqualToken(final TypeToken typeToken){
        if(getType(0) == typeToken){
            tokens.remove(pos);
        }else showErrorMsg("Unknown token type: "+typeToken);
    }

    protected void setTokenType(final int index, final Token token){
        tokens.set(index, token);
    }
    protected void insertToken(final int index, final Token newValue){
        tokens.add(index, newValue);
    }

    protected TypeToken getType(final int position){
        return get(position).getType();
    }
    protected  void next(final int shift){
        pos += shift;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    protected  int getPos(){
        return pos;
    }

    protected  boolean evenToken(final TypeToken currentToken){
        if(get(0).getType() == currentToken)return true;
        else return false;
    }
    protected  void setPos(int pos) {
        this.pos = pos;
    }

    protected void showErrorMsg(final String errorMsg){
        throw new RuntimeException(errorMsg);
    }
}
