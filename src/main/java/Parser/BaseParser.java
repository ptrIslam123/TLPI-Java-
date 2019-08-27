package Parser;

import Lexer.*;
import Parser.Expression.BinaryConditionExpression;
import Parser.Expression.BinaryExpression;
import Parser.Expression.ObjectExpression;
import Parser.Statement.WriteStreamStatement;
import Parser.Type.*;
import java.util.List;

public class BaseParser {
    private List<Token> tokens;
    private int pos, length;
    private final Token EOF = new BaseToken(TypeToken.EOF);

    private BinaryExpression binaryExpr;
    private BinaryConditionExpression binaryCondExpr;
    private WriteStreamStatement write;
    private ObjectExpression objectExpr;
    private VoidType voidV;

    public BaseParser() {
        this.objectExpr = new ObjectExpression();
        this.binaryExpr = new BinaryExpression();
        this.voidV = new VoidType();
        this.binaryCondExpr = new BinaryConditionExpression();
        this.write = new WriteStreamStatement();
    }



    protected Type primitive(){
        Type temp = null;
        /** примитивные типы данных **/
        if(get(0).getType() == TypeToken.NumInt32){
            temp = new IntegerType(get(0).getValue());
            next(1);
            return temp;
        }
        if(get(0).getType() == TypeToken.Str){
            temp = new StringType(get(0).getValue());
            next(1);
            return temp;
        }
        if(get(0).getType() == TypeToken.NumDouble64){
            temp = new DoubleType(get(0).getValue());
            next(1);
            return temp;
        }
        if(get(0).getType() == TypeToken.Bool){
            temp = new BoolType(get(0).getValue());
            next(1);
            return temp;
        }
        if(get(0).getType() == TypeToken.Char){
            temp = new CharType(get(0).getValue());
            next(1);
            return temp;
        }
        throw new RuntimeException("Unknown token type");
    }

    protected Type eval(char operation, Type expr1, Type expr2) {
        binaryExpr.init(operation, expr1, expr2);
        return binaryExpr.eval();
    }
    protected Type eval(TypeToken operation, Type expr1, Type expr2) {
        binaryCondExpr.init(operation, expr1, expr2);
        return binaryCondExpr.eval();
    }

    protected boolean cond(){
        if(get(0).getType() == TypeToken.EOF)return false;
        return true;
    }
    protected boolean cond(final TypeToken type){
        if(get(0).getType() == type)return false;
        return true;
    }
    protected void next(final int position_shift){ pos+= position_shift; }
    protected Token get(final int position_relative){
        int position = pos + position_relative;
        if(position >= length)return EOF;
        return tokens.get(position);
    }
    protected void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }
    protected void setPos(int pos) {
        this.pos = pos;
    }
    protected void setLength(int length) {
        this.length = length;
    }
    protected int getPos() {
        return pos;
    }
    protected int getLength() {
        return length;
    }
    protected boolean consume(final TypeToken type){
        if(get(0).getType() == type){
            next(1);
            return true;
        }else throw new RuntimeException("Unknown token type");
    }
    public List<Token> getTokens() {
        return tokens;
    }
}
