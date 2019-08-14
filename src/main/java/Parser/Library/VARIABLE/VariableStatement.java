package Parser.Library.VARIABLE;

import Parser.TYPE.Type;
import java.util.HashMap;
import java.util.Map;

public class VariableStatement {
   private static Map<String, Type> variable_table;
   static {
       variable_table = new HashMap<String, Type>();
   }

   public static void putValueVariable(final String name, Type value){
       variable_table.put(name, value);
   }
   public static Type getValueVariable(final String name){
       return variable_table.get(name);
   }
   public static boolean isExists(final String name){
       return variable_table.containsKey(name);
   }
}

/**
 * public final class Variables {
 *
 *     private static final Map<String, Double> variables;
 *
 *     static {
 *         variables = new HashMap<>();
 *         variables.put("PI", Math.PI);
 *         variables.put("ПИ", Math.PI);
 *         variables.put("E", Math.E);
 *         variables.put("GOLDEN_RATIO", 1.618);
 *     }
 *
 *     public static boolean isExists(String key) {
 *         return variables.containsKey(key);
 *     }
 *
 *     public static double get(String key) {
 *         if (!isExists(key)) return 0;
 *         return variables.get(key);
 *     }
 *
 *     public static void set(String key, double value) {
 *         variables.put(key, value);
 *     }
 * }
 **/