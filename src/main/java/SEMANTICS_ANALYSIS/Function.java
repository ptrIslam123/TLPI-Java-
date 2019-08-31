package SEMANTICS_ANALYSIS;

import Lexer.*;
import java.util.ArrayList;

public class Function {
    private String name;
    private ArrayList<Token> local_param;
    private TokenBlock body;

    public Function(String name, ArrayList<Token> local_param, TokenBlock body) {
        this.name = name;
        this.local_param = local_param;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Token> getLocal_param() {
        return local_param;
    }

    public TokenBlock getBody() {
        return body;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocal_param(ArrayList<Token> local_param) {
        this.local_param = local_param;
    }

    public void setBody(TokenBlock body) {
        this.body = body;
    }
}
