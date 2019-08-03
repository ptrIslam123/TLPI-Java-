package __Preprocessor;

import __Preprocessor.CONSTANT.Const;
import __Preprocessor.CONSTANT.Constant;
import __Preprocessor.MACROSS.Define;
import __Preprocessor.MACROSS.Macross;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Preprocessor extends BaseClass {
    private String input;
    private List<Define> define_table; /** таблица макроссов **/
    private List<Const> const_table;  /** таблица константных выражений **/

    public Preprocessor(String input) {
        this.define_table = new ArrayList<Define>();
        this.const_table = new ArrayList<Const>();

        this.input = input;
        setText(this.input);
    }

    public String preprocess() throws IOException {
       StringBuilder Buffer = new StringBuilder();
        while(peek(0) != '\0'){

            if(same("#def")){ // парсинг макроса
                Macross macross = new Macross(input, getPos());
                define_table.add(macross.parsing_macross());
                next();
                continue;
            }
            if(same("#const")){ // парсинг константных выражений
                Constant constant = new Constant(input, getPos());
                const_table.add(constant.parsing_const_expr());
                next();
                continue;
            }

            if(same("//")){
                one_line_comment();
                continue;
            }
            if(same("/*")){
                multi_line_comment();
                continue;
            }

            Buffer.append(peek(0));
            next();
        }

        System.out.println(Buffer.toString());
        return Buffer.toString();
    }

    private void multi_line_comment() {
        while(peek(0) != '\0'){
            if(peek(0) == '*' && peek(1) == '/'){
                next(2);
                break;
            }
            next();
        }
    }

    private void one_line_comment() {
        while(peek(0) != '\0'){
            if(peek(0) == '\n'){
                next();
                break;
            }
            next();
        }
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
        return true;
    }

    private char peek(final int position_relative){
       return getCh(position_relative);
    }
}

/*
        // РАБОТА С МАКРОСАМИ //
        Define temp = null;
        for(int i=0; i<define_table.size(); i++){
            temp = define_table.get(i);
            System.out.println(temp.getName()+"(");
            for(String it : temp.getLocal_params()){
                System.out.println(it);
            }
            System.out.println(")"+temp.getBody());
            System.out.println("===============");
        }
*/

/*
        // ДЛЯ КОНСТАНТНЫХ ВЫРАЖЕНИЙ
        Const temp = null;
        for(int i=0; i<const_table.size(); i++){

            temp = const_table.get(i);
            System.out.println(temp.getConstName()+" : "+temp.getConstValue());
        }
*/
