package Lexer;

import java.util.ArrayList;
import java.util.List;

public class TokenBlock implements Token {

    private List<Token> tokens;
    private TypeToken Type;
    public TokenBlock(){
        this.tokens = new ArrayList<Token>();
        this.Type = TypeToken.Block;
    }

    public void putToken(final TypeToken type, final String value){
        tokens.add(new BaseToken(type,value));
    }
    public List<Token> getTokens(){ return tokens; }
    @Override
    public TypeToken getType() { return Type; }
    @Override
    public String getValue() { return "{BLOCK_VALUE}"; }
    @Override
    public void setValue(String value) {}
    @Override
    public void setType(TypeToken type) {}
}
