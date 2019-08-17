package Parser.Variable;

import Parser.Type.Type;
import java.util.HashMap;
import java.util.Map;

public class VariableTable {
    private static Map<String, Type> variable_table;
    static {
        variable_table = new HashMap<String, Type>();
    }

    public static void addVariable(final String name, final Type value){
        variable_table.put(name,value);
    }
    public static Type getValueVariable(final String name){
        if(variable_table.containsKey(name) == false)throw new RuntimeException("Variable not defined");
        return variable_table.get(name);
    }

    public static Map<String, Type> getVariableTable(){
        return variable_table;
    }
}
