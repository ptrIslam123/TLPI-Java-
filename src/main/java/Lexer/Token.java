package Lexer;

public interface Token {

    TypeToken getType();
    String getValue();

    void setType(final TypeToken type);
    void setValue(final String value);
}
