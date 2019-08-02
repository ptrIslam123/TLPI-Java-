package __Preprocessor;

import __Preprocessor.MACROSS.Define;
import __Preprocessor.MACROSS.Macross;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Preprocessor extends BaseClass {
    private String input;
    private List<Define> define_table;

    public Preprocessor(String input) {
        this.define_table = new ArrayList<Define>();
        this.input = input;
        setText(this.input);
    }

    public String preprocess() throws IOException {
       StringBuilder Buffer = new StringBuilder();
        while(peek(0) != '\0'){

            if(same("#def")){ // парсинг макроса
                Macross macross = new Macross(input, getPos());
                define_table.add(macross.parsing_macross());
            }

            Buffer.append(peek(0));
            next();
        }
        /*
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
        //System.out.println(Buffer.toString());
        return null;
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
