package __Preprocessor.CONSTANT;

import __Preprocessor.BaseClass;

public class Constant extends BaseClass {

    private String input;

    public Constant(String input, int pos) {
        this.input = input;
        setPos(pos);
        setText(this.input);
    }

    public Const parsing_const_expr(){
       StringBuilder buffer_const_expr_name = parse_const_expr_name();
       StringBuilder buffer_const_expr_value = parse_const_expr_value();
       return new Const(buffer_const_expr_name, buffer_const_expr_value);
    }

    private StringBuilder parse_const_expr_value() {
        StringBuilder const_expr_value = new StringBuilder();
        while(cond(peek(0))){
            if(peek(0) == ';'){
                next();
                break;
            }
            const_expr_value.append(peek(0));
            next();
        }
        return const_expr_value;
    }

    private StringBuilder parse_const_expr_name() {
        StringBuilder const_expr_name = new StringBuilder();
        skip_char('@');
        while(cond(peek(0))){
            if(peek(0) == '='){
                next();
                break;
            }
            const_expr_name.append(peek(0));
            next();
        }
        return const_expr_name;
    }
    public int getPosition(){ return getPos(); }
    private boolean cond(final char current){
        if(current != '\0')return true;
        else return false;
    }
    private char peek(final int position_relative){
        return getCh(position_relative);
    }
}
