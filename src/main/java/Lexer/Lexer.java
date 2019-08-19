package Lexer;

import java.util.List;

public class Lexer extends BaseLexer {
   private String input;

    public Lexer(final String input){
        this.input = input;
        setFile(input);
        setPos(0);
    }

    public List<Token> run(){
        while(cond()){
            if(peek(0) == '\"'){ /** парсинг строковых данвх **/
                tokenizeString();
                continue;
            }

            if(peek(0) == '/' && peek(1) == '/'){
                only_lini_comment();
            }
            if(peek(0) == '/' && peek(1) == '*'){
                multi_line_comment();
            }
            tokenizeOPERANDS();      /** парсинг много символьных операндов**/

            if(Character.isDigit(peek(0))){ /** парсинг числовых данных**/
                tokenizeNumber();
                continue;
            }
            if(search_single_chars_operand(peek(0))){ /** парсинг односимвольных операндов**/
                TypeToken type = SINGLE_TYPE_OPERAND[index_chars_operand];
                addToken(type,"");
                next();
                continue;
            }
            if(equals_word_first_chars(peek(0))){ /** парсинг символьных данных (переменные, имена функций и т.д.)**/
                tokenizeWord();
                continue;
            }

            next();
        }

        addToken(TypeToken.EOF,"");
        return getTokens();
    }

    private void multi_line_comment() {
        next(2);
        while(cond()){
            if(peek(0) == '*' && peek(1) == '/'){
                next(2);
                break;
            }
            next();
        }
    }

    private void only_lini_comment() {
        next(2);
        while(cond()){
            if(peek(0) == '\n'){ next(); break;}
            next();
        }
    }

    private void tokenizeOPERANDS(){
        if(same("null")){
            addToken(TypeToken.Void,"");
        }
        if(same("length")){
            addToken(TypeToken.Len,"");
        }
        if(same("size")){
            addToken(TypeToken.Size,"");
        }
        if(same("str")){
            addToken(TypeToken.Str_cast,"");
        }
        if(same("integer")){
            addToken(TypeToken.Int_cast,"");
        }
        if(same("double")){
            addToken(TypeToken.Double_cast,"");
        }
        if(same("bool")){
            addToken(TypeToken.Boll_cast,"");
        }
        if(same("print")){
            addToken(TypeToken.sys_read, "");
        }
        if(same("false")){
            addToken(TypeToken.Bool, "0");
        }
        if(same("true")){
            addToken(TypeToken.Bool, "1");
        }
        if(same("def")){
            addToken(TypeToken.Define_func, "");
        }
        if(same("return")){
            addToken(TypeToken.ret, "");
        }
        if(same("if")){
            addToken(TypeToken.If,"");
        }
        if(same("elsif")){
            addToken(TypeToken.ElsIF,"");
        }
        if(same("else")){
            addToken(TypeToken.Els,"");
        }
        if(same("while")){
            addToken(TypeToken.While,"");
        }
        if(same("for")){
            addToken(TypeToken.For,"");
        }
        if(same("break")){
            addToken(TypeToken.Break, "");
        }
        if(same("continue")){
            addToken(TypeToken.Continue,"");
        }
        if(same("&&") || same("and")){
            addToken(TypeToken.And, "");
        }
        if(same("||") || same("or")){
            addToken(TypeToken.Or,"");
        }
        if(same(">=")){
            addToken(TypeToken.MoreEq, "");
        }
        if(same("<=")){
            addToken(TypeToken.LessEq, "");
        }
        if(same("==")){
            addToken(TypeToken.Eq, "");
        }
        if(same("!=")){
            addToken(TypeToken.NoEq, "");
        }
        if(same("++")){
            addToken(TypeToken.Incr,"");
        }
        if(same("--")){
            addToken(TypeToken.Decr,"");
        }
        if(same("+=")){
            addToken(TypeToken.IncrSomeNum,"");
        }
        if(same("-=")){
            addToken(TypeToken.DecrSomeNum, "");
        }
    }   /** парсинг много символьных операндов**/
}
