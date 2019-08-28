package Parser;

import Lexer.TypeToken;
import Lexer.*;
import Parser.DATA_SEGMENT.ObjArray;
import Parser.DATA_SEGMENT.SegmentData;
import Parser.Statement.Statement;
import Parser.Type.*;
import java.util.ArrayList;
import java.util.List;

public final class Parser extends ExpressionEval{
    private boolean flage_block = false;

    public void init_parser(List<Token> tokens, boolean flage_block){
      init_expression_eval(tokens);
      this.flage_block = flage_block;
    }



    public void run(){
       for(Token it : getTokens()) System.out.println(it.getType()+" : "+it.getValue());
       System.out.println("================================\n");


        while(cond()){
           statement();
        }


    }

    private Statement statement() {
        if(get(0).getType() == TypeToken.sys_write){
            next(1);
            write.stream(expression());
            write.execute();
            return null;
        }

        if(get(0).getType() == TypeToken.Alloc){
            next(1);
            String name_obj = get(0).getValue();
            next(1);

                if(get(0).getType() == TypeToken.Equals){   /** декларирование переменных **/
                    next(1);
                    return declare_variable(name_obj, expression());
                }

                if(get(0).getType() == TypeToken.L_SQUareParen){
                    next(1);
                    return declare_array(name_obj);
                }
        }
        primary();
        return null;
    }




    private Statement declare_variable(String name_obj, Type expression) {
        if(flage_block == true){    /** создание объекта на стеке **/

        }
        /** создание объекта в сегменте данных **/
        SegmentData.newObject(name_obj, expression);
        return null;
    }

    private Statement declare_array(String name_obj) {
        Type index_1 = null;
        List<Type> init_data_array = null;

        if(get(0).getType() == TypeToken.NumInt32 || get(0).getType() == TypeToken.Lparen){
            index_1 = expression();
        }


            if(get(0).getType() == TypeToken.L_SQUareParen){
                next(1);
                return declare_multi_array(name_obj,index_1);
            }

        if(get(0).getType() == TypeToken.Equals && get(1).getType() == TypeToken.Lparen){
            next(2);
            init_data_array = initialize_array();
        }
        if(flage_block == true){     /** создание объекта на стеке **/

        }
        /** создание объекта в сегменте данных **/
        SegmentData.newObject(name_obj, index_1, init_data_array);
        return null;
    }

    private Statement declare_multi_array(String name_obj, Type index_1) {
        Type index_2 = null;
        List<ObjArray> init_data_multi_array = null;
        if(get(0).getType() == TypeToken.NumInt32 || get(0).getType() == TypeToken.Lparen){
            index_2 = expression();
        }

        if(get(0).getType() == TypeToken.Equals && get(1).getType() == TypeToken.Lparen){
            next(3);
            init_data_multi_array = init_multi_array();
        }
        if(flage_block == true){     /** создание объекта на стеке **/

        }
        /** создание объекта в сегменте данных **/
        SegmentData.newObject(name_obj, index_1, index_2, init_data_multi_array);
        return null;
    }

    private List<ObjArray> init_multi_array() {
        List<ObjArray> init_data_multi_array = new ArrayList<ObjArray>();
        while(cond(TypeToken.Rparen)) {
            if(get(0).getType() == TypeToken.Lparen)next(1);
            ObjArray array = new ObjArray();
            while(cond(TypeToken.Rparen)){
                array.add(expression());
                if(get(0).getType() == TypeToken.Comma)next(1);
            }
            next(1);
            init_data_multi_array.add(array);
            if(get(0).getType() == TypeToken.Comma)next(1);
        }
        next(1);
        return init_data_multi_array;
    }

    private List<Type> initialize_array() {
        List<Type> init_data_array = new ArrayList<Type>();
        while(cond(TypeToken.Rparen)){
            if(get(0).getType() == TypeToken.Comma)next(1);
            init_data_array.add(expression());
        }
        next(1);
        return init_data_array;
    }


}
