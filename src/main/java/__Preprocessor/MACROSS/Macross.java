package __Preprocessor.MACROSS;

import __Preprocessor.BaseClass;
import java.util.ArrayList;
import java.util.List;

public class Macross extends BaseClass {

    private String input;
    private Define define;
    public Macross(final String input, final int pos) {
        this.input = input;
        setPos(pos);
        setText(this.input);
    }

   public Define parsing_macross(){
        /** Необходимо соблюдение порядка следования парсинга макроссов **/
        StringBuilder buffer_def_name = parse_def_name(); /** получение имени макроса**/
        List<String> params = new ArrayList<String>();  /** получение локальны параметров**/
        parse_def_params(params);
        StringBuilder buffer_def_body = parse_def_body(); /** получение тела макроса**/

         define = new Define(buffer_def_name, params, buffer_def_body);
        return define;
   }

    private StringBuilder parse_def_body() {
        StringBuilder def_body = new StringBuilder();
        //skip_blank_ch('{',1);
        skip_char('{',1);
        while(cond(peek(0))){
            if(peek(0) == '}' && peek(1) == ';'){
                next(2);
                break;
            }
            def_body.append(peek(0));
            next();
        }
        return def_body;
    }

    private void parse_def_params(List<String> params) {
        StringBuilder def_param = new StringBuilder();
        boolean flage_end_params = false;
        while(cond(peek(0))){
            if(peek(0) == ')'){
                next();
                flage_end_params = true;
                break;
            }
            if(peek(0) == ','){ next(); break; }
            def_param.append(peek(0));
            next();
        }
        params.add(def_param.toString());
        if(flage_end_params == false)parse_def_params(params);
        return;
    }

    private StringBuilder parse_def_name() {
        StringBuilder def_name = new StringBuilder();
        //skip_blank_ch();
        skip_char();
        while(cond(peek(0))){
            if(peek(0) == '('){ next(); break; }
            def_name.append(peek(0));
            next();
        }
        return def_name;
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
