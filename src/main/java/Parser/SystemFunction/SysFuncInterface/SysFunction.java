package Parser.SystemFunction.SysFuncInterface;

import Parser.Type.Types.Type;

import java.io.IOException;
import java.util.ArrayList;

public interface SysFunction {
   void setInputParams(ArrayList<Type> args);
   Type executeBody() throws IOException;
}
