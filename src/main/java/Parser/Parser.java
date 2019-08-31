package Parser;

import Lexer.TypeToken;
import Lexer.*;
import Parser.DATA_SEGMENT.ObjArray;
import Parser.DATA_SEGMENT.SegmentData;
import Parser.DATA_SEGMENT.SteckData;
import Parser.Statement.Statement;
import Parser.Type.*;
import SEMANTICS_ANALYSIS.Function;
import SEMANTICS_ANALYSIS.FunctionTable;
import SEMANTICS_ANALYSIS.Parse;

import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

public final class Parser extends ExpressionEval{
    private boolean flage_block = false;
    private Parse parse;

    private static int visibility = 0;

    public void init_parser(List<Token> tokens, boolean flage_block){
      init_expression_eval(tokens);
      setPos(0);
      this.flage_block = flage_block;
    }

    public void run(){
       //for(Token it : getTokens()) System.out.println(it.getType()+" : "+it.getValue());
       //System.out.println("================================\n");


        while(cond()){
           statement();
        }


    }

    private Statement statement() {
        if(get(0).getType() == TypeToken.Block){
             blockStatement();
             next(1);
             return null;
        }

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

    /**** ПОКА ЧТО ВРОДЕ РАБОТАЕТ НАДО ТЕПЕРЬ РЕШИТЬ ПРОБЛЕМУ ВЫДЕЛЕНИЯ ПАМЯТИ НА СТЕКЕ И СЕГМЕНТЕ ДАННЫХ ***/
    private Statement blockStatement() {
        paser = new Parser();
        TokenBlock block = (TokenBlock) get(0);
        paser.init_parser(block.getTokens(), true);

        visibility++;
        //SteckData.setVisibility(visibility);

        paser.run();

        SteckData.deleteObject(visibility);  /** ЧИСТИМ СТЕК ПОСЛЕ ВЫХОДА ИЗ ОБЛАСТИ ВИДИМОСТИ **/
        setFlageBlock(false);       /** УСТАГАВЛИВАЕМ ФЛАГ ЧТО ВЫХОДИМ ИЗ БЛОКА STATEMENT`ОВ **/

        visibility--;
        return null;
    }


    private Statement declare_variable(String name_obj, Type expression) {
        if(flage_block == true){    /** создание объекта на стеке **/
            SteckData.newObject(name_obj, expression, visibility);
            return null;
        }
        /** создание объекта в сегменте данных **/
        SegmentData.newObject(name_obj, expression, 0);
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
            SteckData.newObject(name_obj, index_1, init_data_array, visibility);
            return null;
        }
        /** создание объекта в сегменте данных **/
        SegmentData.newObject(name_obj, index_1, init_data_array, 0);
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
            SteckData.newObject(name_obj, index_1, index_2, init_data_multi_array, visibility);
            return null;
        }
        /** создание объекта в сегменте данных **/
        SegmentData.newObject(name_obj, index_1, index_2, init_data_multi_array, 0);
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

    private void setFlageBlock(final boolean status){
        this.flage_block = status;
    }
}
