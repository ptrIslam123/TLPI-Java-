package Preprocessor.CALL_EXPRESSION;

import Preprocessor.DEFINE.Defines;
import java.util.ArrayList;
import java.util.List;

public class CallDefine implements CallExpression {
    private StringBuilder defineExprName;
    private List<Defines> define_table;
    private List<StringBuilder> in_param, loc_param;

    public CallDefine(final StringBuilder defineExprName, final List<StringBuilder> in_param, final List<Defines> define_table) {
        this.defineExprName = defineExprName;
        this.define_table = define_table;
        init_in_param(in_param);
    }
    @Override
    public StringBuilder call() {
        Defines define = getDefine();
        loc_param = define.getLoc_param();
        define.setIn_param(in_param);
        /**
        System.out.println("LOCAL_PARAM");
        System.out.println(define.getName());
        for(StringBuilder it : define.getLoc_param()) System.out.println(it);
        System.out.println("IN_PARAM");
        for(StringBuilder it : define.getIn_param()) System.out.println(it);
        **/
        return replaceBody(define);
    }

    private StringBuilder replaceBody(final Defines define){
        StringBuilder bufferNewBody = new StringBuilder();
        StringBuilder body = define.getBody();
        int resultIndex = 0;
        for(int i=0; i<body.length(); i++){

            resultIndex = searchIndexParam(body, i, body.charAt(i));
            if(resultIndex != -1){
                StringBuilder defParamName = loc_param.get(resultIndex); /** получаем имя текщего локального параметра**/
                StringBuilder defParamValue = define.getValueParam(defParamName); /** получаем значение текущего локального параметра**/

                bufferNewBody.append(defParamValue);
                i+=defParamName.length() - 1;
                continue;
            }
            bufferNewBody.append(body.charAt(i));
        }
        return bufferNewBody;
    }

    private int searchIndexParam(final StringBuilder body, final int current_pos, final char current){ /** **/
        int result = 0;
        for(int i=0; i<loc_param.size(); i++){
            if(current == loc_param.get(i).charAt(0)){
                result = equals_str(body, current_pos, i);
                if(result != -1)return i;
            }
        }
        return -1;
    }

    private int equals_str(StringBuilder body, int current_pos, int index) { /** **/
        int j=0, size = loc_param.get(index).length(), end_index = current_pos + size;

        for(int i=current_pos; j<size; i++, j++){
            if(body.charAt(i) != loc_param.get(index).charAt(j))return -1;
        }
        return index;
    }

    private boolean accordType(final char current){
        char[] character = {'+','-','*','/','.',',','%','^','?',':',';'}; /** массив допустимых символов до и после константного параметра**/
        for(int i=0; i<character.length; i++){
            if(current == character[i])return true;
        }
        return false;
    }
    private Defines getDefine(){
        Defines temp_define = null;
        for(int i=0; i<define_table.size(); i++){
            temp_define = define_table.get(i);
            if(equalsStr(temp_define.getName(), defineExprName) == true)return temp_define;
        }
        throw new RuntimeException("Error: "+defineExprName+" an undefined macros");
    }
    private boolean equalsStr(final StringBuilder expr1, final StringBuilder expr2){
        String ex1 = expr1.toString();
        String ex2 = expr2.toString();
        if(ex1.equalsIgnoreCase(ex2))return true;
        else return false;
    }
    private void init_in_param(List<StringBuilder> param) {
        int size = param.size();
        this.in_param = new ArrayList<StringBuilder>(size);
        for(int i=0; i<size; i++){
            this.in_param.add(param.get(i));
        }
    }
}
