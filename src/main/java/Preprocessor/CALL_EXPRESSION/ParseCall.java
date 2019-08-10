package Preprocessor.CALL_EXPRESSION;

import Preprocessor.BasePr;
import Preprocessor.CONST.Const;
import Preprocessor.DEFINE.Defines;
import java.util.ArrayList;
import java.util.List;

public class ParseCall extends BasePr {
    private StringBuilder input;
    private List<Defines> define_table; /** таблица макросов**/
    private List<Const> consts_table;   /** таблица константных выражений**/
    private boolean flage_define = false;
    private CallExpression callExpression; /** интерфейс определяющий поведение макровызова **/

    public ParseCall(final StringBuilder input, final int pos, final List<Defines> define_table, final List<Const> const_table) {
        this.input = input;
        this.define_table = define_table;
        this.consts_table = const_table;
        setText(input);
        setPos(pos);
    }

    public StringBuilder callExpressiion(){
        StringBuilder buffer_call_expr = parse_call_directive();
        if(flage_define == true){ /** если вызываемое макровыражение это макрос**/
            List<StringBuilder> in_param = new ArrayList<StringBuilder>();
            parse_call_define(in_param);    /** парсинг входных парметров макроса**/
            callExpression = new CallDefine(buffer_call_expr, in_param, define_table);
            return callExpression.call();
        }
        /** если вызываемое макроопределение это константное выражение **/
        callExpression = new CallConstExpr(buffer_call_expr, consts_table);
        return callExpression.call();
    }

    private StringBuilder parse_call_directive() {
        StringBuilder buffer_expr_name = new StringBuilder();
        while(cond(peek(0))){
            if(peek(0) == '('){
                next();
                flage_define = true;
                break;
            }
            if(match_char_to_expr(peek(0)) == false)break;
            buffer_expr_name.append(peek(0));
            next();
        }
        return buffer_expr_name;
    }

    private void parse_call_define(List<StringBuilder> in_param) {
        StringBuilder buffer_define_param = new StringBuilder();
        boolean flage_end_parse = false;
        while(cond(peek(0))){
            if(peek(0) == ')'){
                next();
                flage_end_parse = true;
                break;
            }
            if(peek(0) == ','){
                next();break;
            }
            buffer_define_param.append(peek(0));
            next();
        }
        in_param.add(buffer_define_param);
        if(flage_end_parse == false)parse_call_define(in_param);
        return;
    }

    public int getPosEnd(){ return getPos(); }
    private char peek(final int position){
        return getCh(position);
    }
}
