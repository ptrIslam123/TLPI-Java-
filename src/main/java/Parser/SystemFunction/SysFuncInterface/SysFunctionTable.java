package Parser.SystemFunction.SysFuncInterface;

import Parser.SystemFunction.SysFunctionForWorkingWithFiles.*;
import Parser.SystemFunction.SysFunctionForWorkingWithType.ClassesCastType.*;
import Parser.SystemFunction.SysFunctionForWorkingWithThreads.CreateThreadFunction;
import Parser.SystemFunction.SysFunctionForWorkingWithType.LengthArray;
import Parser.SystemFunction.SysFunctionForWorkingWithType.ThrowException;

import java.util.HashMap;
import java.util.Map;

public class SysFunctionTable {
    private static Map<String, SysFunction> sysFunctionT;

    public static SysFunction getSysFunc(final String name){
        return sysFunctionT.get(name);
    }

    static {
        sysFunctionT = new HashMap<String, SysFunction>();

        sysFunctionT.put("readf", new ReadFile());
        sysFunctionT.put("writef", new WriteFile());
        sysFunctionT.put("createf", new CreateFile());
        sysFunctionT.put("deletef", new DeleteFile());
        sysFunctionT.put("appendf", new AppendFile());
        sysFunctionT.put("existsf", new ExistsFile());


        sysFunctionT.put("str", new StrCast());
        sysFunctionT.put("int", new IntCast());
        sysFunctionT.put("double", new DoubleCast());
        sysFunctionT.put("char", new CharCast());
        sysFunctionT.put("bool", new BoolCast());

        sysFunctionT.put("length", new LengthArray());
        sysFunctionT.put("throw_exception", new ThrowException());

        sysFunctionT.put("thread_create", new CreateThreadFunction());
    }
}
