package Lexer;


import java.io.Serializable;
import java.util.ArrayList;

public class TokenPush implements Token, Serializable {
    private ArrayList<Token> tokens;
    private final int BEGIN_SIZE_INPUT_PARAMS = 6;
    public TokenPush(){
        this.tokens = new ArrayList<Token>(BEGIN_SIZE_INPUT_PARAMS);
    }

    public void addToken(final Token token){
        tokens.add(token);
    }
    public void addToken(final TypeToken type, final String value){
        tokens.add(new BaseToken(type, value));
    }
    public ArrayList<Token> getTokens() {
        return tokens;
    }
    public Token getToken(final int index){ return tokens.get(index); }
    public int size(){ return tokens.size(); }
    @Override
    public TypeToken getType() {
        return TypeToken.Push;
    }

    @Override
    public String getValue() {
        return "";
    }

    @Override
    public void setValue(String value) {}

    @Override
    public void setType(TypeToken type) {}
}
