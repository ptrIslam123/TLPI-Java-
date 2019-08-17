package Parser;

import Lexer.BaseToken;
import Lexer.Token;
import Lexer.TypeToken;
import Parser.Lib.BinaryExpression;
import Parser.Type.IntegerType;
import Parser.Type.Type;
import Parser.Variable.VariableTable;
import java.util.List;
import java.util.Map;

public class Parser {
    private List<Token> tokes;
    private Token EOF = new BaseToken(TypeToken.EOF);
    private BinaryExpression binaryExpression;
    private int pos,length;

    public Parser(List<Token> tokes) {
        this.tokes = tokes;
        this.length = tokes.size();
        this.binaryExpression = new BinaryExpression();
    }

    public void run(){
        while(cond()){
            parse_variable();
        }

        System.out.println("size_var_table: "+VariableTable.getVariableTable().size());
        for(Map.Entry<String , Type> it : VariableTable.getVariableTable().entrySet()){
            System.out.println(it.getKey()+" = "+it.getValue().asInt());
        }
    }
    private void parse_variable(){
        if(get(0).getType() == TypeToken.Word && get(1).getType() == TypeToken.Equals){
            String name = get(0).getValue();
            pos+=2;
            VariableTable.addVariable(name, additive());
            return;
        }
        throw new RuntimeException("Unknown Expression"+get(0).getValue());
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
    }
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
    }

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
        throw new RuntimeException("Unknown Token Type");
    }

    private boolean cond(){
        if(get(0).getType() != TypeToken.EOF)return true;
        else return false;
    }
    private Token get(final int position_relative){
        int position = pos + position_relative;
        if(position >= length)return EOF;
        return tokes.get(position);
    }
    private Type eval(char operation, Type expr1, Type epxr2) {
        binaryExpression.init(operation, expr1, epxr2);
        return binaryExpression.evalExpression();
    }
}
