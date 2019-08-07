package Preprocessor.DEFINE;

import Preprocessor.BasePr;

import java.util.ArrayList;
import java.util.List;

public class Define extends BasePr {

    private StringBuilder input;
    public Define(StringBuilder input, int pos) {
        this.input = input;
        setText(input);
        setPos(pos);
    }

    public Defines parse(){
        StringBuilder def_name = defineExprName();      /** парсим имя макроопределения**/
        List<StringBuilder> loc_param = new ArrayList<StringBuilder>();
        defineExprParseParam(loc_param);             /** парсим локальные параметры макроопределения**/
        StringBuilder def_body = defineExprBody();   /** парсим тело макроопределения **/
        return new Defines(def_name, loc_param, def_body);
    }

    private void defineExprParseParam(List<StringBuilder> loc_param) {
        StringBuilder buffer_def_param = new StringBuilder();
        boolean flage_end = false;
        while(cond(peek(0))){
            if(peek(0) == ')'){
                next();
                flage_end = true;
                break;
            }
            if(peek(0) == ','){
                next();
                break;
            }
            buffer_def_param.append(peek(0));
            next();
        }
        loc_param.add(buffer_def_param);
        if(flage_end == false)defineExprParseParam(loc_param);
        return;
    }

    private StringBuilder defineExprBody() {
        StringBuilder buffer_def_body = new StringBuilder();
        skip_blank_characters('{');
        next();
        while(cond(peek(0))){
            if(peek(0) == '}' && peek(1) == ';'){
                next(2);
                break;
            }
            buffer_def_body.append(peek(0));
            next();
        }
        return buffer_def_body;
    }

    private StringBuilder defineExprName() {
        StringBuilder buffer_def_name = new StringBuilder();
        skip_blank_characters();
        while(cond(peek(0))){
            if(peek(0) == '('){
                next();
                break;
            }
            buffer_def_name.append(peek(0));
            next();
        }
        return buffer_def_name;
    }

    public int getPosEnd(){ return getPos(); }
    private char peek(final int position){
        return getCh(position);
    }
}
