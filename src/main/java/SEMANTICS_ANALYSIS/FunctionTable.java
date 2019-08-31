package SEMANTICS_ANALYSIS;

import java.util.ArrayList;
import Lexer.*;

public class FunctionTable {
    private static ArrayList<Function> functions;

    static {
        functions = new ArrayList<Function>(10);
    }

    public static void putFuncion(final String name_function, final ArrayList<Token> local_param, final TokenBlock body){
        functions.add(new Function(name_function, local_param, body));
    }

    public static Function getFunction(final String name, final int size_local_params){
        Function function = null;
        for(int i=0; i<functions.size(); i++){
            function = functions.get(i);
            if(function.getName().equalsIgnoreCase(name) && function.getLocal_param().size() == size_local_params)return function;
        }
        throw new RuntimeException("Function: "+name+" not define");
    }

    public static ArrayList<Function> getFunctions() {
        return functions;
    }
}
