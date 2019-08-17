package Parser;

import Lexer.BaseToken;
import Lexer.Token;
import Lexer.TypeToken;
import Parser.Lib.BinaryConditionExpression;
import Parser.Lib.BinaryExpression;
import Parser.Type.*;
import Parser.Variable.VariableTable;
import java.util.List;
import java.util.Map;

public class Parser {
    private List<Token> tokes;
    private Token EOF = new BaseToken(TypeToken.EOF);
    private BinaryExpression binaryExpression;
    private BinaryConditionExpression binaryConditionExpr;
    private int pos,length;

    public Parser(List<Token> tokes) {
        this.tokes = tokes;
        this.length = tokes.size();
        this.binaryConditionExpr = new BinaryConditionExpression();
        this.binaryExpression = new BinaryExpression();
    }

    public void run(){
        while(cond()){
            parse_variable();
        }

        System.out.println("size_var_table: "+VariableTable.getVariableTable().size());
        for(Map.Entry<String , Type> it : VariableTable.getVariableTable().entrySet()){
            System.out.println(it.getKey()+" = "+it.getValue().asBool());
        }
    }
    private void parse_variable(){
        if(get(0).getType() == TypeToken.Word && get(1).getType() == TypeToken.Equals){
            String name = get(0).getValue();
            pos+=2;
            VariableTable.addVariable(name, conditionExpression());
            return;
        }
        throw new RuntimeException("Unknown Expression: "+get(0).getValue());
    } /** парсинг переменных **/
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
    }


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
}
