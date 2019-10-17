package Lexer;

import java.util.ArrayList;

public class TokenIndex implements Token {
    private ArrayList<Token> tokens;

    public TokenIndex() {
        this.tokens = new ArrayList<Token>();
    }

    public void add(final Token token){
        tokens.add(token);
    }
    public void add(final TypeToken type, final String value){
        tokens.add(new BaseToken(type, value));
    }
    public int getSize(){ return tokens.size(); }
    public ArrayList<Token> getTokens() {
        return tokens;
    }

    @Override
    public TypeToken getType() {
        return TypeToken.Index;
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
