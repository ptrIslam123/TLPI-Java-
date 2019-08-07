package __Preprocessor.CALL;

import __Preprocessor.BaseClass;
import __Preprocessor.MACROSS.Define;
import java.util.List;

public class RecursCallDefine extends BaseClass {
    private StringBuilder def_name;  /** имя вызываемого макроса **/
    private StringBuilder def_body = null;
    private List<Define> define_table; /** таблица макроссов **/
    private List<String> input_params; /** список входных параметров **/
    private List<String> loc_param = null;
    private Define define = null;

    public RecursCallDefine(StringBuilder def_name, List<String> input_params, List<Define> define_table) {
        this.def_name = def_name;
        this.input_params = input_params;
        this.define_table = define_table;
    }


    public void run(){
        define = searchDefine(define_table, def_name.toString());
        define.setInput_params(input_params);
        def_body = define.getBody();
        loc_param = define.getLocal_params();

        call_def_expr();
    }

    private void call_def_expr() {
        StringBuilder buffer_def_body = new StringBuilder();
        StringBuilder param = new StringBuilder();
        int[] index = null;
        int result = 0;
        for(int i=0; true ; i++){
            index = searchIndexParam(loc_param, def_body.charAt(i));
            if(count_mett_condition(index) != -1){ // есть удовлетворяющие элементы
                result = suitable_index(index, i, def_body, param);
                if(result != -1){
                    buffer_def_body.append(param);
                    System.out.println("v: "+param);
                    param.delete(0, param.length());
                    i+=result;
                }
            }
            if(i >= def_body.length())break;
            buffer_def_body.append(def_body.charAt(i));
        }
        System.out.println(buffer_def_body.toString());

    }

    private int suitable_index(int[] index, int position_i, StringBuilder body, StringBuilder param) {
        int ret_index = 0, res_compare = 0;
        for(int i=0; i<index.length; i++){
            ret_index = retIndex(i,index);
            res_compare = equals_str(body, loc_param.get(ret_index), position_i);
            if(res_compare != -1){
                //System.out.println("p: " + loc_param.get(ret_index)+"\t\tv: " + define.getValueMap(loc_param.get(ret_index)));
                param.append(define.getValueMap(loc_param.get(ret_index)));
                return loc_param.get(ret_index).length();
            }
        }
        return -1;
    }
    private int retIndex(final int begin, final int[] index){
        for(int i=begin; i<index.length; i++){
            if(index[i] != -1)return i;
        }
        return -1;
    }
    private int equals_str(StringBuilder body, String replace, int beg){
        int j=0;
        for(int i=beg; i<replace.length(); i++){
            if(body.charAt(i) != replace.charAt(j))return -1;
        }
        return replace.length();
    }
    private int count_mett_condition(final int[] array){
        for(int i=0; i<array.length; i++){
            if(array[i] != -1)return 1;
        }
        return -1;
    }
    private int[] searchIndexParam(List<String> loc_param, char current) {
        int len = loc_param.size();
        int[] index_param = new int[len];
        for(int i=0; i<len; i++){
            if(current == loc_param.get(i).charAt(0))index_param[i] = i;
            else index_param[i] = -1;
        }
        return index_param;
    }
}
