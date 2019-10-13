package Lexer;

import java.io.Serializable;

public class BaseToken implements Token, Serializable {
    private TypeToken type;
    private String value;

    public BaseToken(final TypeToken type){
        this.type = type;
        this.value = "";
    }
    public BaseToken(final TypeToken type, final String value){
        this.type = type;
        this.value = value;
    }

    public BaseToken(final TypeToken type, final char value){
        this.type = type;
        this.value = String.valueOf(value);
    }
    public BaseToken(final TypeToken type, final StringBuilder value){
        this.type = type;
        this.value = String.valueOf(value);
    }
    @Override
    public void setType(final TypeToken type) {
        this.type = type;
    }
    @Override
    public void setValue(final String value) {
        this.value = value;
    }
    @Override
    public TypeToken getType() { return this.type; }
    @Override
    public String getValue() { return this.value; }
}
