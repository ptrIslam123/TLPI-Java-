package Parser.Library.VARIABLE;

import Parser.TYPE.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VariableTable {
    private static List<Variable> variable_table;

    static {
        variable_table = new ArrayList<Variable>();
    }

    public static Type getValue(final String name){
        return getVariable(name).getValue();
    }
    private static Variable getVariable(final String name){
        Variable temp = null;
        for(int i=0; i<variable_table.size(); i++){
            temp = variable_table.get(i);
            if(temp.getName().equalsIgnoreCase(name))return temp;
        }
        throw new RuntimeException("Variable does not exists");
    }
    public static void putVariable(final String name, final Type value){
        variable_table.add(new Variable(name, value));
    }
}


/**
 private static final NumberValue ZERO = new NumberValue(0);
 private static final Map<String, Value> variables;

 static {
 variables = new HashMap<>();
 variables.put("PI", new NumberValue(Math.PI));
 variables.put("ПИ", new NumberValue(Math.PI));
 variables.put("E", new NumberValue(Math.E));
 variables.put("GOLDEN_RATIO", new NumberValue(1.618));
 }

 public static boolean isExists(String key) {
 return variables.containsKey(key);
 }

 public static Value get(String key) {
 if (!isExists(key)) return ZERO;
 return variables.get(key);
 }

 public static void set(String key, Value value) {
 variables.put(key, value);
 }
 **/