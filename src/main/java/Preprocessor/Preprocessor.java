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
    private boolean flage_condition_compil = false; /** флаг условной компиляций **/

    public Preprocessor(final StringBuilder input){
        this.input = input;
        this.const_table = new ArrayList<Const>();
        this.define_table = new ArrayList<Defines>();
        setText(input);
        initDirective();
    }



    public StringBuilder run() throws IOException {
        while(cond(peek(0))){

            if(same("#if_def")){
               IfDef ifDef = new IfDef(input, getPos(), define_table, const_table);
               boolean res = ifDef.getValueConditionExpression();

               def_of_conditional_block(res, ifDef.getPosEnd());
               continue;
            }

            if(same("#end_def")){
                skip_block_end();
                continue;
            }

            if(same("#els_def") && flage_condition_compil == false){
                skipblock_els();
                continue;
            }

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
                setLen();
                continue;
            }

            next();
        }

        //System.out.println(input.toString());
        return input;
    }

    private void skipblock_els() {
        int temp_pos = getPos();
        while(cond(peek(0))){
            if(same("#end_def")){
                next(8);
                break;
            }
            next();
        }
        deleteStr(temp_pos, getPos());
        setLen();
        setPos(temp_pos);
    }

    private void skip_block_end() {
        int temp_pos = getPos();
        next(8);
        deleteStr(temp_pos, getPos());
        setLen();
        setPos(temp_pos);
    }


    private void def_of_conditional_block(final boolean value, int end_index) {
        if(value == true){
            flage_condition_compil = false;
            deleteStr(getPos(), end_index);
            setLen();
        }else {
            flage_condition_compil = true;
            int temp_pos = getPos();
            while(cond(peek(0))){
                if(same("#els_def")){
                    next(8);
                    break;
                }
                if(same("#end_def")){
                    next(8);
                    break;
                }
                next();
            }
            deleteStr(temp_pos, getPos());
            setLen();
            setPos(temp_pos);
        }
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
    private void deleteStr(final int beg_i, final int end_i){
        input.delete(beg_i, end_i);
    }
}
