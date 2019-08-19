package Parser.Variable;

import Parser.Type.IntegerType;
import Parser.Type.StringType;
import Parser.Type.Type;

import java.util.HashMap;
import java.util.Map;

public class VariableTable {
    private static Map<String, Type> variable_table;
    static {
        variable_table = new HashMap<String, Type>();
    }

    public static void addVariable(final String name, final Type value){
        variable_table.put(name, value);
    }
    public static Type getValue(final String name){
        if(variable_table.containsKey(name) == false)throw new RuntimeException("Unknown Variable: "+name);
        return variable_table.get(name);
    }

    public static IntegerType getLength(final String name){
        if(variable_table.containsKey(name) == false)throw new RuntimeException("Unknown Variable: "+name);
        Type var = variable_table.get(name);
        if(var instanceof StringType){
            return new IntegerType(String.valueOf(var.asString().length()));
        }else throw new RuntimeException("Error type: "+name);
    }
    public static Map<String, Type> getVariableTable(){ return variable_table; }
}
