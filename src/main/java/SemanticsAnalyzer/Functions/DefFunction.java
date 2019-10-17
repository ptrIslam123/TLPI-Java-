package SemanticsAnalyzer.Functions;

import Lexer.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DefFunction {
    private String name;
    private ArrayList<String> localParams;
    private TokenBlock body;
    private final int LENGTH_LOCAL_PARAMS_FUNCTION;

    public DefFunction(final String name, final List<String> localParams, final TokenBlock body) {
        this.name = name;
        LENGTH_LOCAL_PARAMS_FUNCTION = localParams.size();
        initLocalParams(localParams);
        this.body = new TokenBlock();
        this.body.put(body.getTokens());
    }

    private void initLocalParams(final List<String> localParams) {
        final int SIZE_LOCAL_PARAMS = localParams.size();
        this.localParams = new ArrayList<>(SIZE_LOCAL_PARAMS);
        for(int i=0; i<SIZE_LOCAL_PARAMS; i++){
            this.localParams.add(localParams.get(i));
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getLocalParams() {
        return localParams;
    }

    public TokenBlock getBody() {
        return body;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLengthLocalParams() {
        return LENGTH_LOCAL_PARAMS_FUNCTION;
    }
}
