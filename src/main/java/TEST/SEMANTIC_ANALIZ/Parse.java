package TEST.SEMANTIC_ANALIZ;

import Lexer.*;

import java.util.ArrayList;
import java.util.List;

public class Parse {
    private List<Token> tokens, optimazi_tokens;
    private Token EOF = new BaseToken(TypeToken.EOF);
    private int pos, length;

    public Parse(final List<Token> tokens){
        this.tokens = tokens;
        this.length = tokens.size();
        this.optimazi_tokens = new ArrayList<Token>(this.length);
    }

    public List<Token> run(){
        while(cond()){

            if(peek(0).getType() == TypeToken.ShapeLparen){
                next(1);
                blokeTokens();
                continue;
            }

            optimazi_tokens.add(peek(0));
            next(1);
        }

        return optimazi_tokens;
    }

    private void blokeTokens() {
        TokenBlock block = new TokenBlock();
        Token temp = null;
          while(cond()){
              if(peek(0).getType() == TypeToken.ShapeRparen){
                  next(1);
                  break;
              }
              temp = peek(0);
              block.putToken(temp.getType(), temp.getValue());
              next(1);
          }

          //for(Token it : block.getTokens()) System.out.println(it.getType());
          optimazi_tokens.add(block);
    }

    private void next(final int shift_pos){ pos+=shift_pos; }
    private boolean cond(){
        if(peek(0) == EOF)return false;
        return true;
    }
    private Token peek(final int position_relative){
        int position = pos + position_relative;
        if(position >= length)return EOF;
        return tokens.get(position);
    }
}
