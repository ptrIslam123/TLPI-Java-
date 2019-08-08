package Preprocessor;

public class BasePr {
    private int pos, length;
    private StringBuilder input;

    protected void next(final int shift_pos){ pos+=shift_pos; }
    protected void next(){ pos++; }
    protected int getPos(){ return pos; }
    protected void setPos(final int position){
        this.pos = position;
    }
    protected void setText(final StringBuilder text){
        this.input = text;
        this.length = this.input.length();
    }

    protected boolean match_char_to_expr(final char current){
        if(Character.isLetter(current) == true)return true;
        if(Character.isDigit(current) == true)return true;
        if(current == '_')return true;
        if(current == '@')return true;
        return false;
    } /** проверка символа на соответствие макровыражению **/
    protected void skip_blank_characters(){
        while(getCh(0) != '\0'){
            if(Character.isWhitespace(getCh(0)) == false)break;
            pos++;
        }
    }
    protected void skip_blank_characters(final char current){
        while(getCh(0) != '\0'){
            if(current == getCh(0))break;
            pos++;
        }
    }
    protected char getCh(final int position_relative){
        final int position = pos + position_relative;
        if(position >= length)return '\0';
        return input.charAt(position);
    }
    protected boolean cond(final char current){
        if(current != '\0')return true;
        else return false;
    }
    protected void setLength(final int setLength){
        this.length = setLength;
    }

}
