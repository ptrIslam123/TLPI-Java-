package SemanticsAnalyzer.TokenAnalyzer;

import Lexer.BaseToken;
import Lexer.Token;
import Lexer.TypeToken;
import java.util.ArrayList;
import java.util.List;

public class TokenAnalyzer {

    private List<Token> tokens;
    public TokenAnalyzer(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void run(){
        Analyzer analyzer = new Analyzer(tokens);
        tokens = analyzer.run();
    }

    public ArrayList<Token> getArrayTokens(){
        ArrayList<Token> tokensArray = new ArrayList<>(tokens.size());
        for(Token iterator : tokens){
            tokensArray.add(iterator);
        }
        return tokensArray;
    }
}
