package SemanticsAnalyzer.Functions;

import java.util.ArrayList;

public class FunctionTable {
    private static int begin_size_function_table = 50;
    private static ArrayList<Function> functions;

    static {
        functions = new ArrayList<Function>(begin_size_function_table);
    }

    public static void add(final Function function) {
        functions.add(function);
    }

    public static Function getFunction(final String name, final int LENGTH_LOCAL_PARAMS_FUNCTION){
       Function iterator = null;
       for(int i=0; i<functions.size(); i++){
           iterator = functions.get(i);
           if(iterator.getName().equalsIgnoreCase(name)){
               if(iterator.getLengthLocalParams() == LENGTH_LOCAL_PARAMS_FUNCTION)return iterator;
           }
       }
       return null;
    }


    public static ArrayList<Function> getFunctions() {
        return functions;
    }
}
