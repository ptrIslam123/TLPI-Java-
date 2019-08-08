package Preprocessor.CONST;

import Preprocessor.BasePr;

public class ConstExpression extends BasePr {

    private StringBuilder input;
    public ConstExpression(final StringBuilder input, final int pos){
        this.input = input;
        setText(input);
        setPos(pos);
    }

    public Const parse(){
        StringBuilder const_expr_name = const_expr_name(); /** парсим имя константного выражения**/
        StringBuilder const_expr_value = const_expr_value(); /** парсим значение константного выражения**/
        return new Const(const_expr_name, const_expr_value);
    }

    private StringBuilder const_expr_value() {
        StringBuilder buffer_const_expr_value = new StringBuilder();
        while(cond(peek(0))){
            if(peek(0) == ';'){
                next();
                break;
            }
            buffer_const_expr_value.append(peek(0));
            next();
        }
        return buffer_const_expr_value;
    }
    private StringBuilder const_expr_name() {
        StringBuilder buffer_const_expr_name = new StringBuilder();
        skip_blank_characters();
        while(cond(peek(0))){
            if(peek(0) == '='){
                next();
                break;
            }
            buffer_const_expr_name.append(peek(0));
            next();
        }
        return buffer_const_expr_name;
    }

    public int getPosEnd(){
        return getPos();
    }
    private char peek(final int position){
        return getCh(position);
    }
}
