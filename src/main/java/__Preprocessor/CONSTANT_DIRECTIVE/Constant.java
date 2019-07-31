package __Preprocessor.CONSTANT_DIRECTIVE;

import java.util.HashMap;
import java.util.Map;

public class Constant {
    private Map<String, String> const_directive;

    public Constant(){
        const_directive = new HashMap<String, String>();
    }
    public String getConst_Value(final String const_name){ // метод получения значения константы по его имени
        if(const_directive.get(const_name) == null)throw new RuntimeException("constant is not limited");
        return const_directive.get(const_name);
    }
    public void putConst_value(final String const_name, final String const_value){  // метод добовления констант
        const_directive.put(const_name, const_value);
    }
    public Map<String, String> get_const_directive(){ return const_directive; }
}
