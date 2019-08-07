package Preprocessor;

import Preprocessor.CONST.Const;
import Preprocessor.CONST.ConstExpression;
import Preprocessor.DEFINE.Define;
import Preprocessor.DEFINE.Defines;

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
    }


    public StringBuilder run(){
        while(cond(peek(0))){
            if(peek(0) == '/' && peek(1) == '/'){
                on_line_comment();
                setLen();
                continue;
            } /** однострочный комментарий**/
            if(peek(0) == '/' && peek(1) == '*'){
                multi_line_comment();
                setLen();
                continue;
            } /** многострочный комментарий***/

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

            next();
        }
        System.out.println(input.toString());
        return input;
    }

    private void deleteDefineConstExpr() {
        int index_beg = getPos(), index_end = getPos();
        while(cond(peek(0))){
            if(peek(0) == ';'){
                index_end++;
                break;
            }
            index_end++;
            next();
        }
        System.out.println("peek: "+peek(14));
        System.out.println(index_beg+"\t\t"+index_end);
        input.delete(index_beg, index_end);
    }

    private void on_line_comment() {
        int index_end = getPos(), index_beg = getPos();
        while(cond(peek(0))){
            if(peek(0) == '\n')break;
            index_end++;
            next();
        }
        input.delete(index_beg, index_end);
    }
    private void multi_line_comment() {
        int index_beg  = getPos(), index_end = getPos();
        while(cond(peek(0))){
            if(peek(0) == '*' && peek(1) == '/'){
                index_end+=2;
                break;
            }
            index_end++;
            next();
        }
        input.delete(index_beg, index_end);
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