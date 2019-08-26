package SEMANTICS_ANALYSIS;

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

            if(peek(0).getType() == TypeToken.R_SQUareParen){
                next(1);
            }
            if(peek(0).getType() == TypeToken.Define_func){
                next(1);
                //define_function();
                continue;
            }
            if(peek(0).getType() == TypeToken.Word && peek(1).getType() == TypeToken.Lparen){
                optimazi_tokens.add(new BaseToken(TypeToken.Call));
                optimazi_tokens.add(new BaseToken(TypeToken.Word,peek(0).getValue()));
                next(1);
                continue;
            }
            if(peek(0).getType() == TypeToken.ShapeLparen){
                /*
                if(pos != 0){
                    if(peek(-1).getType() == TypeToken.Equals){
                        optimazi_tokens.add(new BaseToken(TypeToken.ShapeLparen));
                        next(1);
                        continue;
                    }
                    if(peek(-1).getType() == TypeToken.ShapeLparen){

                    }
                }
                next(1);
                */
                optimazi_tokens.add(blokeTokens());
                continue;
            }

            optimazi_tokens.add(peek(0));
            next(1);
        }

/*
        for(Token it : optimazi_tokens){
            System.out.println(it.getType()+" : " +it.getValue());
        }
        System.out.println("==========================");
*/
        return optimazi_tokens;
    }
/*
    private void define_function() {
        String name_func = peek(0).getValue();  // function name
        next(1);
        consume(TypeToken.Lparen);
        List<Token> local_param = parse_local_param(); // get local_param function
        consume(TypeToken.ShapeLparen);
        TokenBlock body = blokeTokens();
        FunctionTable.putFunc(name_func, local_param, body);
    }
*/
    private List<Token> parse_local_param() {
        List<Token> param = new ArrayList<Token>();
        while(peek(0).getType() != TypeToken.Rparen){
            if(peek(0).getType() == TypeToken.Comma)next(1);
            param.add(peek(0));
            next(1);
        }
        next(1);
        return param;
    }

    private TokenBlock blokeTokens() {
     TokenBlock block = new TokenBlock();
     Token it = null;
     while(peek(0).getType() != TypeToken.EOF){
         if(peek(0).getType() == TypeToken.ShapeRparen){
             pos++;
             break;
         }
         if(peek(0).getType() == TypeToken.ShapeLparen){
             pos++;
             block.putBlock(blokeTokens());
             continue;
         }
         block.putToken(peek(0).getType(), peek(0).getValue());
         pos++;
     }
     return block;
    }

    private void next(final int shift_pos){ pos+=shift_pos; }
    private boolean consume(final TypeToken type){
        if(peek(0).getType() == type){
            next(1);
            return true;
        }else throw new RuntimeException("Unknown Token Type: "+peek(0).getType());
    }
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
