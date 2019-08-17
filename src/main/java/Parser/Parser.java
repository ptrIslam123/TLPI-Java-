package Parser;

import Lexer.BaseToken;
import Lexer.Token;
import Lexer.TypeToken;
import Parser.Lib.BinaryConditionExpression;
import Parser.Lib.BinaryExpression;
import Parser.Lib.Expression;
import Parser.Lib.IOstream.ReadStatement;
import Parser.Lib.Statement;
import Parser.Type.*;
import Parser.Type.CastType.CastType;
import Parser.Variable.VariableDeclare;
import Parser.Variable.VariableTable;
import java.util.List;
import java.util.Map;

public class Parser {
    private List<Token> tokes;
    private Token EOF = new BaseToken(TypeToken.EOF);
    private int pos,length;

    private BinaryExpression binaryExpression;
    private BinaryConditionExpression binaryConditionExpr;
    private ReadStatement readStatement;

    public Parser(List<Token> tokes) {
        this.tokes = tokes;
        this.length = tokes.size();
        this.binaryConditionExpr = new BinaryConditionExpression();
        this.binaryExpression = new BinaryExpression();
        this.readStatement = new ReadStatement();
    }

    public void run(){
        /*for(Token it : tokes){
            System.out.println(it.getType() + " : "+it.getValue());
        }
        */

        while(cond()){
            statement();
        }
/*
        System.out.println("size_var_table: "+VariableTable.getVariableTable().size());
        for(Map.Entry<String , Type> it : VariableTable.getVariableTable().entrySet()){
            System.out.println(it.getKey()+" = "+it.getValue().asInt());
        }
*/
    }

    private Statement statement(){
        if(get(0).getType() == TypeToken.sys_read){ // print str(expression)
            pos++;
            readStatement.stream(static_cast());
            readStatement.execute();
            return readStatement;
        }
        return parse_variable();
    }

    private Statement parse_variable(){
        if(get(0).getType() == TypeToken.Word && get(1).getType() == TypeToken.Equals){
            String name = get(0).getValue();
            pos+=2;
            return new VariableDeclare(name, static_cast());
        }
        throw new RuntimeException("Unknown Expression: "+get(0).getValue());
    } /** парсинг переменных **/


    /** ПРИВЕДЕНИЕ ТИПОВ **/
    private Type static_cast(){
        if(get(0).getType() == TypeToken.Str_cast && get(1).getType() == TypeToken.Lparen){
            pos++;
            Type temp_val = primary();
            Expression temp = new CastType(TypeToken.Str, temp_val);
            return temp.evalExpression();
        }
        if(get(0).getType() == TypeToken.Int_cast && get(1).getType() == TypeToken.Lparen){
            pos++;
            Type temp_val = primary();
            Expression temp = new CastType(TypeToken.NumInt32, temp_val);
            return temp.evalExpression();
        }
        if(get(0).getType() == TypeToken.Double_cast && get(1).getType() == TypeToken.Lparen){
            pos++;
            Type temp_val = primary();
            Expression temp = new CastType(TypeToken.NumDouble64, temp_val);
            return temp.evalExpression();
        }
        if(get(0).getType() == TypeToken.Boll_cast && get(1).getType() == TypeToken.Lparen){
            pos++;
            Type temp_val = primary();
            Expression temp = new CastType(TypeToken.Bool, temp_val);
            return temp.evalExpression();
        }
        return conditionExpression();
    }

    /** будем парсить условные выражения **/
    private Type conditionExpression(){
        Type result = additive();
        while(true){
            if(get(0).getType() == TypeToken.Less){
                pos++;
                result = eval(TypeToken.Less, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.More){
                pos++;
                result = eval(TypeToken.More, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.LessEq){
                pos++;
                result = eval(TypeToken.LessEq, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.MoreEq){
                pos++;
                result = eval(TypeToken.MoreEq, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.Eq){
                pos++;
                result = eval(TypeToken.Eq, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.NoEq){
                pos++;
                result = eval(TypeToken.NoEq, result, additive());
                continue;
            }
            break;
        }
        return result;
    }  /** [>], [<], [>=], [<=], [==], [!=]**/


    private Type additive(){
        Type result = multiplicative();
        while(true){
            if(get(0).getType() == TypeToken.Add){
                pos++;
                result = eval('+',result, multiplicative());
                continue;
            }
            if(get(0).getType() == TypeToken.Sub){
                pos++;
                result = eval('-',result, multiplicative());
                continue;
            }
            break;
        }
        return result;
    }           /** [+], [-] **/
    private Type multiplicative(){
        Type result = primary();
        while(true){
            if(get(0).getType() == TypeToken.Mult){
                pos++;
                result = eval('*',result, primary());
                continue;
            }
            if(get(0).getType() == TypeToken.Div){
                pos++;
                result = eval('/',result, primary());
                continue;
            }
            break;
        }
        return result;
    }   /** [*], [/]**/

    private Type primary(){
        Type temp = null;
        if(get(0).getType() == TypeToken.Lparen){
            pos++;
            temp = conditionExpression();
            equal_type(TypeToken.Rparen);
            return temp;
        }
        if(get(0).getType() == TypeToken.Word){
            String name = get(0).getValue();
            pos++;
            return VariableTable.getValueVariable(name);
        }
        if(get(0).getType() == TypeToken.NumInt32){
            temp = new IntegerType(get(0).getValue());
            pos++;
            return temp;
        }
        if(get(0).getType() == TypeToken.NumDouble64){
            temp = new DoubleType(get(0).getValue());
            pos++;
            return temp;
        }
        if(get(0).getType() == TypeToken.Str){
            temp = new StringType(get(0).getValue());
            pos++;
            return temp;
        }
        if(get(0).getType() == TypeToken.Bool){
            temp = new BoolType(get(0).getValue());
            pos++;
            return temp;
        }
        throw new RuntimeException("Unknown Token Type: "+get(0).getType());
    }   /** парсинг примитивов **/

    private boolean cond(){
        if(get(0).getType() != TypeToken.EOF)return true;
        else return false;
    }
    private Token get(final int position_relative){
        int position = pos + position_relative;
        if(position >= length)return EOF;
        return tokes.get(position);
    } /** получение токена **/
    private Type eval(char operation, Type expr1, Type epxr2) {
        binaryExpression.init(operation, expr1, epxr2);
        return binaryExpression.evalExpression();
    } /** метод для вычисления значений выражений **/
    private Type eval(TypeToken operation, Type expr1, Type expr2) {
        binaryConditionExpr.init(operation, expr1, expr2);
        return binaryConditionExpr.evalExpression();
    }
    private boolean equal_type(final TypeToken type){
        if(get(0).getType() == type){
            pos++;
            return true;
        }
        throw new RuntimeException("Unknown Token Type: "+get(0).getType());
    }
}
