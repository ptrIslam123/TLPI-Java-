package Parser.Type.ParserClasses;

import Lexer.*;
import Parser.Expression.*;
import Parser.Statement.ControlConstruction.BreakStatement;
import Parser.Statement.ControlConstruction.ContinueStatement;
import Parser.Statement.ControlConstruction.ReturnStatement;
import Parser.Statement.IOstream.WriteStreamStatement;
import Parser.Type.Integral.*;
import Parser.Type.PrimitiveType;
import Parser.Type.Types.Type;
import java.util.ArrayList;
import java.util.List;

public class BaseParser {
    protected boolean flage_block = false;
    protected boolean flage_static = false;
    protected static boolean flage_struct = false;

    protected static int visibility = 0;

    protected Type retValue  = null;

    private ArrayList<Token> tokens;
    private int pos, length;
    private final Token EOF = new BaseToken(TypeToken.EOF);

    protected Parser paser;

    protected BinaryExpression binaryExpr;
    protected ConditionExpression condExpr;
    protected ObjectData objectData;
    protected WriteStreamStatement writeStream;
    protected CastExpression castExpr;
    protected BreakStatement breakStatement;
    protected ContinueStatement continueStatement;
    protected LogicalFunctionExpression logicalFuncExpr;
    protected  ReturnStatement returnStatement;
    protected Type voidType;



    public BaseParser(){
        setPos(0);
       this.voidType = new PrimitiveType(new VoidType());
        this.objectData = new ObjectData();
        this.binaryExpr = new BinaryExpression();
        this.condExpr = new ConditionExpression();
        this.writeStream = new WriteStreamStatement();
        this.castExpr = new CastExpression();
        this.breakStatement = new BreakStatement();
        this.continueStatement = new ContinueStatement();
        this.logicalFuncExpr = new LogicalFunctionExpression();
        this.returnStatement = new ReturnStatement();
    }

    protected Type primitive(){
        Type temp = null;

        /** примитивные типы данных **/
        if(get(0).getType() == TypeToken.NumInt32){
            temp = new PrimitiveType(new IntegerType(get(0).getValue()));
            next(1);
            return temp;
        }
        if(get(0).getType() == TypeToken.Str){
            temp = new PrimitiveType(new StringType(get(0).getValue()));
            next(1);
            return temp;
        }
        if(get(0).getType() == TypeToken.NumDouble64){
            temp = new PrimitiveType(new DoubleType(get(0).getValue()));
            next(1);
            return temp;
        }
        if(get(0).getType() == TypeToken.Char){
            temp = new PrimitiveType(new CharType(get(0).getValue()));
            next(1);
            return temp;
        }
        if(get(0).getType() == TypeToken.Bool){
            temp = new PrimitiveType(new BoolType(get(0).getValue()));
            next(1);
            return temp;
        }
        throw new RuntimeException("Unknown token type");
    }

    protected Type evalLogicalFucntionExpression(TypeToken operator, Type expreFirst, Type exprSecond) {
        logicalFuncExpr.init(operator, expreFirst, exprSecond);
        return logicalFuncExpr.eval();
    }

    protected Type evalCast(final TypeToken castType, final Type value){
        castExpr.init(castType, value);
        return castExpr.eval();
    }

    protected Type eval(final TypeToken operation, final Type expr1, final Type expr2){
        condExpr.init(operation, expr1, expr2);
        return condExpr.eval();
    }

    protected Type eval(final char operation, final Type expr1, final Type expr2){
        binaryExpr.init(operation, expr1, expr2);
        return binaryExpr.eval();
    }

    protected boolean cond(){
        if(getPos() >= length)return false;
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
    protected void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.length = tokens.size();
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


    protected boolean isFlage_block() {
        return flage_block;
    }
    protected boolean isFlage_static() {
        return flage_static;
    }
    protected boolean isFlage_struct() {
        return flage_struct;
    }
    protected void setFlage_block(boolean flage_block) {
        this.flage_block = flage_block;
    }
    protected void setFlage_static(boolean flage_static) {
        this.flage_static = flage_static;
    }
    protected void setFlage_struct(boolean status) {
        flage_struct = status;
    }
}
