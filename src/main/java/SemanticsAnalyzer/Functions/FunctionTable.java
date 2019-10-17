package SemanticsAnalyzer.Functions;

import java.util.ArrayList;

public class FunctionTable {
    private static int begin_size_function_table = 50;
    private static ArrayList<DefFunction> defFunctions;

    static {
        defFunctions = new ArrayList<DefFunction>(begin_size_function_table);
    }

    public static void add(final DefFunction defFunction) {
        defFunctions.add(defFunction);
    }

    public static DefFunction getFunction(final String name, final int LENGTH_LOCAL_PARAMS_FUNCTION){
       DefFunction iterator = null;
       for(int i = 0; i< defFunctions.size(); i++){
           iterator = defFunctions.get(i);
           if(iterator.getName().equalsIgnoreCase(name)){
               if(iterator.getLengthLocalParams() == LENGTH_LOCAL_PARAMS_FUNCTION)return iterator;
           }
       }
       return null;
    }


    public static ArrayList<DefFunction> getDefFunctions() {
        return defFunctions;
    }
}
