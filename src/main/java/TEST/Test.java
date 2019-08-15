package TEST;

import Lexer.BaseToken;
import Lexer.Token;
import Lexer.TypeToken;

import java.util.List;
import java.util.Map;


public class Test {
    private Token EOF = new BaseToken(TypeToken.EOF);
    private List<Token> tokens;
    private int length, pos;

    public Test(List<Token> tokens) {
        this.tokens = tokens;
        this.length = tokens.size();
    }

    public void run(){
        //for(Token it : tokens) System.out.println(it.getType()+" = "+it.getValue());
        //System.out.println("================");
        //System.out.println("res = "+additive());
        while (cond()){
            statement();
        }
        System.out.println(VariableTable.getVariables().size());
        for(Map.Entry<String, Integer> it : VariableTable.getVariables().entrySet()){
            System.out.println(it.getKey()+" : "+it.getValue());
        }
    }


    private void statement(){
        if(get(0).getType() == TypeToken.Word && get(1).getType() == TypeToken.Equals){
            String name = get(0).getValue();
            pos+=2;
            VariableTable.put(name, additive());
        }
    }

    private int additive(){
        int result = multiplicative();
        while(true){
            if(get(0).getType() == TypeToken.Add){
                pos++;
                result = eval('+', result,multiplicative());
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

    private int multiplicative() {
        int result = primary();
        while(true){
            if(get(0).getType() == TypeToken.Mult){
                pos++;
                result = eval('*', result, primary());
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


    private int primary(){
        if(get(0).getType() == TypeToken.Lparen){
            pos++;
            int temp = additive();
            if(get(0).getType() != TypeToken.Rparen)throw new RuntimeException("!!!!");
            else pos++;
            return temp;
        }
        if(get(0).getType() == TypeToken.Word){
            String name = get(0).getValue();
            pos++;
            return VariableTable.setValue(name);
        }
        if(get(0).getType() == TypeToken.NumInt32){
            int temp = Integer.parseInt(get(0).getValue());
            pos++;
            return temp;
        }
        throw new RuntimeException("Error");
    }

    private boolean cond(){
        if(get(0).getType() == TypeToken.EOF)return false;
        else return true;
    }
    private Token get(final int position_r){
        int position = pos + position_r;
        if(position >= length)return EOF;
        return tokens.get(position);
    }

    private int eval(char operation, int expr1, int expr2){
        switch (operation){
            case '*':{
                int temp = expr1*expr2;
                return temp;
            }
            case '/':{
                int temp = expr1/expr2;
                return temp;
            }
            case '-':{
                int temp = expr1-expr2;
                return temp;
            }
            default:{
                int temp = expr1 + expr2;
                return temp;
            }
        }
    }
}
