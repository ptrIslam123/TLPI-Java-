package Parser.SystemFunction.SysFuncInterface;

import Parser.Type.Types.Type;

import java.io.IOException;
import java.util.ArrayList;

public interface Function {
    void setInputParams(ArrayList<Type> args);
    Type executeBody() throws IOException;
}
