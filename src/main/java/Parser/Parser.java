package Parser;

import Lexer.TypeToken;
import Lexer.*;
import Parser.DATA_SEGMENT.ObjArray;
import Parser.DATA_SEGMENT.ObjectType;
import Parser.DATA_SEGMENT.SegmentData;
import Parser.Statement.Statement;
import Parser.Type.*;
import com.sun.corba.se.impl.ior.ObjectAdapterIdArray;

import java.util.ArrayList;
import java.util.List;

public class Parser extends BaseParser{
    private boolean flage_block = false;

    public void init_parser(List<Token> tokens, boolean flage_block){
      setTokens(tokens);
      setLength(tokens.size());
      setPos(0);
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

    private Statement declare_variable(String name_obj, Type expression) {
        if(flage_block == true){    /** создание объекта на стеке **/

        }
        /** создание объекта в сегменте данных **/
        SegmentData.newObject(name_obj, expression);
        return null;
    }


    private Type expression(){ return conditionExpression(); }
    private Type conditionExpression(){
        Type result = additive();
        while(true){
            if(get(0).getType() == TypeToken.Less){
                next(1);
                result = eval(TypeToken.Less, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.More){
                next(1);
                result = eval(TypeToken.More, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.LessEq){
                next(1);
                result = eval(TypeToken.LessEq, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.MoreEq){
                next(1);
                result = eval(TypeToken.MoreEq, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.Eq){
                next(1);
                result = eval(TypeToken.Eq, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.NoEq){
                next(1);
                result = eval(TypeToken.NoEq, result, additive());
                continue;
            }
            break;
        }
        return result;
    }
    private Type additive(){
        Type result = multiplivative();
        while(true){
            if(get(0).getType() == TypeToken.Add){
                next(1);
                result = eval('+',result, multiplivative());
                continue;
            }
            if(get(0).getType() == TypeToken.Sub){
                next(1);
                result = eval('-',result, multiplivative());
                continue;
            }
            break;
        }
        return result;
    }
    private Type multiplivative(){
        Type result = primary();
        while(true){
            if(get(0).getType() == TypeToken.Mult){
                next(1);
                result = eval('*',result, primary());
                continue;
            }
            if(get(0).getType() == TypeToken.Div){
                next(1);
                result = eval('-',result, primary());
                continue;
            }
            break;
        }
        return result;
    }
    private Type primary(){
        Type temp = null;
        if(get(0).getType() == TypeToken.Lparen) {
            next(1);
            temp = expression();
            consume(TypeToken.Rparen);
            return temp;
        }

        return primitive(); /** парсинг примитивных типов данных **/
    }
}
