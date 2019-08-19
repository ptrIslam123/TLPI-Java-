package Parser;

import Lexer.*;
import Parser.Lib.Expression.BinaryConditionExpression;
import Parser.Lib.Expression.BinaryExpression;
import Parser.Lib.Statement.IOStream.ReadStatement;
import Parser.Lib.Statement.Statement;
import Parser.Type.*;
import Parser.Variable.ArrayDeclare;
import Parser.Variable.ArrayTable;
import Parser.Variable.VariableDeclare;
import Parser.Variable.VariableTable;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parser {
    private List<Token> tokens;
    private final Token EOF = new BaseToken(TypeToken.EOF);
    private int pos, length;

    private VariableDeclare varDeclare;
    private BinaryExpression binaryExpr;
    private BinaryConditionExpression binaryCondExpr;
    private ReadStatement readStrime;
    private ArrayDeclare arrDeclare;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.length = tokens.size();
        this.readStrime = new ReadStatement();
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
        //int arr[]  ={1,2};
        //System.out.println(arr[0] =12);
/*
        System.out.println("size ="+VariableTable.getVariableTable().size());
        for(Map.Entry<String, Type> it : VariableTable.getVariableTable().entrySet()){
            System.out.println(it.getKey()+" = " + it.getValue().asInt());
        }*/
    }

    private Statement statement(){
        return printStatement();
    }

    private Statement printStatement(){
        if(get(0).getType() == TypeToken.sys_read){
            next(1);
            readStrime.stream(expression());
            return readStrime;
        }
        return parse_vairable_statement();
    }
    private Statement parse_vairable_statement(){

        if(get(0).getType() == TypeToken.Word && get(1).getType() == TypeToken.L_SQUareParen){ // word [
            return parse_array_statement();
        }
        if(get(0).getType() == TypeToken.Word && get(1).getType() == TypeToken.Equals){ // word =
            String name = get(0).getValue();
            next(2);
            varDeclare.decalre(name,expression());
            return varDeclare;
        }
        throw new RuntimeException("Unknown statement");
    }

    private Statement parse_array_statement() { /** парсиинг массива данных **/
        String name_array = get(0).getValue();
        next(2);
        Type capasity = null;
        List<Type> initialize_data = null;
        if(get(0).getType() == TypeToken.NumInt32 || get(0).getType() == TypeToken.Lparen){
            capasity = expression(); // размер инициализаций массива
        }
        next(1);
        // =
        equal_tyep(TypeToken.Equals);
        if(get(0).getType() == TypeToken.ShapeLparen){  // если это инициализация массива {data1, data2, ...}
            initialize_data = initialize_array();
            arrDeclare.init(name_array, capasity, initialize_data);
        }else if(get(0).getType() == TypeToken.Void){ // если по индексу массива изменяем значение элемениа arr[index]  = new_value;
            next(1);
            arrDeclare.init(name_array, capasity, initialize_data);
        }else {
            pos -=5;
            expression();
        }

        return arrDeclare;
    }

    private List<Type> initialize_array() {
        next(1);
        List<Type> initialize_data = new ArrayList<Type>();
        while(get(0).getType() != TypeToken.ShapeRparen){
            if(get(0).getType() == TypeToken.Comma)next(1);
            initialize_data.add(expression());
        }
        next(1);
        return initialize_data;
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
        if(get(0).getType() == TypeToken.Lparen){
            next(1);
            temp = expression();
            equal_tyep(TypeToken.Rparen);
            return temp;
        }if(get(0).getType() == TypeToken.Word && get(1).getType() == TypeToken.Point){
            //pos-=2;
           String name_word = get(0).getValue();
           next(2);
           return  sys_method_word(name_word, get(0).getType());
        }
        if(get(0).getType() == TypeToken.Word && get(1).getType() == TypeToken.L_SQUareParen){ /** парсим массив типов, плолучение значениея по индексу **/
            String name_array = get(0).getValue(); // получение имени массива к которому идет обращение
            next(2);
            Type get_index = expression(); // получение индекса обращения
            next(1);
            if(get(0).getType() == TypeToken.Equals){
                next(1);
                Type new_value = expression(); // вычисляем новое значение элемента массива
                ArrayTable.setArrayValue(name_array, get_index, new_value);
                return new VoidType();
            }
            return ArrayTable.getDataIndex(name_array,get_index);
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

    private Type sys_method_word(String word, TypeToken expr) {
       switch (expr){
           case Size:{
               next(1);
               return ArrayTable.getSize(word);
           }
           case Len:{
               next(1);
               return VariableTable.getLength(word);
           }
       }throw new RuntimeException("Unknown operation type: " + expr);
    }/** встроенные в язык методы для некоторых типов данных **/

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
/*

 String name_array = get(0).getValue();
        next(2);
        Type capasity = null;
        List<Type> initialize_data = null;
        if(get(0).getType() == TypeToken.NumInt32){
            capasity = new IntegerType(get(0).getValue()); // размер инициализаций массива
            next(1);
        }
        next(1);
        if(get(0).getType() == TypeToken.Equals) {
            next(1);
            if (get(0).getType() == TypeToken.ShapeLparen) {
                next(1);
                //equal_tyep(TypeToken.ShapeLparen);
                initialize_data = new ArrayList<Type>();
                while (get(0).getType() != TypeToken.ShapeRparen) {
                    if (get(0).getType() == TypeToken.Comma) next(1);
                    initialize_data.add(expression());
                }
                next(1);
            }
            arrDeclare.init(name_array, capasity, initialize_data);
        }else {
            Type new_vale = expression();
            ArrayTable.setArrayValue(name_array, capasity, new_vale);
        }
 */