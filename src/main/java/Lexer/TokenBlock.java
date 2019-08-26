package Lexer;

import java.util.ArrayList;
import java.util.List;

public class TokenBlock implements Token {

    private TypeToken type = TypeToken.Block;
    private List<Token> tokens;

    public TokenBlock() {
       this.tokens = new ArrayList<Token>();
    }

    public void putToken(final TypeToken type, final String value){
        tokens.add(new BaseToken(type, value));
    }

    public void putBlock(final TokenBlock block){
        tokens.add(block);
    }
    public List<Token> getTokens(){ return this.tokens; }

    @Override
    public TypeToken getType() {
        return type;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public void setValue(String value) {

    }

    @Override
    public void setType(TypeToken type) {

    }
}
