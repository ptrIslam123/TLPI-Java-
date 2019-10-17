package Lexer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TokenBlock implements Token, Serializable {

    private TypeToken type = TypeToken.Block;
    private ArrayList<Token> tokens;

    public TokenBlock() {
       this.tokens = new ArrayList<Token>();
    }
    /**
    public void putToken(final TypeToken type, final String value){
        tokens.add(new BaseToken(type, value));
    }

    public void putBlock(final TokenBlock block){
        tokens.add(block);
    }
    public void putBlock(final ArrayList<Token> tokenBlock){
        for(int i=0; i<tokenBlock.size(); i++){
            this.tokens.add(tokenBlock.get(i));
        }
    }
     **/
    public void put(final Token baseToken){
        tokens.add(baseToken);
    }
    /*
    public void put(final TokenBlock tokenBlock){
        tokens.add(tokenBlock);
    }*/
    public void put(final TypeToken typeToken, final String tokenValue){
        tokens.add(new BaseToken(typeToken, tokenValue));
    }
    public void put(final List<Token> tokenList){
        for(Token iterator : tokenList){
            tokens.add(iterator);
        }
    }
    public ArrayList<Token> getTokens(){ return this.tokens; }
    @Override
    public TypeToken getType() {
        return type;
    }

    @Override
    public String getValue() {
        return "";
    }

    @Override
    public void setValue(String value) {

    }

    @Override
    public void setType(TypeToken type) {

    }
}
