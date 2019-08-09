package Preprocessor;

import Preprocessor.CALL_EXPRESSION.ParseCall;
import Preprocessor.CONST.Const;
import Preprocessor.CONST.ConstExpression;
import Preprocessor.DEFINE.Define;
import Preprocessor.DEFINE.Defines;
import Preprocessor.IMPORT_FILE.CaLLImport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Preprocessor extends BasePr {
    private StringBuilder input;
    private List<Const> const_table;
    private List<Defines> define_table;

    public Preprocessor(final StringBuilder input){
        this.input = input;
        this.const_table = new ArrayList<Const>();
        this.define_table = new ArrayList<Defines>();
        setText(input);
        initDirective();
    }



    public StringBuilder run() throws IOException {
        while(cond(peek(0))){
            if(peek(0) == '/' && peek(1) == '/'){
                //on_line_comment();
                setLen();
                continue;
            } /** однострочный комментарий**/
            if(peek(0) == '/' && peek(1) == '*'){
                //multi_line_comment();
                setLen();
                continue;
            } /** многострочный комментарий***/

            if(same("#import")){ /** парсинг директивы подключения внешних файлов**/
                CaLLImport classImport = new CaLLImport(input, (getPos() + 7));
                StringBuilder ImpotrFile = classImport.callImport();
                input.replace(getPos(), classImport.getPosEnd(), ImpotrFile.toString());
                setLen();
                continue;
            }

            if(same("#const")){  /** парсинг константных выражений **/
                ConstExpression constExpr = new ConstExpression(input, (getPos() + 6));
                const_table.add(constExpr.parse());
                input.delete(getPos(), constExpr.getPosEnd());
                setLen();
                continue;
            }
            if(same("#def")){
                Define define = new Define(input, (getPos() + 4));
                define_table.add(define.parse());
                input.delete(getPos(), define.getPosEnd());
                setLen();
                continue;
            }

            if(peek(0) == '@'){ /** директива вызова макровыражения **/
                ParseCall parseCall = new ParseCall(input, getPos(), define_table, const_table);
                StringBuilder value = parseCall.callExpressiion();
                input.replace(getPos(), parseCall.getPosEnd(), value.toString());
                //System.out.println("peek: " + peek(0) + peek(1) + peek(2) + peek(3) + peek(4) + peek(5) + peek(6));
                setLen();
                continue;
            }

            next();
        }


        System.out.println(input.toString());
        return input;
    }


    private void setLen(){
        setLength(input.length());
    }
    private boolean same(final String chars){
        int tep_pos = getPos();
        for(int i=0; i<chars.length(); i++){
            if(peek(0) != chars.charAt(i)){
                setPos(tep_pos);
                return false;
            }
            next();
        }
        setPos(tep_pos);
        return true;
    }
    private void initDirective() {
        const_table.add(new Const(new StringBuilder("@TRUE"), new StringBuilder("1")));  /** директива @TRUE = 1**/
        const_table.add(new Const(new StringBuilder("@FALSE"), new StringBuilder("0")));  /** директива @FALSE = 1**/
        const_table.add(new Const(new StringBuilder("@PI"), new StringBuilder("3.141592653589793238462643383279")));  /** директива содержащее в себе значение ПИ **/
        const_table.add(new Const(new StringBuilder("@E"), new StringBuilder("2.71828182845904523536028747135")));   /** директива содержащее в себе значение Е **/
    }/** данный метод содержит в себе уже некоторые часто используемые константные выражения **/
    private char peek(final int position){
        return getCh(position);
    }
}

/*
        // ТАБЛИЦА МАКРООПРЕДЕЛЕНИЙ //
        Defines temp = null;
        for(int i=0; i<define_table.size(); i++){
            temp = define_table.get(i);
            System.out.println(temp.getName()+"\n(");
            for(StringBuilder it : temp.getLoc_param()) System.out.println(it);
            System.out.println(")"+temp.getBody()+"\n================");
        }
*/


/*
        // ТАБЛИЦА КОНСТАНТНЫХ ВЫРАЖЕНИЙ //
        Const temp = null;
        for(int i=0; i<const_table.size(); i++){
            temp = const_table.get(i);
            System.out.println(temp.getConstName()+" : "+temp.getConstValue());
        }
*/