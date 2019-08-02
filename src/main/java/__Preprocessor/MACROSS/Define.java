package __Preprocessor.MACROSS;

import java.util.ArrayList;
import java.util.List;

public class Define {
    private StringBuilder def_name, def_body;
    private List<String> input_params, local_params;

    public Define(final StringBuilder def_name, final List<String> input_params, final StringBuilder def_body){
        this.def_name = def_name;
        this.def_body = def_body;
        init_local_params(input_params);
    }

    public StringBuilder getName(){ return def_name; }
    public StringBuilder getBody(){ return def_body; }
    public List<String> getInput_params(){ return input_params; }
    public List<String> getLocal_params(){ return local_params; }

    private void init_local_params(List<String> params) {
        this.local_params = new ArrayList<String>();
        for(int i=0; i<params.size(); i++){
            this.local_params.add(params.get(i));
        }
    }
}
