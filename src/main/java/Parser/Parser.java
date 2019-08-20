package Parser;

import Lexer.*;
import Parser.Lib.Expression.BinaryConditionExpression;
import Parser.Lib.Expression.BinaryExpression;
import Parser.Lib.Statement.IOStream.WriteStatement;
import Parser.Lib.Statement.Statement;
import Parser.Type.*;
import Parser.Variable.ArrayDeclare;
import Parser.Variable.ArrayTable;
import Parser.Variable.VariableDeclare;
import Parser.Variable.VariableTable;


import java.util.List;

public class Parser {
    private List<Token> tokens;
    private final Token EOF = new BaseToken(TypeToken.EOF);
    private int pos, length;

    private VariableDeclare varDeclare;
    private BinaryExpression binaryExpr;
    private BinaryConditionExpression binaryCondExpr;
    private WriteStatement writeStream;
    private ArrayDeclare arrDeclare;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.length = tokens.size();
        this.writeStream = new WriteStatement();
        this.varDeclare = new VariableDeclare();
        this.binaryExpr = new BinaryExpression();
        this.binaryCondExpr = new BinaryConditionExpression();
        this.arrDeclare = new ArrayDeclare();
    }
    public void run(){
        //for(Token it : tokens) System.out.println(it.getType()+" : "+it.getValue());

        while(cond()){
            statement();
        }

    }


    private Statement statement(){
        if(get(0).getType() == TypeToken.Block){
            return blockStatement();
        }
        if(get(0).getType() == TypeToken.sys_write){
            next(1);
            writeStream.stream(expression());
            return writeStream;
        }
        if(get(0).getType() == TypeToken.Word && get(1).getType() == TypeToken.Equals){
            String name = get(0).getValue();
            next(2);
            varDeclare.decalre(name, expression());
            return varDeclare;
        }
        throw new RuntimeException("Error");
    }

    private Statement blockStatement() {
        TokenBlock block = (TokenBlock) get(0);
        Parser parser = new Parser(block.getTokens());
        parser.run();
        next(1);
        return null;
    }


    private Type expression(){ return conditionExpression(); }
    private Type conditionExpression(){
        Type result = additive();
        while(true){
            if(get(0).getType() == TypeToken.Less){
                next(1);
                result = eval(TypeToken.Less, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.More){
                next(1);
                result = eval(TypeToken.More, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.LessEq){
                next(1);
                result = eval(TypeToken.LessEq, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.MoreEq){
                next(1);
                result = eval(TypeToken.MoreEq, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.Eq){
                next(1);
                result = eval(TypeToken.Eq, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.NoEq){
                next(1);
                result = eval(TypeToken.NoEq, result, additive());
                continue;
            }
            break;
        }
        return result;
    }
    private Type additive(){
        Type result = multiplivative();
        while(true){
            if(get(0).getType() == TypeToken.Add){
                next(1);
                result = eval('+',result, multiplivative());
                continue;
            }
            if(get(0).getType() == TypeToken.Sub){
                next(1);
                result = eval('-',result, multiplivative());
                continue;
            }
            break;
        }
        return result;
    }
    private Type multiplivative(){
        Type result = primary();
        while(true){
            if(get(0).getType() == TypeToken.Mult){
                next(1);
                result = eval('*',result, primary());
                continue;
            }
            if(get(0).getType() == TypeToken.Div){
                next(1);
                result = eval('-',result, primary());
                continue;
            }
            break;
        }
        return result;
    }



    private Type primary(){
        Type temp = null;
        if(get(0).getType() == TypeToken.Lparen) {
            next(1);
            temp = expression();
            equal_tyep(TypeToken.Rparen);
            return temp;
        }
        if(get(0).getType() == TypeToken.Word){
            String name = get(0).getValue();
            next(1);
            return VariableTable.getValue(name);
        }
        if(get(0).getType() == TypeToken.NumInt32){
            temp = new IntegerType(get(0).getValue());
            next(1);
            return temp;
        }
        if(get(0).getType() == TypeToken.NumDouble64){
            temp = new DoubleType(get(0).getValue());
            next(1);
            return temp;
        }
        if(get(0).getType() == TypeToken.Str){
            temp = new StringType(get(0).getValue());
            next(1);
            return temp;
        }
        if(get(0).getType() == TypeToken.Bool){
            temp = new BoolType(get(0).getValue());
            next(1);
            return temp;
        }
        throw new RuntimeException("Unknown token type");
    }

    private void next(final int shift_pos){ pos+=shift_pos; }
    private Token get(final int position_relative){
        int position = pos + position_relative;
        if(position >= length)return EOF;
        return tokens.get(position);
    }
    private Type eval(char operation, Type expr1, Type expr2) {
        binaryExpr.init(operation, expr1, expr2);
        return binaryExpr.evalExpr();
    }
    private Type eval(TypeToken operation, Type expr1, Type expr2) {
        binaryCondExpr.init(operation, expr1, expr2);
        return binaryCondExpr.evalExpr();
    }
    private boolean cond() {
        if(get(0).getType() == TypeToken.EOF)return false;
        else return true;
    }
    private boolean equal_tyep(final TypeToken type){
        if(get(0).getType() == type){
            next(1);
            return true;
        }else throw new RuntimeException("Unknow token type");
    }
}
