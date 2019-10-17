package Parser.SystemFunction.SysFunctionForWorkingWithThreads;

import Parser.Type.ParserClasses.Parser;
import Parser.Type.Types.Type;
import SemanticsAnalyzer.Functions.DefFunction;

import java.io.IOException;
import java.util.ArrayList;

public class ThreadFunction extends Parser implements Runnable {
    private Type result;
    private String nameFunc;
    private ArrayList<Type> listInputParams;

    public ThreadFunction(final String nameFunc, final ArrayList<Type> listInputParams) {
        this.nameFunc = nameFunc;
        this.listInputParams = listInputParams;
    }

    @Override
    public void run() {
       /* DefFunction currentFunc = searchFunction(nameFunc, listInputParams.size());
        if(currentFunc != null){
            result =  executeUserFunc(currentFunc, listInputParams);
        }
        try {
            result = executeSysFunc(nameFunc, listInputParams);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    public Type getResultExecuteFuncBody(){
        //return result;
        return null;
    }
}
