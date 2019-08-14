package Lexer;

import java.util.ArrayList;
import java.util.List;

public class BaseLexer {
    private String input;
    private int pos, length;
    protected int index_chars_operand;
    private List<Token> tokens;

    public BaseLexer(){
        this.tokens = new ArrayList<Token>();
    }

    protected final char[] single_chars_operand = {     /** массив односимвольных операндов**/
       '+','-','/','*','=','!',',','.','>','<','(',')','{','}','[',']'
    } ;
    protected final TypeToken[] SINGLE_TYPE_OPERAND = {     /** массив односимвольных токенов (лексем) операндов**/
      TypeToken.Add, TypeToken.Sub, TypeToken.Div,
      TypeToken.Mult, TypeToken.Equals, TypeToken.Disjunc,
      TypeToken.Comma, TypeToken.Point, TypeToken.More,
            TypeToken.Less, TypeToken.Lparen, TypeToken.Rparen,
            TypeToken.ShapeLparen, TypeToken.ShapeRparen, TypeToken.L_SQUareParen,
            TypeToken.R_SQUareParen
    };



    protected void tokenizeWord(){
        StringBuilder buffer_word = new StringBuilder();
        buffer_word.append(peek(0));
        pos++;
        while(cond()){
            if(equals_word(peek(0)) == false)break;
            buffer_word.append(peek(0));
            pos++;
        }
        addToken(TypeToken.Word, buffer_word.toString());
    }       /** токенизация символьных данных - слов (имена переменных и функций))**/



    protected void tokenizeNumber(){
        StringBuilder buffer_number = new StringBuilder();
        boolean flage_double = false;
        while(cond()){
            if(peek(0) == '.' && (Character.isDigit(peek(1)) == true) && flage_double == false){
                flage_double = true;
                buffer_number.append(peek(0));
                pos++;
            }
            if(peek(0) == '.' && (Character.isDigit(peek(1)) == true) && flage_double == true){
                throw new RuntimeException("Error Type");
            }
            if(Character.isDigit(peek(0)) == false)break;
            buffer_number.append(peek(0));
            pos++;
        }
        if(flage_double == true){
            addToken(TypeToken.NumDouble64, buffer_number.toString());
        }else {
            addToken(TypeToken.NumInt32, buffer_number.toString());
        }
    } /** токенизация чисел**/
    protected void tokenizeString(){
        StringBuilder buffer_str = new StringBuilder();
        pos++;
        while(cond()){
            if(peek(0) == '\"'){ pos++; break; }
            buffer_str.append(peek(0));
            pos++;
        }
        addToken(TypeToken.Str, buffer_str.toString());
        pos++;
    } /** токенизация строк **/


    protected boolean equals_word_first_chars(final char fcurrent){
        if(Character.isLetter(fcurrent) == true)return true;
        if(Character.isDigit(fcurrent) == true)return false;
        if(fcurrent == '$')return true;
        if(fcurrent == '_')return true;
        else return false;
    }
    protected boolean equals_word(final char current) {
        int temp12332;
        if(Character.isLetter(current) == true)return true;
        if(Character.isDigit(current) == true)return true;
        if(current == '$')return true;
        if(current == '_')return true;
        else return false;
    }
    protected boolean search_single_chars_operand(final char current){
        for(int i=0; i<single_chars_operand.length; i++){
            if(single_chars_operand[i] == current){
                index_chars_operand = i;
                return true;
            }
        }
        return false;
    }
    protected void addToken(final TypeToken type, final String value){
        tokens.add(new BaseToken(type, value));
    }

    protected char peek(final int position_relative){
        int position = pos + position_relative;
        if(position >= length)return '\0';
        return input.charAt(position);
    }
    protected void setFile(final String input){
        this.input = input;
        this.length = input.length();
    }
    protected void setPos(final int position){
        this.pos = position;
    }
    protected boolean cond(){
        if(peek(0) == '\0')return false;
        else return true;
    }
    protected int getPos(){ return pos; }
    protected void next(){pos++;}
    protected void next(final int shift_position){ pos+=shift_position; }
    protected List<Token> getTokens(){ return tokens;}
    protected boolean same(final String str){
        int temp_pos = pos;
        for(int i=0; i<str.length(); i++, pos++){
            if(peek(0) != str.charAt(i)){ pos = temp_pos; return false;}
        }
        return true;
    }
}
