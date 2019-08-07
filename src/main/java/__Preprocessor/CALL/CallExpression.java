package __Preprocessor.CALL;

import __Preprocessor.BaseClass;
import __Preprocessor.CONSTANT.Const;
import __Preprocessor.MACROSS.Define;

import java.util.ArrayList;
import java.util.List;

public class CallExpression extends BaseClass {

    private String input;
    private final List<Const> const_table;
    private final List<Define> define_table;
    private boolean flage_call_def = false;
    public CallExpression(final String input, final int pos, final List<Define> define_table, final List<Const> const_table){
        this.input = input;
        this.define_table = define_table;
        this.const_table = const_table;
        setPos(pos);
        setText(input);
    }

    public String getExpr(){
       return parse_call_expr();
    }

    private String parse_call_expr() {
        StringBuilder buffer_call_expr = new StringBuilder();
        while(cond(peek(0))){
            if(peek(0) == '('){ /** если это вызов макроса а не константного выражения **/
                flage_call_def = true;
                next();
                break;
            }
            if(mismatch(peek(0)) == false)break;
                buffer_call_expr.append(peek(0));
            next();
        }

        if(flage_call_def == true){ /** если все таки это вызов макроса **/
            return parse_call_def(buffer_call_expr);
        }
        return parse_call_const_expr(buffer_call_expr); /** если вызов констаньного выражения **/
    }


    private String parse_call_def(StringBuilder def_name) {
        List<String> input_params = new ArrayList<String>();
        parse_def_input_params(input_params);
        RecursCallDefine recursCallDefine = new RecursCallDefine(def_name, input_params, define_table);
        // test@
        recursCallDefine.run();
        return null;
    }

    private void parse_def_input_params(List<String> param) {
        StringBuilder def_in_param = new StringBuilder();
        boolean flage_param_end = false;
        while(cond(peek(0))){
            if(peek(0) == ')'){
                flage_param_end = true;
                next();
                break;
            }
            if(peek(0) == ','){
                next();
                break;
            }
            def_in_param.append(peek(0));
            next();
        }
        param.add(def_in_param.toString());
        if(flage_param_end == false)parse_def_input_params(param);
        return;
    }

    private String parse_call_const_expr(StringBuilder const_expr_name) {
        Const temp = null;
        for(int i=0; i<const_table.size(); i++){
            temp = const_table.get(i);
            if(equalsExpr(temp.getConstName(), const_expr_name) == true){
                //System.out.println(temp.getConstValue().toString());
                return temp.getConstValue().toString();
            }
        }
        throw new RuntimeException("ERROR: This constant expression was not found");
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
