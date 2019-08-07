package __Preprocessor.MACROSS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Define {
    private StringBuilder def_body;
    private String def_name;
    private List<String> input_params, local_params;
    private Map<String, String> defMap;

    public Define(final StringBuilder def_name, final List<String> input_params, final StringBuilder def_body){
        this.def_name = def_name.toString();
        this.def_body = def_body;
        init_local_params(input_params);
    }

    public String getName(){ return def_name; }
    public StringBuilder getBody(){ return def_body; }
    public List<String> getInput_params(){ return input_params; }
    public List<String> getLocal_params(){ return local_params; }
    public boolean empty_params(){
        if(local_params.size() == 0)return true;
        else return false;
    } /** проверка на наличе параметров макроса **/

    public void setInput_params(final List<String> params_call_macross){
        input_params = new ArrayList<String>();
        if(params_call_macross.size() != local_params.size()){
            throw new RuntimeException("ERROR: The discrepancy of the signature of the declared macro");
        }
        for(int i=0; i<params_call_macross.size(); i++){
            input_params.add(params_call_macross.get(i));
        }
        initMapdef();
    }
    private void init_local_params(List<String> params) {
        this.local_params = new ArrayList<String>();
        for(int i=0; i<params.size(); i++){
            this.local_params.add(params.get(i));
        }
    }

    public Map<String, String> getDefMap(){ return defMap; }
    public String getValueMap(final String key_param){
        return defMap.get(key_param);
    }
    public void initMapdef(){
        defMap = new HashMap<String, String>();
        for(int i=0; i<input_params.size(); i++){
            defMap.put(local_params.get(i), input_params.get(i));
        }
    }
}
