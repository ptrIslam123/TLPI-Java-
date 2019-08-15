package TEST;

import java.util.HashMap;
import java.util.Map;

public class VariableTable {

    private static Map<String, Integer> variables;
    static {
        //System.out.println("innit Map");
        variables = new HashMap<String, Integer>();
    }

    public static void put(String name, int value){
        //System.out.println("PUT: "+name+" v: "+value);
        variables.put(name, value);
    }
    public static Map<String, Integer> getVariables(){ return variables;}
    public static int setValue(String name){
        return variables.get(name);
    }
}
